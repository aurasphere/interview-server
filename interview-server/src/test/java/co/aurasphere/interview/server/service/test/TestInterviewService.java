package co.aurasphere.interview.server.service.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.model.User;
import co.aurasphere.interview.server.rest.model.CreateUserResponse;
import co.aurasphere.interview.server.rest.model.GetUserSurveyResultResponse;
import co.aurasphere.interview.server.rest.model.SearchUserResponse;
import co.aurasphere.interview.server.service.InterviewService;

/**
 * Test for {@link InterviewService}.
 * 
 * @author Donato Rimenti
 */
public class TestInterviewService {

	/**
	 * The service to test.
	 */
	private InterviewService interviewService;

	/**
	 * Sets up the test.
	 */
	@Before
	@SuppressWarnings("resource")
	public void setUp() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("interview-servlet-test.xml");
		interviewService = context.getBean(InterviewService.class);
	}

	/**
	 * Tests {@link InterviewService#getTechList()}.
	 */
	@Test
	public void testGetTechList() {
		List<String> techList = interviewService.getTechList();
		Assert.assertNotNull(techList);
		Assert.assertNotEquals(techList.size(), 0);
	}

	/**
	 * Tests {@link InterviewService#createUser(User)}.
	 */
	@Test
	public void testCreateUser() {
		// Tests the null validations.
		User user = new User();
		user.setName("test");
		try {
			interviewService.createUser(user);
			Assert.fail("Validation failure. An exception should have been thrown.");
		} catch (NullPointerException e) {
		}

		user.setSurname("test");
		try {
			interviewService.createUser(user);
			Assert.fail("Validation failure. An exception should have been thrown.");
		} catch (NullPointerException e) {
		}

		user.setEmail("test");
		try {
			interviewService.createUser(user);
			Assert.fail("Validation failure. An exception should have been thrown.");
		} catch (NullPointerException e) {
		}

		user.setBirthday(LocalDate.now());
		try {
			interviewService.createUser(user);
			Assert.fail("Validation failure. An exception should have been thrown.");
		} catch (NullPointerException e) {
		}

		user.setName(null);
		user.setTechnology("Java");
		try {
			interviewService.createUser(user);
			Assert.fail("Validation failure. An exception should have been thrown.");
		} catch (NullPointerException e) {
		}

		// Tests that aurasphere.co emails are forbidden.
		user.setName("test");
		user.setEmail("test@aurasphere.co");
		user.setPassword("banane");
		
		CreateUserResponse response = interviewService.createUser(user);
		Assert.assertNotNull(response);
		Assert.assertFalse(response.isOutcome());
	}

	/**
	 * Tests {@link InterviewService#getAvailableSurveys(String, String)}.
	 */
	@Test
	public void testGetAvailableSurveys() {
		String username = "interview@aurasphere.co";
		String technology = "Java";
		Set<String> availableSurveys = interviewService.getAvailableSurveys(username, technology);
		Assert.assertNotNull(availableSurveys);
		Assert.assertNotEquals(availableSurveys.size(), 0);
	}

	/**
	 * Tests {@link InterviewService#getSurveyByName(String)}.
	 */
	@Test
	public void testGetSurveyByName() {
		String name = "Spring";
		Survey survey = interviewService.getSurveyByName(name);

		// Checks that a survey is returned without answers.
		Assert.assertNotNull(survey);
		Assert.assertNull(survey.getQuestions().get(0).getCorrectAnswers());
	}

	/**
	 * Tests
	 * {@link InterviewService#searchUser(String, String, String, LocalDate, String, LocalDate, LocalDate, String, int)}.
	 */
	@Test
	public void testSearchUser() {
		// Admin users should not be found.
		String email = "interview@aurasphere.co";
		SearchUserResponse result = interviewService.searchUser(null, null, email, null, null, null, null, null, 10);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getSearchResults());
		Assert.assertEquals(result.getSearchResults().size(), 0);

		// Tries the search by technology.
		String technology = "Java";
		result = interviewService.searchUser(null, null, null, null, technology, null, null, null, 10);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getSearchResults());
	}

	/**
	 * Tests {@link InterviewService#submitSurvey(String, List, String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testSubmitSurvey() {
		String surveyName = "Spring";
		String userEmail = "test";
		List<Integer[]> userAnswers = new ArrayList<Integer[]>();
		userAnswers.add(new Integer[10]);

		// A NullPointerException should be thrown since this user does not
		// exist.
		interviewService.submitSurvey(surveyName, userAnswers, userEmail);
	}

	/**
	 * Tests {@link InterviewService#deleteUserSurveyScore(String, String)}.
	 */
	@Test
	public void testDeleteUserSurveyScore() {
		String email = "47";
		String surveyName = "Java Middle Senior";
		boolean result = interviewService.deleteUserSurveyScore(email, surveyName, LocalDateTime.now());
		Assert.assertTrue(result);
	}

	/**
	 * Tests
	 * {@link InterviewService#getUserSurveyResult(String, String, LocalDateTime)}.
	 */
	@Test
	public void testGetUserSurveyResult() {
		String email = "47";
		String surveyName = "Java Middle Senior";
		GetUserSurveyResultResponse result = interviewService.getUserSurveyResult(email, surveyName,
				LocalDateTime.now());

		// Checks that the survey with the correct answers is returned.
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getSurvey());
		Assert.assertNotNull(result.getSurvey().getQuestions().get(0).getCorrectAnswers());
	}

	/**
	 * Tests {@link InterviewService#activateAccount(String, String)}.
	 */
	@Test
	public void testActivateAccount() {
		String email = "donatohan.rimenti@gmail.com";
		boolean result = interviewService.activateAccount(email, "aaa");

		// Checks the outcome.
		Assert.assertTrue(result);
	}

	/**
	 * Tests {@link InterviewService#sendConfirmationEmailAgain(String)}.
	 */
	@Test
	public void testSendConfirmationEmailAgain() {
		String email = "donatohan.rimenti@gmail.com";
		
		// Mock request.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new HttpServletRequest() {
			public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
					throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAttribute(String name, Object o) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncSupported() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncStarted() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				return null;
			}
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(String path) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getRemotePort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getRemoteHost() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteAddr() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public String getRealPath(String path) {
				return null;
			}
			@Override
			public BufferedReader getReader() throws IOException {
				return null;
			}
			@Override
			public String getProtocol() {
				return null;
			}
			@Override
			public String[] getParameterValues(String name) {
				return null;
			}
			@Override
			public Enumeration<String> getParameterNames() {
				return null;
			}
			@Override
			public Map<String, String[]> getParameterMap() {
				return null;
			}
			@Override
			public String getParameter(String name) {
				return null;
			}
			@Override
			public Enumeration<Locale> getLocales() {
				return null;
			}
			@Override
			public Locale getLocale() {
				return null;
			}
			@Override
			public int getLocalPort() {
				return 0;
			}
			@Override
			public String getLocalName() {
				return null;
			}
			@Override
			public String getLocalAddr() {
				return null;
			}
			@Override
			public ServletInputStream getInputStream() throws IOException {
				return null;
			}
			@Override
			public DispatcherType getDispatcherType() {
				return null;
			}
			@Override
			public String getContentType() {
				return null;
			}
			@Override
			public long getContentLengthLong() {
				return 0;
			}
			@Override
			public int getContentLength() {
				return 0;
			}
			@Override
			public String getCharacterEncoding() {
				return null;
			}
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			@Override
			public Object getAttribute(String name) {
				return null;
			}
			@Override
			public AsyncContext getAsyncContext() {
				return null;
			}
			@Override
			public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
				return null;
			}
			@Override
			public void logout() throws ServletException {
			}
			@Override
			public void login(String username, String password) throws ServletException {
			}
			@Override
			public boolean isUserInRole(String role) {
				return false;
			}
			@Override
			public boolean isRequestedSessionIdValid() {
				return false;
			}
			@Override
			public boolean isRequestedSessionIdFromUrl() {
				return false;
			}
			@Override
			public boolean isRequestedSessionIdFromURL() {
				return false;
			}
			@Override
			public boolean isRequestedSessionIdFromCookie() {
				return false;
			}
			@Override
			public Principal getUserPrincipal() {
				return null;
			}
			@Override
			public HttpSession getSession(boolean create) {
				return null;
			}
			@Override
			public HttpSession getSession() {
				return null;
			}
			@Override
			public String getServletPath() {
				return null;
			}
			@Override
			public String getRequestedSessionId() {
				return null;
			}
			@Override
			public StringBuffer getRequestURL() {
				return null;
			}
			@Override
			public String getRequestURI() {
				return null;
			}
			@Override
			public String getRemoteUser() {
				return null;
			}
			@Override
			public String getQueryString() {
				return null;
			}
			@Override
			public String getPathTranslated() {
				return null;
			}
			@Override
			public String getPathInfo() {
				return null;
			}
			@Override
			public Collection<Part> getParts() throws IOException, ServletException {
				return null;
			}
			@Override
			public Part getPart(String name) throws IOException, ServletException {
				return null;
			}
			@Override
			public String getMethod() {
				return null;
			}
			@Override
			public int getIntHeader(String name) {
				return 0;
			}
			@Override
			public Enumeration<String> getHeaders(String name) {
				return null;
			}
			@Override
			public Enumeration<String> getHeaderNames() {
				return null;
			}
			@Override
			public String getHeader(String name) {
				return null;
			}
			@Override
			public long getDateHeader(String name) {
				return 0;
			}
			@Override
			public Cookie[] getCookies() {
				return null;
			}
			@Override
			public String getContextPath() {
				return "http://localhost:8080/interview";
			}
			@Override
			public String getAuthType() {
				return null;
			}
			@Override
			public String changeSessionId() {
				return null;
			}
			@Override
			public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
				return false;
			}
		}));
		
		interviewService.sendConfirmationEmailAgain(email);
	}

}