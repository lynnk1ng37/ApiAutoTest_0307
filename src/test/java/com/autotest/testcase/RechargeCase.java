package com.autotest.testcase;

import org.testng.annotations.DataProvider;

import com.autotest.util.CaseUtil;

/**
 * 充值模块测试用例代码
 */
public class RechargeCase extends BaseCase{
	//用例excel表中对应的ApiId，用来获取对应的接口信息
	public static final String API_ID = "2";
	
	@DataProvider(name = "datas")
	public Object[][] datas(){
		//指定接口编号和要取数据的列名,获取指定接口编号和指定列的数据并存储到二维数组中
		Object[][] datas = CaseUtil.getCaseDataByApiId(API_ID, cellNames);
		//返回数组
		return datas;
	}
}
