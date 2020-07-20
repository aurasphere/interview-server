package co.aurasphere.interview.server.service;

import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import co.aurasphere.interview.server.dao.SurveyDao;
import co.aurasphere.interview.server.dao.TechnologyDao;
import co.aurasphere.interview.server.dao.UserDao;
import co.aurasphere.interview.server.model.Question;
import co.aurasphere.interview.server.model.Score;
import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.model.Technology;
import co.aurasphere.interview.server.model.User;
import co.aurasphere.interview.server.rest.model.CreateUserResponse;
import co.aurasphere.interview.server.rest.model.GetUserSurveyResultResponse;
import co.aurasphere.interview.server.rest.model.SearchUserResponse;
import co.aurasphere.interview.server.utilities.InterviewMimeMessagePreparator;

/**
 * Provides the business logic for this application.
 * 
 * @author Donato Rimenti
 */
@Service
public class InterviewService {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(InterviewService.class);

	/**
	 * Page size for paginated results, like
	 * {@link #searchUser(String, String, String, LocalDate, String, LocalDate, LocalDate, int)}.
	 */
	private final static int RESULT_PAGE_SIZE = 10;

	/**
	 * Dao for surveys.
	 */
	@Autowired
	private SurveyDao surveyDao;

	/**
	 * Dao for users.
	 */
	@Autowired
	private UserDao userDao;

	/**
	 * Dao for technologies.
	 */
	@Autowired
	private TechnologyDao technologyDao;

	/**
	 * Encryptor used to digest the password.
	 */
	@Autowired
	private PasswordEncryptor encryptor;

	/**
	 * Server secret used for token generation.
	 */
	@Autowired
	@Qualifier("serverSecret")
	private String serverSecret;

	/**
	 * Used to send confirmation emails after registration.
	 */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Template generation for emails.
	 */
	@Autowired
	private VelocityEngine velocityEngine;

	/**
	 * Used to handle asynchronous task.
	 */
	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * Email sender for the registration confirmation.
	 */
	@Autowired
	@Qualifier("appSenderEmail")
	private String appSenderEmail;

	/**
	 * Inserts a new survey into the database.
	 *
	 * @param survey
	 *            the survey to insert
	 * @return true, if the insert is successful
	 */
	public boolean insertSurvey(Survey survey) {
		LOG.warn("InterviewService.insertSurvey({})", survey);
		// Validates and performs the insert.
		Objects.requireNonNull(survey, "Questionario nullo.");
		return surveyDao.insertSurvey(survey);
	}

	/**
	 * Gets a list of available technologies for the surveys.
	 *
	 * @return a list of available technologies
	 */
	public List<String> getTechList() {
		LOG.trace("InterviewService.getTechList()");
		List<String> techList = new ArrayList<String>();
		List<Technology> technologies = technologyDao.findAll();

		// Converts the dao objects into a list of strings.
		for (Technology t : technologies) {
			techList.add(t.getName());
		}

		return techList;
	}

	/**
	 * Gets a survey by its name.
	 *
	 * @param name
	 *            the name of the survey to retrieve
	 * @return a survey
	 */
	public Survey getSurveyByName(String name) {
		LOG.trace("InterviewService.getSurveyByName({})", name);
		// Validates and performs the select.
		Objects.requireNonNull(name, "Nome questionario nullo");
		Survey survey = surveyDao.getSurveyByName(name, false);
		
		// Computes the survey time if not present.
		if (survey.getTime() == 0) {
			// Gives 30 seconds for question.
			int computedTime = survey.getQuestions().size() * 30;
			survey.setTime(computedTime);
		}
		
		return survey;
	}

	/**
	 * Returns the list of the available surveys for a user.
	 * 
	 * @param username
	 *            the user whose survey list has to be retrieved
	 * @param technology
	 *            the technology whose survey list has to be retrieved
	 * @return a list of surveys for that technology not yet taken by the user
	 */
	public Set<String> getAvailableSurveys(String username, String technology) {
		LOG.trace("InterviewService.getAvailableSurveys(*, {})", username, technology);
		// Input validations.
		Objects.requireNonNull(username, "Username nullo.");
		Objects.requireNonNull(technology, "Tecnologia nulla.");

		// Gets a list of survey names.
		List<Survey> surveys = surveyDao.getSurveysByTechnology(technology);
		Set<String> availableSurveys = new HashSet<String>();
		for (Survey s : surveys) {
			String surveyName = s.getName();
			availableSurveys.add(surveyName);
		}
		return availableSurveys;
	}

