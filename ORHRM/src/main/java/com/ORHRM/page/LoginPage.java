package com.ORHRM.page;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ORHRM.actiondriver.ActionClass;
import com.ORHRM.base.BassClass;

public class LoginPage {
	
	private ActionClass actionClass;
	
	public LoginPage(WebDriver driver) throws IOException {
	this.actionClass = BassClass.getActionClass(); 
	}	
	
	private By userNameField = By.cssSelector("input[name='username']");
	private By passwordField = By.cssSelector("input[type='password']");
	private By loginButton = By.cssSelector("button[type='submit']");
	
	public void login(String userName, String Password) {
		actionClass.enterText(userNameField, userName);
		actionClass.enterText(passwordField, Password);
		actionClass.click(loginButton);
	}
	

}
