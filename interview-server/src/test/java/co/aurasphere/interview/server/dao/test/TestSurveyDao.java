package co.aurasphere.interview.server.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.aurasphere.interview.server.dao.SurveyDao;
import co.aurasphere.interview.server.model.Survey;

/**
 * Test for {@link SurveyDao}.
 * 
 * @author Donato Rimenti
 */
public class TestSurveyDao {

	/**
	 * The dao to test.
	 */
	private SurveyDao surveyDao;

	/**
	 * Sets up the test.
	 */
	@Before
	@SuppressWarnings("resource")
	public void setUp() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("interview-servlet-test.xml");
		surveyDao = context.getBean(SurveyDao.class);
	}

	/**
	 * Tests {@link SurveyDao#getSurveysByTechnology(String)}.
	 */
	@Test
	public void testGetSurveyByTechnology() {
		List<Survey> result = surveyDao.getSurveysByTechnology("Java");

		// Checks that the list is not null.
		Assert.assertNotNull(result);
	}

	/**
	 * Tests {@link SurveyDao#getSurveysByName(String)}.
	 */
	@Test
	public void testGetSurveyByName() {
		String name = "Spring";

		// Checks that answers are not retrieved.
		Survey result = surveyDao.getSurveyByName(name, false);
		Assert.assertNotNull(result);
		Assert.assertEquals(name, result.getName());
		Assert.assertNull("Correct answers have been retrieved!", result.getQuestions().get(0).getCorrectAnswers());

		// Checks that answers are retrieved.
		result = surveyDao.getSurveyByName(name, true);
		Assert.assertNotNull(result);
		Assert.assertEquals(name, result.getName());
		Assert.assertNotNull("Correct answers have not been retrieved!",
				result.getQuestions().get(0).getCorrectAnswers());
		Assert.assertNotEquals("Correct answers have not been retrieved!", 0,
				result.getQuestions().get(0).getCorrectAnswers());
	}

}
