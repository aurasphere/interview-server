package co.aurasphere.interview.server.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.aurasphere.interview.server.dao.TechnologyDao;
import co.aurasphere.interview.server.model.Technology;

/**
 * Test for {@link TechnologyDao}.
 * 
 * @author Donato Rimenti
 */
public class TestTechnologyDao {

	/**
	 * The dao to test.
	 */
	private TechnologyDao technologyDao;

	/**
	 * Sets up the test.
	 */
	@Before
	@SuppressWarnings("resource")
	public void setUp() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("interview-servlet-test.xml");
		technologyDao = context.getBean(TechnologyDao.class);
	}

	/**
	 * Tests {@link TechnologyDao#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<Technology> technologies = technologyDao.findAll();

		// Checks that at least a technology is found.
		Assert.assertNotNull(technologies);
		Assert.assertNotEquals(technologies.size(), 0);

	}

}
