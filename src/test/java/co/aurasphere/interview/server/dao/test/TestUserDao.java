package co.aurasphere.interview.server.dao.test;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.aurasphere.interview.server.dao.UserDao;
import co.aurasphere.interview.server.model.User;

/**
 * Test for {@link UserDao}.
 * 
 * @author Donato Rimenti
 */
public class TestUserDao {

	/**
	 * The dao to test.
	 */
	private UserDao userDao;

	/**
	 * Sets up the test.
	 */
	@Before
	@SuppressWarnings("resource")
	public void setUp() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("interview-servlet-test.xml");
		userDao = context.getBean(UserDao.class);
	}

	/**
	 * Tests {@link UserDao#getUserByEmail(String)}.
	 */
	@Test
	public void testGetUserByEmail() {
		String email = "interview@aurasphere.co";
		User result = userDao.getUserByEmail(email);
		Assert.assertNotNull(result);
	}

	/**
	 * Tests
	 * {@link UserDao#searchUser(String, String, String, java.time.LocalDate, String, java.time.LocalDate, java.time.LocalDate, String, long, int)}.
	 */
	@Test
	public void testSearchUser() {
		// Admin users should not be found.
		String email = "interview@aurasphere.co";
		List<User> result = userDao.searchUser(null, null, email, null, null, null, null, null, 0, 10);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 0);

		// Tries the search by technology.
		String technology = "Java";
		result = userDao.searchUser(null, null, null, null, technology, null, null, null, 0, 10);
		Assert.assertNotNull(result);

		// Checks that the ordering works without exceptions.
		String testSurvey = "Java Middle Senior";
		result = userDao.searchUser(null, null, null, null, technology, null, null, testSurvey, 0, 10);
		Assert.assertNotNull(result);
	}

	/**
	 * Tests
	 * {@link UserDao#countSearchUserTotal(String, String, String, java.time.LocalDate, String, java.time.LocalDate, java.time.LocalDate, String)}.
	 */
	@Test
	public void testCountSearchUser() {
		// Admin users should not be found.
		String email = "interview@aurasphere.co";
		long result = userDao.countSearchUserTotal(null, null, email, null, null, null, null, null);
		Assert.assertNotNull(result);
		Assert.assertEquals(result, 0);

		// Tests the technology search.
		String technology = "Java";
		result = userDao.countSearchUserTotal(null, null, null, null, technology, null, null, null);
		Assert.assertNotNull(result);
	}

	/**
	 * Tests {@link UserDao#deleteUserSurveyScore(String, String)}.
	 */
	@Test
	public void testDeleteUserSurveyScore() {
		String email = "47";
		String surveyName = "Java Middle Senior";
		boolean result = userDao.deleteUserSurveyScore(email, surveyName, LocalDateTime.now());
		Assert.assertTrue(result);
	}

	/**
	 * Tests {@link UserDao#saveUserUpdateTime(String, LocalDateTime)}.
	 */
	@Test
	public void testSaveUserUpdateTime() {
		String email = "47";
		boolean result = userDao.saveUserUpdateTime(email, LocalDateTime.now());
		Assert.assertTrue(result);
	}

}
