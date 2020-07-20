package co.aurasphere.interview.server.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

import co.aurasphere.interview.server.model.User;

/**
 * Dao for {@link User}.
 * 
 * @author Donato Rimenti
 */
@Repository
public class UserDao {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(UserDao.class);

	/**
	 * The collection name.
	 */
	private final static String COLLECTION_NAME = "user";

	/**
	 * The email field.
	 */
	private final static String EMAIL_FIELD = "email";

	/**
	 * The name field.
	 */
	private static final String NAME_FIELD = "name";

	/**
	 * The surname field.
	 */
	private static final String SURNAME_FIELD = "surname";

	/**
	 * The technology field.
	 */
	private static final String TECHNOLOGY_FIELD = "technology";

	/**
	 * The survey name field inside a score.
	 */
	private static final String SURVEY_NAME_FIELD = "surveyName";

	/**
	 * The time taken field which represents when a survey has been taken.
	 */
	private static final String TIME_TAKEN_FIELD = "timeTaken";

	/**
	 * The scores field.
	 */
	private static final String SCORES_FIELD = "scores";

	/**
	 * The score field which represent a survey score.
	 */
	private static final String SCORE_FIELD = "score";

	/**
	 * The time taken field inside a score. Represents the time at which the
	 * survey has been completed.
	 */
	private static final String TIME_TAKEN_SUBFIELD = SCORES_FIELD + "." + TIME_TAKEN_FIELD;

	/**
	 * The birthday field.
	 */
	private static final String BIRTHDAY_FIELD = "birthday";

	/**
	 * The creation time field.
	 */
	private static final String CREATION_TIME_FIELD = "creationTime";

	/**
	 * The update time field.
	 */
	private static final String UPDATE_TIME_FIELD = "updateTime";

	/**
	 * The authorities field which represents a user's permissions.
	 */
	private static final String AUTHORITIES_FIELD = "authorities";

	/**
	 * The role field which represents a user's role inside a permission.
	 */
	private static final String ROLE_FIELD = "role";

	/**
	 * The admin role value.
	 */
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	/**
	 * The mongo template.
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

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
	 * @param skip
	 *            the number of records to skip
	 * @param limit
	 *            the number of elements to return
	 * @return a list of users
	 */
	public List<User> searchUser(String name, String surname, String email, LocalDate birthday, String technology,
			LocalDate dateTakenStart, LocalDate dateTakenEnd, String orderBySurveyScore, long skip, int limit) {
		LOG.debug("UserDao.searchUser(*, *, *, *, {}, {}, {}, {}, {}, {})", technology, dateTakenStart, dateTakenEnd,
				orderBySurveyScore, skip, limit);

		List<AggregationOperation> aggregationOperations = new ArrayList<AggregationOperation>();
		// 1. Filter by query parameters.
		aggregationOperations.add(Aggregation.match(buildSearchUserCriteria(name, surname, email, birthday, technology,
				dateTakenStart, dateTakenEnd, orderBySurveyScore)));

		// Optional steps only used when orderBySurveyScore is not null.
		if (orderBySurveyScore != null) {
			String thisString = "this";
			String[] projectionFields = new String[] { EMAIL_FIELD, NAME_FIELD, SURNAME_FIELD, BIRTHDAY_FIELD,
					CREATION_TIME_FIELD, UPDATE_TIME_FIELD, TECHNOLOGY_FIELD, SCORES_FIELD };

			// 2. Projection: creates a new array field with the scores to be
			// ordered.
			String tmpScoreArrayField = "originalScores";
			aggregationOperations.add(Aggregation.project(projectionFields)
					.and(ArrayOperators.Filter.filter("$" + SCORES_FIELD).as(thisString).by(ComparisonOperators
							.valueOf(thisString + "." + SURVEY_NAME_FIELD).equalToValue(orderBySurveyScore)))
					.as(tmpScoreArrayField));

			// 3. Projection: creates a new field with the max score for the
			// survey.
			String tmpMaxScoreField = "maxScore";
			aggregationOperations.add(Aggregation.project(projectionFields)
					.and(AccumulatorOperators.Max.maxOf(tmpScoreArrayField + "." + SCORE_FIELD)).as(tmpMaxScoreField));

			// 4. Sort by the temp max field.
			aggregationOperations.add(Aggregation.sort(new Sort(Sort.Direction.DESC, tmpMaxScoreField)));
		}

		// 5. Skip.
		aggregationOperations.add(Aggregation.skip(skip));
		// 6. Limit.
		aggregationOperations.add(Aggregation.limit(limit));

		// Executes the aggregation and returns the result.
		return mongoTemplate.aggregate(Aggregation.newAggregation(User.class, aggregationOperations), User.class)
				.getMappedResults();
	}

