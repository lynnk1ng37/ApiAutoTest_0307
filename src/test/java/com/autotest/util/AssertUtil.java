package com.autotest.util;

import org.testng.Assert;

public class AssertUtil {
	
	public static String assertEquals(String actual,String expected){
		String result = "PASS";
		try{
			Assert.assertEquals(actual, expected);
		}catch(Throwable t){
			result = actual;
		}
		return result;
	}
}
