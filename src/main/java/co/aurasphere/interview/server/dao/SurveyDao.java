package co.aurasphere.interview.server.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import co.aurasphere.interview.server.model.Survey;

/**
 * Dao for {@link Survey}.
 * 
 * @author Donato Rimenti
 *
 */
@Repository
public class SurveyDao {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(SurveyDao.class);

	/**
	 * The collection name.
	 */
	private final static String COLLECTION_NAME = "survey";

	/**
	 * The technology field.
	 */
	private final static String TECHNOLOGY_FIELD = "technology";

	/**
	 * The question's answers field.
	 */
	private final static String QUESTION_ANSWERS_FIELD = "questions.correctAnswers";

	/**
	 * The name field.
	 */
	private final static String NAME_FIELD = "_id";

	/**
	 * The mongo template.
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Gets the available surveys for a specific technology. Only the names are
	 * returned.
	 *
	 * @param technology
	 *            the technology whose surveys needs to be retrieved
	 * @return a list of surveys
	 */
	public List<Survey> getSurveysByTechnology(String technology) {
		LOG.trace("SurveyDao.getSurveysByTechnology({})", technology);
		Query query = new Query();
		// Gets all the surveys that matches the technology passed as argument
		// and returns only the names.
		query.addCriteria(Criteria.where(TECHNOLOGY_FIELD).is(technology)).fields().include(NAME_FIELD);
		return mongoTemplate.find(query, Survey.class);
	}

	/**
	 * Gets a survey by its name. It doesn't return the answers for that survey.
	 *
	 * @param name
	 *            the name of the survey to retrieve
	 * @param getAnswers
	 *            whether or not to get the answers of the survey
	 * @return a survey
	 */
	public Survey getSurveyByName(String name, boolean getAnswers) {
		LOG.trace("SurveyDao.getSurveyByName({}, {})", name, getAnswers);
		Query query = new Query();
		// Gets a survey that matches the given name.
		query.addCriteria(Criteria.where(NAME_FIELD).is(name));

		// Excludes the answers if they are not required.
		if (!getAnswers) {
			query.fields().exclude(QUESTION_ANSWERS_FIELD);
		}
		return mongoTemplate.findOne(query, Survey.class);
	}

	/**
	 * Removes all the surveys from the collection.
	 */
	public boolean deleteAll() {
		LOG.warn("SurveyDao.deleteAll()");
		mongoTemplate.getCollection(COLLECTION_NAME).drop();
		return true;
	}

	/**
	 * Inserts a new survey into the collection.
	 *
	 * @param survey
	 *            the survey to insert
	 */
	public boolean insertSurvey(Survey survey) {
		LOG.warn("SurveyDao.insertSurvey({})", survey);
		mongoTemplate.insert(survey);
		return true;
	}

}