	/**
	 * Counts the users than matches the given criteria. The users found, if
	 * any, will match every non-null field passed as argument.
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
	 * @param filterBySurvey
	 *            the filter to use for surveys score. Only users which have
	 *            completed that survey will be returned
	 * @return the number of users that matches the given criteria
	 */
	public long countSearchUserTotal(String name, String surname, String email, LocalDate birthday, String technology,
			LocalDate dateTakenStart, LocalDate dateTakenEnd, String filterBySurvey) {
		LOG.debug("UserDao.searchUser(*, *, *, *, {}, {}, {}, {})", technology, dateTakenStart, dateTakenEnd,
				filterBySurvey);
		Query query = new Query(buildSearchUserCriteria(name, surname, email, birthday, technology, dateTakenStart,
				dateTakenEnd, filterBySurvey));
		return mongoTemplate.count(query, User.class);
	}

	/**
	 * Builds a criteria that matches one or more users according to the
	 * arguments. The users found, if any, will match every non-null field
	 * passed as argument.
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
	 * @param filterBySurvey
	 *            the filter to use for surveys score. Only users which have
	 *            completed that survey will be returned
	 * @return a query that checks for the passed parameters
	 */
	private Criteria buildSearchUserCriteria(String name, String surname, String email, LocalDate birthday,
			String technology, LocalDate dateTakenStart, LocalDate dateTakenEnd, String filterBySurvey) {
		LOG.debug("UserDao.searchUser(*, *, *, *, {}, {}, {}, {})", technology, dateTakenStart, dateTakenEnd,
				filterBySurvey);

		// Ignores admin users and adds filters to the query according to the
		// passed parameters.
		Criteria criteria = Criteria.where(AUTHORITIES_FIELD).not()
				.elemMatch(Criteria.where(ROLE_FIELD).is(ROLE_ADMIN));
		List<Criteria> andCriteria = new ArrayList<Criteria>();
		if (name != null && !name.isEmpty()) {
			LOG.debug("Aggiunto filtro per nome [*] alla query.");
			andCriteria.add(Criteria.where(NAME_FIELD).is(name));
		}
		if (surname != null && !surname.isEmpty()) {
			LOG.debug("Aggiunto filtro per cognome [*] alla query.");
			andCriteria.add(Criteria.where(SURNAME_FIELD).is(surname));
		}
		if (email != null && !email.isEmpty()) {
			LOG.debug("Aggiunto filtro per email [*] alla query.");
			andCriteria.add(Criteria.where(Fields.UNDERSCORE_ID).is(email));
		}
		if (birthday != null) {
			LOG.debug("Aggiunto filtro per compleanno [*] alla query.");
			andCriteria.add(Criteria.where(BIRTHDAY_FIELD).is(birthday));
		}
		if (technology != null && !technology.isEmpty()) {
			LOG.debug("Aggiunto filtro per tecnologia [{}] alla query.", technology);
			andCriteria.add(Criteria.where(TECHNOLOGY_FIELD).is(technology));
		}
		if (dateTakenStart != null && dateTakenEnd != null) {
			LOG.debug("Aggiunto filtro per data inizio questionari [{}] e data fine questionari [{}] alla query.",
					dateTakenStart, dateTakenEnd);
			andCriteria.add(Criteria.where(TIME_TAKEN_SUBFIELD).gte(dateTakenStart).lte(dateTakenEnd));
		} else {
			if (dateTakenStart != null) {
				LOG.debug(
						"Aggiunto filtro per data questionari [{}] alla query con valore del campo data inizio questionari.",
						dateTakenStart);
				andCriteria.add(Criteria.where(TIME_TAKEN_SUBFIELD).is(dateTakenStart));
			}
			if (dateTakenEnd != null) {
				LOG.debug(
						"Aggiunto filtro per data questionari [{}] alla query con valore del campo data fine questionari.",
						dateTakenEnd);
				andCriteria.add(Criteria.where(TIME_TAKEN_SUBFIELD).is(dateTakenEnd));
			}
		}

		// [v1.1.0] Filters by surveys.
		if (filterBySurvey != null) {
			LOG.debug("Aggiunto filtro per punteggio questionario [{}] presente alla query.", filterBySurvey);
			andCriteria
					.add(Criteria.where(SCORES_FIELD).elemMatch(Criteria.where(SURVEY_NAME_FIELD).is(filterBySurvey)));
		}

		return criteria.andOperator(andCriteria.toArray(new Criteria[0]));
	}

