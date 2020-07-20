package co.aurasphere.interview.server.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import co.aurasphere.interview.server.dao.SurveyDao;
import co.aurasphere.interview.server.dao.TechnologyDao;
import co.aurasphere.interview.server.dao.UserDao;
import co.aurasphere.interview.server.model.Question;
import co.aurasphere.interview.server.model.Survey;
import co.aurasphere.interview.server.model.Technology;
import co.aurasphere.interview.server.model.User;

/**
 * Utility class for loading the database with surveys, users and technologies
 * data.
 * 
 * @author Donato Rimenti
 */
public class DataManagerUtils {

	/**
	 * Admin role.
	 */
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	/**
	 * User role.
	 */
	private static final String ROLE_USER = "ROLE_USER";

	/**
	 * Java technology.
	 */
	private final static String TECHNOLOGY_JAVA = "Java";

	/**
	 * .NET technology.
	 */
	private static final String TECHNOLOGY_DOT_NET = ".NET";

	/**
	 * Ruby technology.
	 */
	private static final String TECHNOLOGY_RUBY = "Ruby";

	/**
	 * Dao for surveys.
	 */
	private static SurveyDao surveyDao;

	/**
	 * Dao for users.
	 */
	private static UserDao userDao;

	/**
	 * Dao for technologies.
	 */
	private static TechnologyDao technologyDao;

	/**
	 * The main method.
	 *
	 * @param args
	 *            null
	 * @throws InterruptedException
	 */
	public static void main(String... args) throws InterruptedException {
		// Setup for the application.
		setUp();

		// Clears the DB.
		surveyDao.deleteAll();
		// userDao.deleteAll();

		// Insert surveys.
		insertJavaBaseSurvey();
		insertJava8Survey();
		insertJavaMiddleSeniorSurvey();
		insertSpringSurvey();

		// Insert users.
//		insertAdminUser();
//		insertMockUsers(100);

		// Insert technologies.
//		insertTechnologies();

		// Waits for any pending operations.
		Thread.sleep(5000);
	}

	/**
	 * Loads the Spring context and used beans.
	 */
	public static void setUp() {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/interview-servlet.xml");
		surveyDao = context.getBean(SurveyDao.class);
		userDao = context.getBean(UserDao.class);
		technologyDao = context.getBean(TechnologyDao.class);
	}

	/**
	 * Inserts technologies into DB.
	 */
	private static void insertTechnologies() {
		Technology java = new Technology(TECHNOLOGY_JAVA);
		technologyDao.insert(java);

		Technology dotNet = new Technology(TECHNOLOGY_DOT_NET);
		technologyDao.insert(dotNet);

		Technology ruby = new Technology(TECHNOLOGY_RUBY);
		technologyDao.insert(ruby);
	}

	// *********************************************************************************************
	// User insertion methods
	// *********************************************************************************************

	/**
	 * Inserts some mock Java users into DB.
	 * 
	 * @param number
	 *            the number of users to insert
	 */
	private static void insertMockUsers(int number) {
		for (int i = 0; i < number; i++) {
			User user = new User();
			user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(ROLE_USER)));
			user.setBirthday(LocalDate.now());
			user.setCreationTime(LocalDateTime.now());
			user.setEmail(Integer.toString(i));
			user.setName(Integer.toString(i));
			user.setSurname(Integer.toString(i));
			user.setTechnology(TECHNOLOGY_JAVA);

