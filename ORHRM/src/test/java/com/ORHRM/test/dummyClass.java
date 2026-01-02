package com.ORHRM.test;

import org.testng.annotations.Test;

import com.ORHRM.base.BassClass;

public class dummyClass extends BassClass {

	@Test
	public void dummyTest() {
		String Title = driver.getTitle();
		assert  Title.equals("OrangeHRM") : "Test Failed";
		System.out.println("Test Passed");
		
	}
}
