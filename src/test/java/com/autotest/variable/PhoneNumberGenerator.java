package com.autotest.variable;

import java.util.Map;

import com.autotest.util.JDBCUtil;
import com.autotest.util.RandomGeneratePhoneNumber;

public class PhoneNumberGenerator {
	/**
	 * 随机生成一个手机号
	 * @return
	 */
	public static String generatePhoneNumber(){
		return RandomGeneratePhoneNumber.randomGeneratePhoneNumber();
	}
	
	/**
	 * 生成未注册的用于注册的手机号
	 * @return
	 */
	public static String generateUnRegisterPhoneNumber(){
		//查询数据库中最大的一个手机号，+1即是不存在
		String sql = "select concat(max(mobilephone)+1,'') as unRegisterPhoneNumber from member";
		Map<String, Object> columnLabelAndValues = JDBCUtil.sqlQuery(sql);
		return (String) columnLabelAndValues.get("unRegisterPhoneNumber");
	}	
	/**
	 * 生成系统中不存在的手机号
	 * @return
	 */
	public static String generateNotExistPhoneNumber(){
		//查询数据库中最大的一个手机号，+1即是不存在
		String sql = "select concat(max(mobilephone)+1000,'') as notExistPhoneNumber from member";
		Map<String, Object> columnLabelAndValues = JDBCUtil.sqlQuery(sql);
		return (String) columnLabelAndValues.get("notExistPhoneNumber");
	}
}