	/**
	 * Removes all the users from the collection.
	 */
	public void deleteAll() {
		LOG.warn("UserDao.deleteAll()");
		mongoTemplate.getCollection(COLLECTION_NAME).drop();
	}

	/**
	 * Inserts a new user into the collection.
	 *
	 * @param user
	 *            the user to insert
	 */
	public void insertUser(User user) {
		LOG.trace("UserDao.insertUser(*)");
		mongoTemplate.insert(user);
	}

	/**
	 * Updates one user.
	 * 
	 * @param user
	 *            the user to update
	 */
	public void updateUser(User user) {
		LOG.info("UserDao.updateUser(*)");
		mongoTemplate.save(user);
	}

	/**
	 * Gets a user by its email.
	 *
	 * @param email
	 *            the email of the user to retrieve
	 * @return a user
	 */
	public User getUserByEmail(String email) {
		LOG.trace("UserDao.getUserByEmail(*)");
		Query query = new Query();
		query.addCriteria(Criteria.where(Fields.UNDERSCORE_ID).is(email));
		return mongoTemplate.findOne(query, User.class);
	}

	/**
	 * Deletes a survey score for an user.
	 *
	 * @param email
	 *            the email of the user whose score needs to be deleted
	 * @param surveyName
	 *            the email of the user to retrieve
	 * @param timeTaken
	 *            the time at which the survey as been taken, used as id along
	 *            with the survey name
	 * @return the outcome of the operation
	 */
	public boolean deleteUserSurveyScore(String email, String surveyName, LocalDateTime timeTaken) {
		LOG.warn("UserDao.deleteUserSurveyScore(*, {}, {})", surveyName, timeTaken);
		Query query = new Query();
		query.addCriteria(Criteria.where(Fields.UNDERSCORE_ID).is(email));
		Update update = new Update();

		// Builds the key for the update.
		Map<String, Object> updateKey = new HashMap<String, Object>();
		updateKey.put(SURVEY_NAME_FIELD, surveyName);
		updateKey.put(TIME_TAKEN_FIELD, timeTaken);
		update.pull(SCORES_FIELD, new BasicDBObject(updateKey));

		mongoTemplate.updateFirst(query, update, User.class);
		return true;
	}

	/**
	 * Updates the user last update time field with the current time.
	 *
	 * @param email
	 *            the email of the user to update
	 * @param time
	 *            the last update time to save
	 * @return the outcome of the operation
	 */
	public boolean saveUserUpdateTime(String email, LocalDateTime time) {
		LOG.trace("UserDao.saveUserUpdateTime(*)");
		Query query = new Query();
		query.addCriteria(Criteria.where(Fields.UNDERSCORE_ID).is(email));
		Update update = new Update();
		update.set(UPDATE_TIME_FIELD, time);
		WriteResult result = mongoTemplate.updateFirst(query, update, User.class);
		return result.getN() == 1;
	}

	/**
	 * Checks that a user with a given email is enabled.
	 * 
	 * @param userEmail
	 *            the email to check
	 * @return true if the account is enabled, false otherwise
	 */
	public boolean checkAccountEnabled(String userEmail) {
		LOG.warn("UserDao.checkAccountEnabled({})", userEmail);
		return mongoTemplate.findById(userEmail, User.class).isEnabled();
	}

}