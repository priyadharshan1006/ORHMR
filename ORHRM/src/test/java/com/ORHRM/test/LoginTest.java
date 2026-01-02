package com.ORHRM.test;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ORHRM.base.BassClass;
import com.ORHRM.page.LoginPage;

public class LoginTest extends BassClass{

	private LoginPage loginPage;
	
	@BeforeMethod
	public void setupPages() throws IOException {
		loginPage = new LoginPage(getDriver());
	}
	
	
	
	@Test
	public void ToVeifyLogin() {
		loginPage.login("admin", "admin124");
	}
	
	
}
