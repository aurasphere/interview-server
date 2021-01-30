package co.aurasphere.interview.desktop;

import java.awt.SplashScreen;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for Interview desktop app. Starts an embedded Jetty
 * loading the server WAR, opens the browser and navigates to the first page.
 * 
 * @author Donato Rimenti
 *
 */
public class InterviewDesktop {

	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(InterviewDesktop.class);

	/**
	 * Base folder inside Windows' programs.
	 */
	private static final String AURASPHERE_DIRECTORY_NAME = "Aurasphere";

	/**
	 * App folder.
	 */
	private static final String APP_DIRECTORY_NAME = "Interview";

	/**
	 * Config file name.
	 */
	private static final String CONFIG_FILE_NAME = "desktop-config.properties";

	/**
	 * Server WAR file's default name.
	 */
	private static final String WAR_FILE_DEFAULT_NAME = "interview-server.war";

	/**
	 * Chrome driver default file name.
	 */
	private static final String CHROME_DRIVER_FILE_DEFAULT_NAME = "chromedriver.exe";

	/**
	 * Config key for the server's WAR path.
	 */
	private static final String WAR_PATH_PROP_KEY = "war.path";

	/**
	 * Config key for Chrome driver's path.
	 */
	private static final String CHROME_DRIVER_PATH_PROP_KEY = "chrome.driver.path";

	/**
	 * Config key for the port to use for the embedded server.
	 */
	private static final String SERVER_PORT_PROP_KEY = "server.port";

	/**
	 * Config key for whether or not to use kiosk mode.
	 */
	private static final String KIOSK_PROP_KEY = "kiosk";

	/**
	 * Server's web app context path.
	 */
	private static final String SERVER_CONTEXT_PATH = "/interview-server";

	/**
	 * Main method of this class. Loads the configuration, starts the embedded
	 * server and opens the browser.
	 * 
	 * @param args
	 *            null
	 */
	public static void main(String[] args) {
		LOG.info("Interview Desktop started.");

		// Loads the configuration file.
		LOG.info("Loading configuration file. Checking for [{}] inside [{}] in Windows programs folder.",
				APP_DIRECTORY_NAME, AURASPHERE_DIRECTORY_NAME);
		// Gets the application path accordingly to the current OS bits.
		String programFiles = System.getenv("ProgramFiles(x86)");
		if (programFiles == null) {
			programFiles = System.getenv("ProgramFiles");
		}
		Path appBaseDir = Paths.get(programFiles, AURASPHERE_DIRECTORY_NAME, APP_DIRECTORY_NAME);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(appBaseDir.resolve(CONFIG_FILE_NAME).toFile()));
		} catch (IOException e) {
			LOG.error("Error while loading the configuration file. Defaults values will be used instead.", e);
		}

		// Loads the actual properties.
		String warPath = getPropertyOrDefault(properties, WAR_PATH_PROP_KEY,
				appBaseDir.resolve(WAR_FILE_DEFAULT_NAME).toString());
		String chromeDriverPath = getPropertyOrDefault(properties, CHROME_DRIVER_PATH_PROP_KEY,
				appBaseDir.resolve(CHROME_DRIVER_FILE_DEFAULT_NAME).toString());
		int serverPort = Integer.valueOf(getPropertyOrDefault(properties, SERVER_PORT_PROP_KEY, "8080"));
		boolean useKiosk = Boolean.valueOf(getPropertyOrDefault(properties, KIOSK_PROP_KEY, "false"));

		// Configures and starts the server.
		LOG.info("Starting up Jetty server on port [{}].", serverPort);
		Server server = new Server(serverPort);
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(SERVER_CONTEXT_PATH);
		File warFile = new File(warPath);
		webapp.setWar(warFile.getAbsolutePath());
		server.setHandler(webapp);
		try {
			server.start();
		} catch (Exception e) {
			LOG.error("Error while starting the server.", e);
			System.exit(1);
		}

		// Once ready opens the browser.
		LOG.info("Opening up browser. Chrome driver's path is [{}].", chromeDriverPath);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		// The options used are to enable fullscreen and disable the info bars
		// on top with italian language.
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars", "lang=it", "start-maximized", "disable-smooth-scrolling");
		if (useKiosk) {
			LOG.info("Setting browser to kiosk mode.");
			options.addArguments("kiosk");
		}

		// Hides the splashscreen.
		SplashScreen splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen != null) {
			splashScreen.close();
		}

		// Starts the driver and navigates to the main page.
		ChromeDriver driver = new ChromeDriver(options);
		driver.navigate().to("http://localhost:" + serverPort + SERVER_CONTEXT_PATH + "/index.html");

		// Polls the browser in order to check if it has been closed.
		LOG.info("Starting monitoring thread.");
		Timer monitorTimer = new Timer();
		monitorTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					while (true) {
						// Tries to get the window handles. If it fails, the
						// browser has been closed and an exception will be
						// raised.
						driver.getWindowHandles();
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					LOG.error("InterruptedException on polling thread", e);
					closeDriverAndExit(driver, true);
				} catch (WebDriverException e) {
					// Chrome has been closed, stopping the server.
					LOG.info("Chrome has been closed, stopping the server.");
					try {
						server.stop();
					} catch (Exception e2) {
						LOG.error("Error while stopping the server.", e2);
						closeDriverAndExit(driver, true);
					}
					closeDriverAndExit(driver, false);
				}
			}

		}, 0);
	}

	/**
	 * Closes the Chrome driver to avoid memory leaks and terminates the
	 * program.
	 * 
	 * @param driver
	 *            the driver to close
	 * @param error
	 *            whether an error occured or not
	 */
	private static void closeDriverAndExit(ChromeDriver driver, boolean error) {
		// Closes the Chrome driver to avoid memory leaks.
		driver.quit();
		LOG.info("Chrome driver closed.");

		// Gets the error code and terminates the program.
		int errorCode;
		if (error) {
			errorCode = 1;
		} else {
			errorCode = 0;
		}
		System.exit(errorCode);
		LOG.info("Interview Desktop stopped.");
	}

	/**
	 * Loads a property or its default value if the former is not found.
	 * 
	 * @param properties
	 *            the configuration file
	 * @param key
	 *            the key of the property to load
	 * @param defaultValue
	 *            the default value in case the property is not found
	 * @return a property or its default value
	 */
	private static String getPropertyOrDefault(Properties properties, String key, String defaultValue) {
		LOG.info("Loading property [{}]", key);

		// If the properties are null, returns the default.
		if (properties == null) {
			LOG.warn("Properties not found, using default value [{}] for [{}]", defaultValue, key);
			return defaultValue;
		}

		// Gets the value from the properties.
		String value = (String) properties.get(key);

		// If the property is not found, the default value is returned.
		if (value == null || value.isEmpty()) {
			LOG.warn("Property [{}] not found on configuration file. Using default value: [{}]", key, defaultValue);
			return defaultValue;
		}

		LOG.info("Property [{}] found on configuration file. Using value: [{}]", key, value);
		return value;
	}

}