			userDao.insertUser(user);
		}
	}

	/**
	 * Inserts an admin user into DB.
	 */
	private static void insertAdminUser() {
		User user = new User();
		user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority(ROLE_ADMIN)));
		user.setCreationTime(LocalDateTime.now());
		user.setEmail("interview@aurasphere.co");
		user.setPassword("interview");

		userDao.insertUser(user);
	}

	// *********************************************************************************************
	// Survey insertion methods
	// *********************************************************************************************

	/**
	 * Inserts the Java Spring survey into DB.
	 */
	private static void insertSpringSurvey() {
		Survey survey = new Survey();
		survey.setName("Spring");
		Question q1 = new Question("The default scope of bean is 'singleton'.", Arrays.asList("True", "False"), 0);
		Question q2 = new Question("What are the types of injection supported by Spring?",
				Arrays.asList("Constructor Injection, Setter Injection and Interface Injection",
						"Constructor Injection only.",
						"Constructor Injection, Setter Injection before 2.0 and Interface Injection with 2.0 and above version",
						"Constructor Injection and Setter Injection"),
				3);
		Question q3 = new Question("Which event is published when context is initialized?", Arrays.asList(
				"ContextReadyEvent", "ContextInitializedEvent", "ContextRefreshedEvent", "ContextStartedEvent"), 2);
		Question q4 = new Question("Spring event mechanism is based on the standard:",
				Arrays.asList("Observer Design Pattern", "Mediator Design Pattern", "Factory Design Pattern"), 0);
		Question q5 = new Question("What is dependency injection?",
				Arrays.asList(
						"A design pattern where dependent objects are defined in an xml for easy maintainability.",
						"This is a design pattern whereby dependent objects are passed into an object from the outside at the time application is initialized.",
						"A unique feature of spring which allows object to be injected at the time of compilation."),
				1);
		Question q6 = new Question("A bean must have id attribute in beans configuration file.",
				Arrays.asList("True", "False"), 1);
		Question q7 = new Question("What BeanPostProcessor does?",
				Arrays.asList("It processes beans once a bean is initialized.",
						"It defines callback methods that you can implement to provide your own instantiation logic, dependency-resolution logic etc.",
						"It processes beans once a bean is loaded.", "It processes beans once a bean exits."),
				1);
		Question q8 = new Question("What is true about <list> collection configuration elements?",
				Arrays.asList("This helps in wiring a list of values, allowing duplicates.",
						"This helps in wiring a list of values but without any duplicates.",
						"This can be used to inject a collection of name-value pairs where name and value can be of any type.",
						"This can be used to inject a collection of name-value pairs where the name and value are both Strings."),
				0);
		Question q9 = new Question("If a bean is scoped to HTTP request, scope is:",
				Arrays.asList("session", "global-session", "prototype", "request"), 3);
		Question q10 = new Question(
				"Which programming technique allows programmers to modularize crosscutting concerns, or behavior?",
				Arrays.asList("Spring AOP", "Spring DAO", "Spring ORM", "Spring Roo"), 0);

		survey.setQuestions(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10));
		survey.setTechnology(TECHNOLOGY_JAVA);
		survey.setTime(15);
		surveyDao.insertSurvey(survey);
	}

	/**
	 * Inserts the Java Middle Senior survey into DB.
	 */
	private static void insertJavaMiddleSeniorSurvey() {
		Survey survey = new Survey();
		survey.setName("Java Middle Senior");
		Question q1 = new Question("Java EE, the Java Enterprise Edition, is...",
				Arrays.asList("an improved version of the Java Standard Edition for business professionals",
						"a development environment, designed for creating enterprise applications",
						"a platform for enterprise class level, distributed applications",
						"a replacement of the Java Virtual Machine for running internet applications"),
				2);
		Question q2 = new Question("Container services are configured via...",
				Arrays.asList("special configuration methods in Java Access Beans", "deployment descriptors",
						"resource property files", "application server administration configuration"),
				1);
		Question q3 = new Question("Which of the following is NOT true?",
				Arrays.asList(
						"Java EE applications are based on 4 phases: development, assembly, deployment, administration",
						"Java EE applications are autonomic self-managing, self-healing, self-protecting enterprise applications",
						"Java EE applications are split up in multiple tiers: client tier, web tier, EJB tier and integration tier",
						"Java EE applications are typically a combination of application clients, web components and EJBs"),
				1);
		Question q4 = new Question("Which elements are not part of the Java EE specification? (2 answers)",
				Arrays.asList("applets", "Java Mail", "portlets", "Unified Expression Language"), new Integer[] { 0, 2 });
		Question q5 = new Question(
				"Which of the following descriptions of a Java EE web application are correct? (2 answers)",
				Arrays.asList("A Java EE web application may contain servlets and applets",
						"A Java EE web application may contain servlets and EJBs",
						"A Java EE web application may contain JavaServer Pages and JavaBeans",
						"A Java EE web application must contain JavaServer Pages and applets"),
				new Integer[] { 0, 2 });
		Question q6 = new Question("JavaServer Faces...",
				Arrays.asList("are a replacement of JavaServer Pages",
						"are used as a facade for servlets and JavaServer Pages", "is an MVC based web framework",
						"is the new name of the Struts framework"),
				2);
		Question q7 = new Question("Enterprise Java Beans...", Arrays.asList("must be deployed in a Java EE web container",
				"implement server side business components", "are used as a replacement of a relational database",
				"are necessary for the integration with enterprise services"), 1);
		Question q8 = new Question("Java Messaging Service (JMS)...",
				Arrays.asList("allows messages to participate in a distributed transaction",
						"enables the synchronous exchange of messages",
						"is necessary for sending and receiving e-mails",
						"is a non-standard Java EE feature of IBM WebSphere MQ"),
				0);
		Question q9 = new Question("The best way to access a database from a standard Java EE application is...",
				Arrays.asList(
						"based on a dynamic lookup of a datasource via JNDI (Java Naming and Directory Interface)",
						"using a JDBC DriverManager to optimize the connection to the database",
						"working with static SQL statements via SQLJ",
						"developing a customised persistency framework, based on JDBC type 1 drivers"),
				0);
		Question q10 = new Question("Which quote illustrates best the support of web services in Java EE?",
				Arrays.asList(
						"Java EE application servers contain a specific web services container to interact with other web services",
						"Java EE provides the required XML APIs and tools in order to quickly and effectively design, develop, test and deploy web services",
						"The web services support is part of the Java SE, and as such is available in Java EE too.",
						"The Java EE server provides special deployment descriptors for web services"),
				1);
		Question q11 = new Question("The use of a relational database in a Java EE environment is supported best by...",
				Arrays.asList("Bean Managed Persistent Enterprise Entity Beans",
						"POJO Entities with annotations and controlled by an implementation of the Java Persistence Architecture",
						"Container Managed Transactional beans with direct JDBC access",
						"POJO beans and the specification of SQL in the EJB deployment descriptor"),
				1);
		Question q12 = new Question("The EJB specification architecture defines... (2 correct answers)",
				Arrays.asList("Client side security and encryption", "Distributed object components",
						"Relational database components", "Transactional components"),
				new Integer[] { 1, 3 });
		Question q13 = new Question(
				"What type of enterprise bean is used to embody application processing state information?",
				Arrays.asList("javax.ejb.EnterpriseBean", "javax.ejb.MessageBean", "javax.ejb.SessionBean",
						"javax.ejb.EntityBean"),
				2);
		Question q14 = new Question("A Java EE Enterprise Application Archive (EAR) contains typically...",
				Arrays.asList("client modules, web modules, EJB modules and resource adapters",
						"EJB modules and the associated deployment descriptors",
						"web modules and the associated deployment descriptors",
						"all the deployment descriptors for the enterprise modules"),
				0);
		Question q15 = new Question("Which quote about Java EE transaction management is NOT correct?",
				Arrays.asList("Java EE transaction management supports distributed transactions with 2-phase commit",
						"Java EE transaction management supports the Web services - Transaction specification",
						"Java EE transaction management supports the flat transaction model",
						"Java EE transaction management implies by default auto commit"),
				1);
		Question q16 = new Question("Security in Java EE... (2 answers)",
				Arrays.asList("is provided by the Java EE containers",
						"requires the Java Authentication and Authorisation Service (JAAS) on the web tier",
						"is implemented as single sign-on feature, relying on an LDAP server",
						"is based on realms, users, groups and roles"),
				new Integer[] { 0, 3 });
		Question q17 = new Question("Which pattern is NOT defined as a Java EE pattern (or Java EE blueprint)?",
				Arrays.asList("Business Delegate", "Decorator", "Service to Worker", "Data Access Object"), 1);
		Question q18 = new Question("Which is true? (Choose all that apply.)",
				Arrays.asList("\"X extends Y\" is correct if and only if X is a class and Y is an interface",
						"\"X extends Y\" is correct if and only if X is an interface and Y is a class",
						"\"X extends Y\" is correct if X and Y are either both classes or both interfaces",
						"\"X extends Y\" is correct for all combinations of X and Y being classes and/or interfaces"),
				new Integer[] { 2 });
		Question q19 = new Question(
				"Given two files:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">package</span> pkgA<span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">2</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Foo</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">3</span><span class=\"code-default-color\">.</span>     <span class=\"code-primitive\">int</span> a <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number\">5</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">protected</span> <span class=\"code-primitive\">int</span> b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number\">6</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">int</span> c <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number\">7</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">6</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span><br/>                                               <br/><span class=\"code-literal-or-line-number-or-line-number\">3</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">package</span> pkgB<span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">import</span> <span class=\"code-static-import\">pkgA.*</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Baz</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">6</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> main<span class=\"code-default-color\">(</span>String<span class=\"code-default-color\">[]</span> args<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">7</span><span class=\"code-default-color\">.</span>         Foo f <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Foo<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number-or-line-number\">8</span><span class=\"code-default-color\">.</span>         System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">print</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot; &quot;</span> <span class=\"code-default-color\">+</span> f<span class=\"code-default-color\">.</span><span class=\"code-method\">a</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">9</span><span class=\"code-default-color\">.</span>         System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">print</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot; &quot;</span> <span class=\"code-default-color\">+</span> f<span class=\"code-default-color\">.</span><span class=\"code-method\">b</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">10</span><span class=\"code-default-color\">.</span>         System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">print</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot; &quot;</span> <span class=\"code-default-color\">+</span> f<span class=\"code-default-color\">.</span><span class=\"code-method\">c</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">11</span><span class=\"code-default-color\">.</span>     <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number-or-line-number\">12</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span><br/></pre></div><br/>"
						+ "What is the result? (Choose all that apply.)",
				Arrays.asList("5 6 7", "5 followed by an exception", "Compilation fails with an error on line 7",
						"Compilation fails with an error on line 8", "Compilation fails with an error on line 9",
						"Compilation fails with an error on line 10"),
				new Integer[] { 3, 4 });
		Question q20 = new Question(
				"Given:"
				+ " <div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">abstract</span> <span class=\"code-keyword\">interface</span> <span class=\"code-class-name\">Frobnicate</span> <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>String s<span class=\"code-default-color\">);</span><br/> <span class=\"code-default-color\">}</span> </pre></div><br/>"
				+ "Which is a correct class? (Choose all that apply.)",
				Arrays.asList(
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">abstract</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Frob</span> <span class=\"code-keyword\">implements</span> Frobnicate <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">abstract</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>String s<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{}</span><br/> <span class=\"code-default-color\">}</span> </pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">abstract</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Frob</span> <span class=\"code-keyword\">implements</span> Frobnicate <span class=\"code-default-color\">{}</span> </pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Frob</span> <span class=\"code-keyword\">extends</span> Frobnicate <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>Integer i<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{}</span><br/> <span class=\"code-default-color\">}</span> </pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Frob</span> <span class=\"code-keyword\">implements</span> Frobnicate <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>Integer i<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{}</span><br/> <span class=\"code-default-color\">}</span> </pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Frob</span> <span class=\"code-keyword\">implements</span> Frobnicate <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>String i<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{}</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">twiddle</span><span class=\"code-default-color\">(</span>Integer s<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{}</span><br/> <span class=\"code-default-color\">}</span> </pre></div>"),
				new Integer[] { 1 });
		Question q21 = new Question(
				"Given the following:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-literal-or-line-number\">1</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">X</span> <span class=\"code-default-color\">{</span> <span class=\"code-primitive\">void</span> do1<span class=\"code-default-color\">()</span> <span class=\"code-default-color\">{</span> <span class=\"code-default-color\">}</span> <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number\">2</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Y</span> <span class=\"code-keyword\">extends</span> X <span class=\"code-default-color\">{</span> <span class=\"code-primitive\">void</span> do2<span class=\"code-default-color\">()</span> <span class=\"code-default-color\">{</span> <span class=\"code-default-color\">}</span> <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number\">3</span><span class=\"code-default-color\">.</span><br/><span class=\"code-literal-or-line-number\">4</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Chrome</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number\">5</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> main<span class=\"code-default-color\">(</span>String <span class=\"code-default-color\">[]</span> args<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number\">6</span><span class=\"code-default-color\">.</span>         X x1 <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> X<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number\">7</span><span class=\"code-default-color\">.</span>         X x2 <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Y<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number\">8</span><span class=\"code-default-color\">.</span>         Y y1 <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Y<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number\">9</span><span class=\"code-default-color\">.</span>         <span class=\"code-comment\">// insert code here</span><br/><span class=\"code-literal-or-line-number\">10</span><span class=\"code-default-color\">.</span>    <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number\">11</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span> </pre></div><br/>"
						+ "Which, inserted at line 9, will compile? (Choose all that apply.)",
				Arrays.asList("x2.do2();", "(Y)x2.do2();", "((Y)x2).do2();",
						"None of the above statements will compile"),
				new Integer[] { 2 });

		survey.setQuestions(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17,
				q18, q19, q20, q21));
		survey.setTechnology(TECHNOLOGY_JAVA);
		survey.setTime(10);
		surveyDao.insertSurvey(survey);
	}

	/**
	 * Inserts the Java 8 survey into DB.
	 */
	private static void insertJava8Survey() {
		Survey survey = new Survey();
		survey.setName("Java 8 + Design Patterns");
		Question q1 = new Question(
				"What is the output?"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-literal-or-line-number-or-line-number\">2</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">import</span> <span sclass=\"code-static-import\">java.util.*</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">3</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Test2</span><span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> main<span class=\"code-default-color\">(</span>String a<span class=\"code-default-color\">[]){</span><br/><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">.</span>          Map s <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Hashtable<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number-or-line-number\">6</span><span class=\"code-default-color\">.</span>          s<span class=\"code-default-color\">.</span><span class=\"code-method\">put</span><span class=\"code-default-color\">(</span><span class=\"code-keyword\">null</span><span class=\"code-default-color\">,</span><span class=\"code-keyword\">null</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">7</span><span class=\"code-default-color\">.</span>          System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">println</span><span class=\"code-default-color\">(</span>s<span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">8</span><span class=\"code-default-color\">.</span>     <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number-or-line-number\">9</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span> </pre></div><br/>",
				Arrays.asList("null", "NullPointerException", "[null = null]", "[]"), 1);
		Question q2 = new Question(
				"What is the output?"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">import</span> <span class=\"code-static-import\">java.util.*</span><span class=\"code-default-color\">;</span><br/><span class=\"code-literal-or-line-number-or-line-number\">2</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Test4</span><span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">3</span><span class=\"code-default-color\">.</span>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> main<span class=\"code-default-color\">(</span>String a<span class=\"code-default-color\">[]){</span><br/><span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">.</span>         Map s <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> LinkedHashMap<span class=\"code-default-color\">();</span><br/><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">.</span>         s<span class=\"code-default-color\">.</span><span class=\"code-method\">put</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot;1&quot;</span><span class=\"code-default-color\">,</span><span class=\"code-string\">&quot;one&quot;</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">6</span><span class=\"code-default-color\">.</span>         s<span class=\"code-default-color\">.</span><span class=\"code-method\">put</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot;3&quot;</span><span class=\"code-default-color\">,</span><span class=\"code-string\">&quot;three&quot;</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">7</span><span class=\"code-default-color\">.</span>         s<span class=\"code-default-color\">.</span><span class=\"code-method\">put</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot;2&quot;</span><span class=\"code-default-color\">,</span><span class=\"code-string\">&quot;two&quot;</span><span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">8</span><span class=\"code-default-color\">.</span>         System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">println</span><span class=\"code-default-color\">(</span>s<span class=\"code-default-color\">);</span><br/><span class=\"code-literal-or-line-number-or-line-number\">9</span><span class=\"code-default-color\">.</span>     <span class=\"code-default-color\">}</span><br/><span class=\"code-literal-or-line-number-or-line-number\">10</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span> </pre></div><br/>",
				Arrays.asList("[1=one,2=two,3=three]", "NullPointerException", "[1=one,3=three,2=two]", "[]"), 2);
		Question q3 = new Question(
				"How will you synchronize below given HashMap object?<br/>" + 
		"<div class=\"code-block\"><pre class=\"pre-code-block\">HashMap hashmap = <span class=\"code-keyword\">new</span> HashMap();</pre></div><br/>",
				Arrays.asList("Collections.synchronized(hashmap);", "Map.synchronizedMap(hashmap);",
						"Collections.synchronizedMap(hashmap);", "Map.synchronized(hashmap);"),
				2);
		Question q4 = new Question("Which is a valid declaration within an interface?",
				Arrays.asList("protected short stop = 23;", "public Boolean demo(long bow);",
						"final void demo(short stop);", "static char demo(double duty);"),
				1);
		Question q5 = new Question("Can an abstract class define both abstract methods and non-abstract methods?",
				Arrays.asList("No, it must have all one or the other.",
						"Yes, but the child classes do not inherit the abstract methods.",
						"No, it must have all abstract methods.", "Yes, the child classes inherit both."),
				3);
		Question q6 = new Question(
				"For a given code snippet"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">.</span> <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Constructor</span> <span class=\"code-default-color\">{</span><br/><span class=\"code-literal-or-line-number-or-line-number\">2</span><span class=\"code-default-color\">.</span>        <span class=\"code-keyword\">public</span> Constructor <span class=\"code-default-color\">(</span><span class=\"code-primitive\">int</span> x<span class=\"code-default-color\">,</span> <span class=\"code-primitive\">int</span> y<span class=\"code-default-color\">,</span> <span class=\"code-primitive\">int</span> z<span class=\"code-default-color\">)</span><br/><span class=\"code-literal-or-line-number-or-line-number\">3</span><span class=\"code-default-color\">.</span>        <span class=\"code-default-color\">{}</span><br/><span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">.</span> <span class=\"code-default-color\">}</span> </pre></div><br/>"
						+ "Which of the following is considered as overloaded constructor?",
				Arrays.asList("public void Constructor(int x, int y, byte z){}", "private Object Constructor() {}",
						"Constructor() {}", "protected int Constructor(){}"),
				2);
		Question q7 = new Question("Lambda expressions in Java 8 are based on...",
				Arrays.asList("Procedural programming", "Functional programming", "Data programming", "All"), 1);
		Question q8 = new Question("How many methods are there in functional interface in Java 8?",
				Arrays.asList("0", "1", "2", "3"), 1);
		Question q9 = new Question("Stream operations in Java 8 can be divided into...",
				Arrays.asList("Terminal types", "Intermediate types", "All", "None"), 2);
		Question q10 = new Question(
				"Which of these represents a process that accepts one argument and produces a result in Java 8?",
				Arrays.asList("Function", "Process", "Method", "JavaFunctions"), 0);
		Question q11 = new Question("In Java 8 Function is...",
				Arrays.asList("Class", "Interface", "Lambda Expression", "Object"), 1);
		Question q12 = new Question("What can help us in avoiding NullPointerExceptions and null checks in Java 8?",
				Arrays.asList("Optional", "Required", "NotNull", "NotRequired"), 0);
		Question q13 = new Question("Code before Java 8 essentially used to be...",
				Arrays.asList("Declarative", "Imperative", "Subjective", "None"), 1);
		Question q14 = new Question("In Java 8, R apply(T t) is a method of...",
				Arrays.asList("Function", "Process", "Predicate", "None"), 0);
		Question q15 = new Question("Which of these does Stream.map() operates on?",
				Arrays.asList("Class", "Interface", "Predicate", "Function"), 3);
		Question q16 = new Question("Which method can be used to check null on an Optional variable in Java 8?",
				Arrays.asList("isPresent()", "isNullable()", "isPresentable()", "isNotNull()"), 0);
		Question q17 = new Question("Which of these does forEach() operates on?",
				Arrays.asList("Methods", "Consumer", "Producer", "Producer"), 1);
		Question q18 = new Question("\"map\" and \"filter\" are...", Arrays.asList("Parallel operations",
				"Intermediate operations", "Terminal operations", "Initial operations"), 1);
		Question q19 = new Question("Lambda expressions used in Streams should be...", Arrays.asList("Stateful",
				"Stateless", "Either stateful or stateless", "Stateful only if they are thread-safe"), 1);
		Question q20 = new Question(
				"Which design pattern creates a complex object using simple objects and using a step by step approach?",
				Arrays.asList("Facade Pattern", "Builder pattern", "Factory pattern", "Iterator Pattern"), 1);
		Question q21 = new Question("Adapter pattern is also called __________.",
				Arrays.asList("Decorator pattern", "Template pattern", "Wrapper pattern", "State pattern"), 2);
		Question q22 = new Question(
				"To make user use same instance of class across application we should use __________.",
				Arrays.asList("Iterator Pattern", "Command Pattern", "Facade Pattern", "Singleton Pattern"), 3);
		Question q23 = new Question("Which of the following is true for facade pattern?",
				Arrays.asList(
						"The facade class provides basic, simplified services to clients by taking upon itself the job of dealing with a complex sub-system.",
						"The Facade pattern eases interaction between a client and a sub-system of suppliers by providing a simpler interface to the sub-system",
						"All of the above",
						"Sub-systems may be complex, but often clients only need basic services that can be supplied through a simple interface."),
				2);

		survey.setQuestions(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17,
				q18, q19, q20, q21, q22, q23));
		survey.setTechnology(TECHNOLOGY_JAVA);
		survey.setTime(15);
		surveyDao.insertSurvey(survey);
	}

	/**
	 * Inserts the Java base survey into DB.
	 */
	private static void insertJavaBaseSurvey() {
		Survey survey = new Survey();
		survey.setName("Java Base + Database");
		Question q1 = new Question("Una classe non può essere dichiarata...",
				Arrays.asList("Static", "Private", "Default", "Nessuna delle precedenti"), 2);
		Question q2 = new Question("Come si può evitare che una variabile membro diventi serializzata?",
				Arrays.asList("Marcandola private", "Marcandola volatile", "Marcandola transient", "Non è possibile"),
				2);
		Question q3 = new Question("Cos'è un instanceof?",
				Arrays.asList("I metodi di un oggetto", "Un operatore e parola chiave", "Nessuna delle precedenti"), 1);
		Question q4 = new Question("Il seguente frammento di codice Java: "
				+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">class</span> <span class=\"code-class-name\">A</span> <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">main</span><span class=\"code-default-color\">(</span>String<span class=\"code-default-color\">[]</span> args<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>         B b <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> A<span class=\"code-default-color\">();</span><br/>     <span class=\"code-default-color\">}</span><br/><span class=\"code-default-color\">}</span><br/><span class=\"code-keyword\">class</span> <span class=\"code-class-name\">B</span> <span class=\"code-keyword\">extends</span> A <span class=\"code-default-color\">{}</span></pre></div><br/>"
				+ "Quale risultato dà?", Arrays.asList("Compile error", "Runtime Exception", "No error"), 0);
		Question q5 = new Question(
				"Il seguente frammento di codice Java:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-primitive\">float</span> a <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">9</span><span class=\"code-default-color\">/</span><span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">;</span> </pre></div><br/>"
						+ "Quale risultato dà?",
				Arrays.asList("Compilation error: Divisions must be in a try block",
						"Compilation error: DivideByZeroException", "Runtime Exception", "No Error: a is NaN"),
				2);
		Question q6 = new Question(
				"Il seguente frammento di codice Java:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">class</span> <span class=\"code-class-name\">A</span> <span class=\"code-default-color\">{</span><br/>     <span class=\"code-primitive\">int</span> b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">;</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">main</span><span class=\"code-default-color\">(</span>String<span class=\"code-default-color\">[]</span> args<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>             System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">println</span><span class=\"code-default-color\">(</span><span class=\"code-string\">&quot;b is &quot;</span> <span class=\"code-default-color\">+</span> b<span class=\"code-default-color\">);</span><br/>        <span class=\"code-default-color\">}</span><br/><span class=\"code-default-color\">}</span></pre></div><br/>"
						+ "Quale risultato dà?",
				Arrays.asList("Compilation error", "Runtime Error", "Runtime Exception", "Output of b is 1"), 0);
		Question q7 = new Question("Il seguente frammento di codice Java:"
				+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-primitive\">int</span> a <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">3.5</span><span class=\"code-default-color\">;</span></pre></div><br/>"
				+ "Quale risultato dà?",
				Arrays.asList("Compilation error", "Runtime error", "a being 3.5", "a being 3"), 0);
		Question q8 = new Question(
				"Il seguente frammento di codice Java:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-primitive\">int</span> k <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">;</span><br/><span class=\"code-primitive\">int</span> n <span class=\"code-default-color\">=</span> k<span class=\"code-default-color\">;</span><br/><span class=\"code-primitive\">int</span> p <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">;</span><br/><span class=\"code-keyword\">while</span> <span class=\"code-default-color\">(</span>n <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>    p <span class=\"code-default-color\">*=</span> n<span class=\"code-default-color\">;</span><br/>    n<span class=\"code-default-color\">--;</span><br/><span class=\"code-default-color\">}</span><br/>System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">println</span><span class=\"code-default-color\">(</span>k <span class=\"code-default-color\">+</span> <span class=\"code-string\">&quot;! = &quot;</span> <span class=\"code-default-color\">+</span> p<span class=\"code-default-color\">);</span></pre></div><br/>"
						+ "Quale risultato dà?",
				Arrays.asList("Il frammento di codice compila, ma in esecuzione ciclo all'infinito",
						"Il frammento di codice compila e produce a standard output la stringa \"4! = 24\"",
						"Il frammento di codice non compila, il ciclo while deve essere sostituito da un ciclo for",
						"Nessuna delle precedenti affermazioni è corretta"),
				1);
		Question q9 = new Question(
				"Quale è il risultato dell'esecuzione del seguente metodo Java?"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">int</span> <span class=\"code-method-signature-name\">fattoriale</span><span class=\"code-default-color\">(</span><span class=\"code-primitive\">int</span> n<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>     <span class=\"code-primitive\">int</span> p <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">1</span><span class=\"code-default-color\">;</span><br/>     <span class=\"code-keyword\">while</span> <span class=\"code-default-color\">(</span>n <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>         p <span class=\"code-default-color\">=</span> p <span class=\"code-default-color\">*</span> n<span class=\"code-default-color\">;</span><br/>         n <span class=\"code-default-color\">=</span> decrementa<span class=\"code-default-color\">(</span>n<span class=\"code-default-color\">);</span><br/>     <span class=\"code-default-color\">}</span><br/>     <span class=\"code-keyword\">return</span> p<span class=\"code-default-color\">;</span><br/><span class=\"code-default-color\">}</span></pre></div><br/>",
				Arrays.asList("il fattoriale di n se n > 0, altrimenti 1", "sempre 1, per qualsiasi valore di n",
						"il metodo non termina a causa di un ciclo infinito"),
				0);
		Question q10 = new Question(
				"Dato il seguente frammento di codice Java:"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">public</span> <span class=\"code-keyword\">class</span> <span class=\"code-class-name\">Test</span> <span class=\"code-default-color\">{</span><br/>     <span class=\"code-keyword\">public</span> <span class=\"code-keyword\">static</span> <span class=\"code-primitive\">void</span> <span class=\"code-method-signature-name\">main</span><span class=\"code-default-color\">(</span>String<span class=\"code-default-color\">[]</span> args<span class=\"code-default-color\">)</span> <span class=\"code-default-color\">{</span><br/>         Integer x <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Integer<span class=\"code-default-color\">(</span><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">);</span><br/>         Integer y <span class=\"code-default-color\">=</span> <span class=\"code-keyword\">new</span> Integer<span class=\"code-default-color\">(</span><span class=\"code-literal-or-line-number-or-line-number\">5</span><span class=\"code-default-color\">);</span><br/>         System<span class=\"code-default-color\">.</span><span class=\"code-method\">out</span><span class=\"code-default-color\">.</span><span class=\"code-method\">println</span><span class=\"code-default-color\">(</span>x <span class=\"code-default-color\">==</span> y<span class=\"code-default-color\">);</span><br/>           <span class=\"code-default-color\">}</span><br/><span class=\"code-default-color\">}</span></pre></div><br/>"
						+ "Che cosa restituisce x==y?",
				Arrays.asList("Errore di compilazione", "Runtime Exception", "True", "False"), 3);
		Question q11 = new Question(
				"Quali, tra i seguenti frammenti di codice JavaScript, attribuiscono alla variabile  b un valore maggiore di 0, assumendo che i valori di a e c siano rispettivamente 5 e 0?",
				Arrays.asList(
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">if</span> <span class=\"code-default-color\">(</span>a <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span><br/>     <span class=\"code-keyword\">if</span> <span class=\"code-default-color\">(</span>c <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span> b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">100</span><span class=\"code-default-color\">;</span></pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">if</span> ((c <span class=\"code-default-color\">&gt;=</span> a) <span class=\"code-default-color\">||</span> (c <span class=\"code-default-color\">&gt;=</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span>)) b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">100</span>;<br/> <span class=\"code-keyword\">else</span> b <span class=\"code-default-color\">=</span> <span class=\"code-default-color\">-</span><span class=\"code-literal-or-line-number-or-line-number\">100</span>;</pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">if</span> <span class=\"code-default-color\">(</span>a <span class=\"code-default-color\">&gt;</span> c <span class=\"code-default-color\">&amp;&amp;</span> c <span class=\"code-default-color\">&gt;=</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span> b <span class=\"code-default-color\">=</span> <span class=\"code-default-color\">-</span><span class=\"code-literal-or-line-number-or-line-number\">100</span><span class=\"code-default-color\">;</span><br/> <span class=\"code-keyword\">else</span> b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">100</span><span class=\"code-default-color\">;</span></pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-keyword\">if</span> <span class=\"code-default-color\">(</span>c <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">0</span><span class=\"code-default-color\">)</span><br/>     <span class=\"code-keyword\">if</span> <span class=\"code-default-color\">(</span>a <span class=\"code-default-color\">&gt;</span> <span class=\"code-literal-or-line-number-or-line-number\">4</span><span class=\"code-default-color\">)</span> b <span class=\"code-default-color\">=</span> <span class=\"code-literal-or-line-number-or-line-number\">100</span> <span class=\"code-keyword\">else</span> b <span class=\"code-default-color\">=</span> <span class=\"code-default-color\">-</span><span class=\"code-literal-or-line-number-or-line-number\">100</span><span class=\"code-default-color\">;</span></pre></div>"),
				1);
		Question q12 = new Question(
				"Dato il seguente foglio di stile esterno:<br/>"
						+ "<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-html-tag\">div</span><span class=\"code-css-class-name\">.rosso</span> {<span class=\"code-css-attribute \">color</span><span class=\"code-default-color\">:</span> <span class=\"code-css-attribute\">red</span>;}<br/><span class=\"code-html-tag\">li</span><span class=\"code-css-class-name\">.ridotto</span> {<span class=\"code-css-attribute\">font-size</span><span class=\"code-default-color\">:</span> <span class=\"code-css-number\">80</span><span class=\"code-default-color\">%</span>;}<br/><span class=\"code-html-tag\">li</span><span class=\"code-css-class-name\">.grande</span> {<span class=\"code-css-attribute\">font-size</span><span class=\"code-default-color\">:</span> <span class=\"code-css-number\">120</span><span class=\"code-default-color\">%</span>;}</pre></div><br/>"
						+ "Indicare quale dei seguenti frammenti di codice HTML lo utilizzano correttamente:",
				Arrays.asList(
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-html-tag\">&lt;ul&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li</span> <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”rosso”</span><span class=\"code-html-tag\">&gt;</span> primo item <span class=\"code-html-tag\">&lt;/li&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li&gt;</span> secondo item <span class=\"code-html-tag\">&lt;/li&gt;</span><br/><span class=\"code-html-tag\">&lt;/ul&gt;</span></pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-html-tag\">&lt;ul&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li</span> <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”ridotto”</span><span class=\"code-html-tag\">&gt;</span> primo item <span class=\"code-html-tag\">&lt;/li&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li&gt;</span> secondo item <span class=\"code-html-tag\">&lt;/li <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”rosso”</span>&gt;</span><br/><span class=\"code-html-tag\">&lt;/ul&gt;</span></pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-html-tag\">&lt;ul&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li</span> <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”ridotto”</span><span class=\"code-html-tag\">&gt;</span> primo item <span class=\"code-html-tag\">&lt;/li&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li&gt;</span> secondo <span class=\"code-html-tag\">&lt;div </span><span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”rosso”</span><span class=\"code-html-tag\">&gt;</span> item <span class=\"code-html-tag\">&lt;/div&gt;</span><span class=\"code-html-tag\">&lt;/li &gt;</span><br/><span class=\"code-html-tag\">&lt;/ul&gt;</span></pre></div><br/>",
						"<div class=\"code-block\"><pre class=\"pre-code-block\"><span class=\"code-html-tag\">&lt;ul <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”ridotto”</span>&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li</span> <span class=\"code-html-tag-attribute\">class=</span><span class=\"code-html-tag-attribute-value\">”ridotto”</span><span class=\"code-html-tag\">&gt;</span> primo item <span class=\"code-html-tag\">&lt;/li&gt;</span><br/>     <span class=\"code-html-tag\">&lt;li&gt;</span> secondo item <span class=\"code-html-tag\">&lt;/li &gt;</span><br/><span class=\"code-html-tag\">&lt;/ul&gt;</span></pre></div>"),
				2);
		Question q13 = new Question("Row is synonymous with the term:",
				Arrays.asList("Record.", "relation.", "column.", "field."), 0);
		Question q14 = new Question(
				"Which of the following is a group of one or more attributes that uniquely identifies a row?",
				Arrays.asList("Key", "Determinant", "Tuple", "Relation"), 0);
		Question q15 = new Question(
				"When the values in one or more attributes being used as a foreign key must exist in another set of one or more attributes in another table, we have created a(n):",
				Arrays.asList("transitive dependency.", "insertion anomaly.", "referential integrity constraint.",
						"normal form."),
				3);
		Question q16 = new Question("A key:", Arrays.asList("must always be composed of two or more columns.",
				"can only be one column.", "identifies a row.", "identifies a column."), 2);
		Question q17 = new Question("SQL views can be used to hide:",
				Arrays.asList("columns and rows only.", "complicated SQL syntax only.",
						"both of the above can be hidden by an SQL view.", "None of the above is correct"),
				2);
		Question q18 = new Question("In a 1:N relationship, the foreign key is placed in:",
				Arrays.asList("either table without specifying parent and child tables.", "the parent table.",
						"the child table.", "either the parent table or the child table."),
				2);
		Question q19 = new Question("A primary key should be defined as:", Arrays.asList("NULL.", "NOT NULL.",
				"Either of the above can be used.", "None of the above are correct."), 1);
		Question q20 = new Question("A UNION query is which of the following?",
				Arrays.asList(
						"Combines the output from no more than two queries and must include the same number of columns.",
						"Combines the output from no more than two queries and does not include the same number of columns.",
						"Combines the output from multiple queries and must include the same number of columns.",
						"Combines the output from multiple queries and does not include the same number of columns."),
				2);

		survey.setQuestions(Arrays.asList(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17,
				q18, q19, q20));
		survey.setTechnology(TECHNOLOGY_JAVA);
		survey.setTime(15);
        surveyDao.insertSurvey(survey);
    }

}