	/**
	 * Tries to create a user. If the creation fails (for example if the user
	 * already exists), returns a negative response with an error message.
	 * 
	 * @param user
	 *            the user to create
	 * @return a response which contains an error message if anything goes wrong
	 */
	public CreateUserResponse createUser(User user) {
		LOG.trace("InterviewService.createUser(*)");
		// Input validations.
		String email = user.getEmail();
		Objects.requireNonNull(user, "Utente nullo.");
		Objects.requireNonNull(user.getName(), "Nome utente nullo.");
		Objects.requireNonNull(user.getSurname(), "Cognome utente nullo.");
		Objects.requireNonNull(email, "Email utente nulla.");
		Objects.requireNonNull(user.getBirthday(), "Data di nascita utente nulla.");
		Objects.requireNonNull(user.getTechnology(), "Tecnologia utente nulla.");
		Objects.requireNonNull(user.getPassword(), "Password utente nulla.");

		// Sets creation times and privileges.
		LocalDateTime updateTime = LocalDateTime.now();
		user.setCreationTime(updateTime);
		user.setUpdateTime(updateTime);
		user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

		// [v1.2.0] Password double hashing, client-side and server-side.
		user.setPassword(encryptor.encryptPassword(user.getPassword()));

		// Tries to insert the user. If the user is already there, returns an
		// error message.
		String userEmail = user.getEmail();
		try {
			userDao.insertUser(user);
		} catch (DuplicateKeyException e) {
			LOG.warn("Tentativo di registrazione di un utente duplicato [{}] bloccato.", userEmail);
			return new CreateUserResponse("Questa email e' gia' registrata.");
		}

		// [v1.3.0] Generates a unique token and sends a confirmation email.
		String token = generateUniqueToken(updateTime, userEmail);
		sendConfirmationEmail(userEmail, token);

		// Everything went well, return a positive response.
		return new CreateUserResponse(true);
	}

	/**
	 * Generates a new unique token for the confirmation email.
	 * 
	 * @param generationTime
	 *            the time of the generation which needs to be the same of the
	 *            last user update
	 * @param userEmail
	 *            the email of the user for the token
	 * @return a unique token used for authentication
	 */
	private String generateUniqueToken(LocalDateTime generationTime, String userEmail) {
		LOG.trace("InterviewService.generateUniqueToken(*, {})", userEmail);
		return encryptor.encryptPassword(userEmail + generationTime + serverSecret);
	}

	/**
	 * Sends a new confirmation email for account registration. The difference
	 * with {@link #sendConfirmationEmail(String, String)} is that this method
	 * saves a new update time for the user and generates a new token according
	 * to this time in order to renew the token validity.
	 * 
	 * @param userEmail
	 *            the email of the user to whom the confirmation needs to be
	 *            send
	 */
	public void sendConfirmationEmailAgain(String userEmail) {
		LOG.info("InterviewService.sendConfirmationEmailAgain({})", userEmail);
		// Saves a new update time in order to renew the token.
		LocalDateTime updateTime = LocalDateTime.now();
		userDao.saveUserUpdateTime(userEmail, updateTime);

		// Creates an unique token and sends the email again.
		String token = generateUniqueToken(updateTime, userEmail);
		sendConfirmationEmail(userEmail, token);
	}

