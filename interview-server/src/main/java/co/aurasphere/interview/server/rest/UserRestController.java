package co.aurasphere.interview.server.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.aurasphere.interview.server.model.User;
import co.aurasphere.interview.server.rest.model.BaseInterviewServletRestResponse;
import co.aurasphere.interview.server.rest.model.ConfirmAccountRequest;
import co.aurasphere.interview.server.rest.model.ConfirmAccountResponse;
import co.aurasphere.interview.server.rest.model.CreateUserResponse;
import co.aurasphere.interview.server.rest.model.DeleteUserSurveyScoreRequest;
import co.aurasphere.interview.server.rest.model.GetUserSurveyResultRequest;
import co.aurasphere.interview.server.rest.model.GetUserSurveyResultResponse;
import co.aurasphere.interview.server.rest.model.PasswordRecoveryRequest;
import co.aurasphere.interview.server.rest.model.PasswordRecoveryResponse;
import co.aurasphere.interview.server.rest.model.SearchUserRequest;
import co.aurasphere.interview.server.rest.model.SearchUserResponse;
import co.aurasphere.interview.server.service.InterviewService;

/**
 * REST controller for operations on a {@link User}.
 * 
 * @author Donato Rimenti
 */
@RestController
@RequestMapping("/user")
public class UserRestController {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(UserRestController.class);

	/**
	 * The service.
	 */
	@Autowired
	private InterviewService service;

	/**
	 * Creates a new user and performs autologin if everything goes well.
	 *
	 * @param user
	 *            the user to create
	 * @param httpRequest
	 *            the request, injected by Spring, used to perform autologin
	 * @return the outcome of the operation
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CreateUserResponse createUser(@RequestBody User user, HttpServletRequest httpRequest)
			throws IOException, ServletException {
		LOG.info("UserRestController.createUser(*)");
		CreateUserResponse response = service.createUser(user);
		return response;
	}

	/**
	 * Searches a user using the criteria passed as arguments.
	 *
	 * @param request
	 *            the filters for the search
	 * @return a list of users that matches the given criteria
	 */
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody SearchUserResponse searchUser(@RequestBody SearchUserRequest request) {
		LOG.trace("UserRestController.searchUser(*)");
		return service.searchUser(request.getName(), request.getSurname(), request.getEmail(), request.getBirthday(),
				request.getTechnology(), request.getDateTakenStart(), request.getDateTakenEnd(),
				request.getOrderBySurveyScore(), request.getPage());
	}

	/**
	 * Deletes a survey score for an user.
	 *
	 * @param request
	 *            contains the data about the survey score to delete
	 * @return the outcome of the operation
	 */
	@RequestMapping(value = "/deleteUserSurveyScore", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BaseInterviewServletRestResponse deleteUserSurveyScore(
			@RequestBody DeleteUserSurveyScoreRequest request) {
		LOG.warn("UserRestController.deleteUserSurveyScore(*)");
		boolean outcome = service.deleteUserSurveyScore(request.getEmail(), request.getSurveyName(),
				request.getSurveyTime());
		return new BaseInterviewServletRestResponse(outcome);
	}

	/**
	 * Gets the user answers to a survey along with the survey itself and the
	 * correct answers.
	 *
	 * @param request
	 *            contains the data about the survey and user to retrieve
	 * @return the user answers along with the survey and the correct answers to
	 *         it
	 */
	@RequestMapping(value = "/getUserSurveyResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody GetUserSurveyResultResponse getUserSurveyResult(
			@RequestBody GetUserSurveyResultRequest request) {
		LOG.warn("UserRestController.getUserSurveyResult(*)");
		return service.getUserSurveyResult(request.getEmail(), request.getSurveyName(), request.getSurveyTime());
	}

	/**
	 * Sends a confirmation email to activate an account.
	 *
	 * @param email
	 *            the email to activate
	 * @return the outcome of the operation
	 */
	@RequestMapping(value = "/sendAccountActivationEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BaseInterviewServletRestResponse sendAccountActivationEmail(
			@RequestParam("email") String email) {
		LOG.info("UserRestController.sendAccountActivationEmail({})", email);
		service.sendConfirmationEmailAgain(email);
		return new BaseInterviewServletRestResponse(true, "Email inviata correttamente");
	}

	/**
	 * Activates an account.
	 *
	 * @param request
	 *            contains the data of the account to activate
	 * @return the outcome of the operation
	 */
	@RequestMapping(value = "/confirmAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ConfirmAccountResponse confirmAccount(@RequestBody ConfirmAccountRequest request) {
		LOG.info("UserRestController.confirmAccount(*)");
		boolean outcome = service.activateAccount(request.getEmail(), request.getToken());
		String message;
		if (outcome) {
			message = "Attivazione account avvenuta con successo.";
		} else {
			message = "Il link che hai seguito \u00e8 scaduto o invalido. Effettua il login con il tuo account per inviare una nuova mail di attivazione.";
		}
		return new ConfirmAccountResponse(outcome, message);
	}

	/**
	 * Sends an email to recover an account password.
	 *
	 * @param email
	 *            the email whose password needs to be recovered
	 * @return the outcome of the operation
	 */
	@RequestMapping(value = "/sendRecoverPasswordEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody BaseInterviewServletRestResponse sendRecoverPasswordEmail(
			@RequestParam("email") String email) {
		LOG.info("UserRestController.sendRecoverPasswordEmail({})", email);
		service.sendRecoverPasswordEmail(email);
		return new BaseInterviewServletRestResponse(true, "Email inviata correttamente");
	}

	/**
	 * Confirms the password recovery by storing the new password.
	 *
	 * @param request
	 *            contains the data of the account whose password needs to be
	 *            recovered
	 * @return the outcome of the operation
	 */
	@RequestMapping(value = "/passwordRecovery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody PasswordRecoveryResponse passwordRecovery(@RequestBody PasswordRecoveryRequest request) {
		LOG.info("UserRestController.passwordRecovery(*)");
		boolean outcome = service.passwordRecovery(request.getEmail(), request.getToken(), request.getNewPassword());
		String message;
		if (outcome) {
			message = "Salvataggio nuova password avvenuto con successo.";
		} else {
			message = "Il link che hai seguito \u00e8 scaduto o invalido.";
		}
		return new PasswordRecoveryResponse(outcome, message);
	}

	/**
	 * This webservice does nothing. It is used only to extend the user session
	 * if needed.
	 */
	@GetMapping("/extendUserSession")
	public void extendUserSession() {
	}

}
