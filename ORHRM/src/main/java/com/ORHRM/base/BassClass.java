package com.ORHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.LogManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ORHRM.actiondriver.ActionClass;
import com.ORHRM.utilities.LoggerManager;

import org.apache.logging.log4j.Logger;

public class BassClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionClass actionClass;
	public static final Logger Logger = LoggerManager.getLogger(BassClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// To call and load the config.prperties files
		prop = new Properties();
		FileInputStream file = new FileInputStream("src\\main\\resources\\config.properties");
		prop.load(file);
		Logger.info("Config.properties File Loaded ");
	}

	public void launchBrowser() {

		// initialize the webDriver based on the Browser setup in config.properties file
		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			Logger.info("ChromeDriver instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			Logger.info("EdgeDriver instance is created");
		} else {
			throw new IllegalArgumentException("This" + browser + "is not supported");
		}
	}

	public void configBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));

		// To Maximize the window
		driver.manage().window().maximize();

		// To Navigate to URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to navigate to the URL because of : " + e.getMessage());
		}
	}

	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Test Name : " + this.getClass().getSimpleName());
		launchBrowser();
		configBrowser();
		staticWait(2);

		Logger.info("WebDriver Initialized and Browser Maximized");

		// Initialize the actionClass only once
		if (actionClass == null) {
			actionClass = new ActionClass(driver);
			Logger.info("ActionClass instance is created");
			Logger.trace("This is trace message");
			Logger.debug("This is trace message");
			Logger.info("This is trace message");
			Logger.warn("This is trace message");
			Logger.error("This is trace message");
			Logger.fatal("This is trace message");

		}

	}

	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));

	}

	// To getter method for Webdriver
	public static WebDriver getDriver() {
		if (driver == null) {
			System.out.println("WebDriver id not initialized");
			throw new IllegalStateException("WebDriver id not initialized");
		}
		return driver;
	}

	// To getter method for ActionClass
	public static ActionClass getActionClass() {
		if (actionClass == null) {
			System.out.println("ActionClass id not initialized");
			throw new IllegalStateException("ActionClass id not initialized");
		}
		return actionClass;
	}

	// To setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	// Getter method for Properties

	public static Properties getProp() {
		return prop;
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Failed to quit because of : " + e.getMessage());
			}
		}
		Logger.info("WebDriver instance is closed");
		driver = null;
		actionClass = null;
	}

}