	/**
	 * Sends a new confirmation email for account registration.
	 * 
	 * @param userEmail
	 *            the email of the user to whom the confirmation needs to be
	 *            send
	 * @see #sendConfirmationEmailAgain(String)
	 */
	private void sendConfirmationEmail(String userEmail, String token) {
		LOG.info("InterviewService.sendConfirmationEmail({})", userEmail);

		// Checks that the account is disabled before sending the activation
		// mail.
		boolean accountEnabled = userDao.checkAccountEnabled(userEmail);
		if (accountEnabled) {
			throw new IllegalStateException("Tentativo di invio mail di attivazione a un account gia' attivato!");
		}

		// Message from template.
		VelocityContext context = new VelocityContext();
		context.put("appPath", getAppUrl());
		context.put("userEmail", userEmail);
		context.put("userToken", token);

		Template template = velocityEngine.getTemplate("registration-confirmation.vm");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		MimeMessagePreparator preparator = new InterviewMimeMessagePreparator(appSenderEmail, userEmail,
				"Conferma creazione account colloqui", writer.toString());

		// Sends the message. XXX: This is sync to add some delay and prevent
		// spam but it may be prone to congestion depending on the SMTP server
		// workload.
		this.mailSender.send(preparator);
	}

	/**
	 * Builds the app URL which will be in the form:
	 * <code>[protocol]://[host]:[port]/[context_path]</code>
	 * 
	 * @return the app URL
	 */
	private String getAppUrl() {
		// Request data to get the context.
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		// Builds the application path.
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	/**
	 * Sends a password recovery email for a user.
	 * 
	 * @param userEmail
	 *            the email of the user whose password needs to be recovered
	 */
	public void sendRecoverPasswordEmail(String userEmail) {
		LOG.info("InterviewService.sendRecoverPasswordEmail({})", userEmail);

		// We compute this synchronously because we can't access the request
		// from another thread.
		final String appUrl = getAppUrl();

		// All of this is executed asynchronously in order to prevent
		// enumeration attacks.
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				LOG.info("InterviewService.sendRecoverPasswordEmail({}).asyncTask", userEmail);
				// Saves a new update time in order to renew the token.
				LocalDateTime updateTime = LocalDateTime.now();
				boolean outcome = userDao.saveUserUpdateTime(userEmail, updateTime);

				// Checks that the recipient is registered before sending the
				// email.
				if (!outcome) {
					LOG.error("Richiesto recupero password con email errata. Possibile enumeration attack.");
					return;
				}

				// Creates an unique token and sends the email again.
				String token = generateUniqueToken(updateTime, userEmail);

				// Message from template.
				VelocityContext context = new VelocityContext();
				context.put("appPath", appUrl);
				context.put("userEmail", userEmail);
				context.put("userToken", token);

				Template template = velocityEngine.getTemplate("password-recovery.vm");
				StringWriter writer = new StringWriter();
				template.merge(context, writer);

				MimeMessagePreparator preparator = new InterviewMimeMessagePreparator(appSenderEmail, userEmail,
						"Recupero password colloqui", writer.toString());

				// Sends the email.
				mailSender.send(preparator);
			}
		});
	}

	/**
	 * Submits a survey, adding the score to the user profile.
	 *
	 * @param surveyName
	 *            the name of the survey taken
	 * @param userAnswers
	 *            the answers provided by the user
	 * @param userEmail
	 *            the email of the user
	 * @return the survey taken with the correct answers
	 */
	public Survey submitSurvey(String surveyName, List<Integer[]> userAnswers, String userEmail) {
		LOG.trace("InterviewService.submitSurvey({}, *, *)", surveyName);

		// Input validations
		Objects.requireNonNull(surveyName);
		Objects.requireNonNull(userAnswers);
		Objects.requireNonNull(userEmail);

		// Gets the survey correct answers from database and compares them with
		// the ones provided by the user.
		Survey survey = surveyDao.getSurveyByName(surveyName, true);
		List<Question> surveyQuestions = survey.getQuestions();
		List<List<Integer>> userAnswerList = new ArrayList<List<Integer>>();

		// Computes the score.
		int finalScore = 0;
		for (int i = 0; i < userAnswers.size(); i++) {
			Integer[] surveyAnswer = surveyQuestions.get(i).getCorrectAnswers();
			Integer[] userAnswer = userAnswers.get(i);
			userAnswerList.add(Arrays.asList(userAnswer));

			// Sorts the answers before comparing them.
			Arrays.sort(surveyAnswer);
			Arrays.sort(userAnswer);

			if (Arrays.equals(surveyAnswer, userAnswer)) {
				finalScore++;
			}
		}

		// The max score of the survey is equal to one point per question
		// number.
		Score score = new Score(surveyName, finalScore, surveyQuestions.size(), userAnswerList);

		// Updates the user score.
		User user = userDao.getUserByEmail(userEmail);
		Set<Score> scores = user.getScores();

		// Initializes the set if empty. If the score is already in the set, an
		// exception is thrown. An admin must reset the score before it can be
		// taken again. [v1.1.0] This has changed and now a survey can be taken
		// as many times as needed since all the history is saved.
		if (scores == null) {
			scores = new HashSet<Score>();
			user.setScores(scores);
		} else if (scores.contains(score)) {
			LOG.error("Il questionario [{}] risulta già compilato dall'utente [{}]. Probabile tentativo di tampering.",
					surveyName, userEmail);
			throw new IllegalStateException(
					"Il questionario [" + surveyName + "] e' gia' stato compilato dall'utente [" + userEmail + "]!");
		}

		// Adds the new score.
		scores.add(score);

		// Updates the timestamps.
		user.setUpdateTime(LocalDateTime.now());
		userDao.updateUser(user);

		// Returns the correct answers.
		return survey;
	}

	/**
	 * Searches for one or more users that matches the given criteria. The users
	 * found, if any, will match every non-null field passed as argument.
	 *
	 * @param name
	 *            the users' name
	 * @param surname
	 *            the users' surname
	 * @param email
	 *            the users' email
	 * @param birthday
	 *            the users' birthday
	 * @param technology
	 *            the users' technology
	 * @param dateTakenStart
	 *            the users' start limit for a taken survey. If a user has taken
	 *            a survey from this date up to the <code>dateTakenEnd</code>
	 *            field, it will match. If the latter argument is null, then
	 *            only the users who have taken a survey on this day will match
	 * @param dateTakenEnd
	 *            the users' end limit for a taken survey. If a user has taken a
	 *            survey from this date down to the <code>dateTakenStart</code>
	 *            field, it will match. If the latter argument is null, then
	 *            only the users who have taken a survey on this day will match
	 * @param orderBySurveyScore
	 *            the name of a survey to use for sorting the scores
	 * @param page
	 *            the page to fetch
	 * @return the specified page of users' search results along with the count
	 *         of total result pages
	 */
	public SearchUserResponse searchUser(String name, String surname, String email, LocalDate birthday,
			String technology, LocalDate dateTakenStart, LocalDate dateTakenEnd, String orderBySurveyScore, int page) {
		LOG.trace("InterviewService.searchUser(*, *, *, *, {}, {}, {}, {})", technology, dateTakenStart, dateTakenEnd,
				page);

		// Convers a page to a number of elements to skip for the query.
		int elementsToSkip = (page - 1) * RESULT_PAGE_SIZE;

		// Gets the specified page of results.
		List<User> searchResults = userDao.searchUser(name, surname, email, birthday, technology, dateTakenStart,
				dateTakenEnd, orderBySurveyScore, elementsToSkip, RESULT_PAGE_SIZE);

		// Gets the count of available pages of results.
		long resultsCount = userDao.countSearchUserTotal(name, surname, email, birthday, technology, dateTakenStart,
				dateTakenEnd, orderBySurveyScore);

		// Returns a page of results along with the count of result pages.
		return new SearchUserResponse(searchResults, resultsCount);
	}

	/**
	 * Deletes a survey score for an user.
	 *
	 * @param email
	 *            the email of the user whose score needs to be deleted
	 * @param surveyName
	 *            the name of the survey which score needs to be deleted
	 * @param timeTaken
	 *            the time of the survey taken used as id in conjunction with
	 *            the survey name
	 * @return the outcome of the operation
	 */
	public boolean deleteUserSurveyScore(String email, String surveyName, LocalDateTime timeTaken) {
		LOG.warn("InterviewService.deleteUserSurveyScore(*, {}, {})", surveyName, timeTaken);

		// Input validations
		Objects.requireNonNull(email);
		Objects.requireNonNull(surveyName);
		Objects.requireNonNull(timeTaken);

		return userDao.deleteUserSurveyScore(email, surveyName, timeTaken);
	}

	/**
	 * Gets the user answers to a survey along with the survey itself and the
	 * correct answers.
	 *
	 * @param email
	 *            the email of the user whose score needs to be retrieved
	 * @param surveyName
	 *            the name of the survey which score needs to be retrieved
	 * @param timeTaken
	 *            the time of the survey taken used as id in conjunction with
	 *            the survey name
	 * @return the user answers along with the survey and the correct answers to
	 *         it
	 */
	public GetUserSurveyResultResponse getUserSurveyResult(String email, String surveyName, LocalDateTime timeTaken) {
		LOG.warn("InterviewService.getUserSurveyResult(*, {}, {})", surveyName, timeTaken);

		// Input validations
		Objects.requireNonNull(email);
		Objects.requireNonNull(surveyName);
		Objects.requireNonNull(timeTaken);

		// Gets the survey.
		Survey survey = surveyDao.getSurveyByName(surveyName, true);

		// XXX: The current implementation doesn't fill in the actual user
		// answers since this information is already present on the view. The
		// parameters are still available to this method though for future
		// implementations if needed.

		return new GetUserSurveyResultResponse(survey, null);
	}

	/**
	 * Checks that the token passed as argument is valid for the user. If so,
	 * the user account is activated.
	 * 
	 * @param email
	 *            the email to activate
	 * @param token
	 *            the token for the activation
	 * @return the outcome of the operation
	 */
	public boolean activateAccount(String email, String token) {
		LOG.info("InterviewService.activateAccount(*, *)", email, token);
		User user = userDao.getUserByEmail(email);

		// This may happen if the request is forged manually.
		if (user == null) {
			LOG.warn("User with email [{}] does not exist.", email);
			return false;
		}
		// Checks if already enabled.
		if (user.isEnabled()) {
			LOG.warn("User with email [{}] is already enabled.", email);
			return false;
		}

		// Token validation ok.
		if (checkTokenValid(token, user)) {
			user.setUpdateTime(LocalDateTime.now());
			user.setEnabled(true);
			userDao.updateUser(user);
			return true;
		}

		return false;
	}

	/**
	 * Stores a new password for a user with the given email.
	 * 
	 * @param email
	 *            the email of the user
	 * @param token
	 *            the security token for this operation
	 * @param newPassword
	 *            the new password of the user
	 * @return the outcome of the operation
	 */
	public boolean passwordRecovery(String email, String token, String newPassword) {
		LOG.info("InterviewService.passwordRecovery(*, *, *)", email, token);
		User user = userDao.getUserByEmail(email);

		// Token validation ok.
		if (checkTokenValid(token, user)) {
			// [v1.2.0] Password double hashing, client-side and server-side.
			user.setPassword(encryptor.encryptPassword(newPassword));
			user.setUpdateTime(LocalDateTime.now());

			// Let's also enable the user if it's not. It doesn't make any sense
			// to send another mail.
			user.setEnabled(true);
			userDao.updateUser(user);
			return true;
		}

		return false;
	}

	/**
	 * Checks if a given token is valid and not expired for a user.
	 * 
	 * @param token
	 *            the token to check
	 * @param user
	 *            the user of the token
	 * @return true if the token is valid and not expired, false otherwise
	 */
	private boolean checkTokenValid(String token, User user) {
		// Performs validation on the token.
		LocalDateTime lastUpdateTime = user.getUpdateTime();
		boolean tokenValid = encryptor.checkPassword(user.getEmail() + lastUpdateTime + serverSecret, token);
		boolean tokenExpired = Duration.between(lastUpdateTime, LocalDateTime.now()).toHours() > 0;

		// Validation ok.
		if (tokenValid && !tokenExpired) {
			LOG.trace("Valid token and not expired.");
			return true;
		}

		LOG.warn("Token is not valid or expired for email [{}].", user.getEmail());
		return false;
	}

}