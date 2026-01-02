package com.ORHRM.actiondriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ORHRM.base.BassClass;

public class ActionClass {
	private WebDriver driver;
	private WebDriverWait wait;
	protected static Properties prop;
	public static final Logger Logger = BassClass.Logger;
	
	public ActionClass (WebDriver driver) throws IOException {
				
		this.driver =  driver;
		int explicitWait = Integer.parseInt(BassClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
		Logger.info("WebDriver instance is created");
	}

	private void waitForElementToBeClickable (By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			Logger.info("Clicked the element");
		} catch (Exception e) {
			System.out.println("The Element is not clickable because of : "+e.getMessage() );
			Logger.error("Unable to Click the elements");
		}
	}
	private void waitForElementToBeVisiable (By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			
		} catch (Exception e) {
			System.out.println("The Element is not visible because of : "+e.getMessage());
		}
	}
	
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			System.out.println("The element is unable to click because of : "+e.getMessage());
		}
	}
	
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisiable(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			Logger.info("Entered Text : "+ value);
		} catch (Exception e) {
			Logger.error("Unable to enter the value because of : "+e.getMessage());
		}
	}
	public String getText(By by) {
		try {
			waitForElementToBeVisiable(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			Logger.error("Unable to get the text because of : "+e.getMessage());
			return "";
		}
	}
	
	public void compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisiable(by);
			String actualText = driver.findElement(by).getText();
			if(expectedText.equals(actualText)) {
				System.out.println("The Text are matching with "+expectedText+" to "+actualText);
			}
				else {
					System.out.println("The Text are not matching with "+expectedText+" to "+actualText);
				}
		} catch (Exception e) {
			Logger.error("Unable to compare the Text Because of : "+e.getMessage());
		}	
	}
	
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisiable(by);
			return  driver.findElement(by).isDisplayed();
			
		} catch (Exception e) {
			Logger.error("The element is not displayed because of : "+e.getMessage());
			return false; 
		}
	}
	
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("argument[0], scrollIntoView(true);", element);
		} catch (Exception e) {
			Logger.error("Unable to Locate element because of : "+e.getMessage());
		}
	}
	
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver ->((JavascriptExecutor) WebDriver)
			.executeScript("return document.readyState").equals("complete"));
			Logger.info("Page load successfully");
		} catch (Exception e) {
			Logger.error("Page did not load within "+timeOutInSec+"seconds. Exception: "+e.getMessage());
		}
		
	}
}
