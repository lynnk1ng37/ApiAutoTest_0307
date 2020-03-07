package com.autotest.testcase;

import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.autotest.pojo.WriteBackData;
import com.autotest.util.AssertUtil;
import com.autotest.util.DBUtil;
import com.autotest.util.ExcelUtil;
import com.autotest.util.HttpUtil;
import com.autotest.util.RestUtil;
import com.autotest.util.VariableUtil;

import static com.autotest.util.ExcelUtil.*;
/**
 * 接口测试统一处理类
 * @author Lynn
 *
 */
public class BaseCase {
	//创建日志记录器logger
	public Logger logger = Logger.getLogger(BaseCase.class);
	
	//当前数组保存的是需要从excel表中取出数据的列名，而所有测试接口的列名是一样的，因此抽取出来放到父类里让子类继承
	public String[] cellNames = {EXCEL_DATA_CASE_ID,EXCEL_DATA_APi_ID,EXCEL_DATA_REQUEST_PARAM,
			EXCEL_DATA_EXPECTED,EXCEL_DATA_PRE_VALIDATE_SQL,EXCEL_DATA_AFTER_VALIDATE_SQL};
	/**
	 * 所有的测试接口执行过程是一样的，因此抽取出来，子类继承后只需要提供特定的dataProvider(ApiId)即可
	 * @param caseId
	 * @param apiId
	 * @param requestParam
	 * @param expected
	 * @param preValidateSql
	 * @param afterValidateSql
	 */
	@Test(dataProvider = "datas")
	public void test(String caseId,String apiId,String requestParam,String expected,
			String preValidateSql,String afterValidateSql){
		
		//判断当前用例是否配置接口调用前的验证脚本
		if(preValidateSql!=null && preValidateSql.trim().length()>0) {
			logger.info("接口调用前的数据验证查询");
			//调用将参数化变量名替换为变量值的方法，并返回替换后的结果
			preValidateSql = VariableUtil.replaceVariables(preValidateSql);
			//如果配置，则需要在脚本验证前做查询，并接收查询的结果
			String preValidateResult = DBUtil.doQuery(preValidateSql);
			//将返回的实际结果数据保存到集合中
	    	ExcelUtil.writeBackDatas.add(new WriteBackData(CASE_SHEET_NAME,caseId,EXCEL_DATA_PRE_VALIDATE_RESULT,preValidateResult));
		}
		
		//获取Url和methodType
    	String url = RestUtil.getUrlByApiId(apiId);
    	String methodType = RestUtil.getMethodTypeByApiId(apiId);
    	//调用将参数化变量名替换为变量值的方法，并返回替换后的结果
    	requestParam = VariableUtil.replaceVariables(requestParam);
		
    	//将json格式字符串转换成Map集合
		@SuppressWarnings("unchecked")
		Map<String, String> params = (Map<String, String>) JSONObject.parse(requestParam);
		
    	//调用接口请求方法获取实际结果
		logger.info("调用接口");
    	String actual = HttpUtil.doRequest(url, methodType, params);
    	
    	//判断当前用例是否配置接口调用后的验证脚本
    	if(afterValidateSql!=null && afterValidateSql.trim().length()>0){
    		logger.info("接口调用后的数据验证查询");
    		//调用将参数化变量名替换为变量值的方法，并返回替换后的结果
    		afterValidateSql = VariableUtil.replaceVariables(afterValidateSql);
    		//如果配置，则需要在脚本验证前做查询，并接收查询的结果
    		String afterValidateResult = DBUtil.doQuery(afterValidateSql);
    		//将返回的实际结果数据保存到集合中
        	ExcelUtil.writeBackDatas.add(new WriteBackData(CASE_SHEET_NAME,caseId,EXCEL_DATA_AFTER_VALIDATE_RESULT,afterValidateResult));
    	}
    	
    	//调用断言方法判断是否通过，通过的回写PASS，未通过的回写实际结果
    	String result = AssertUtil.assertEquals(actual,expected);
    	//再将结果数据保存到集合中，套件执行完毕后一次性回写到excel表中
    	ExcelUtil.writeBackDatas.add(new WriteBackData(CASE_SHEET_NAME,caseId,EXCEL_DATA_ACTUAL,result));
	}	
	/**
	 * 批量回写实际结果到excel中
	 */
	@AfterSuite
	public void batchWriteBackDatas(){
		ExcelUtil.batchWriteBackDatas(EXCEL_PATH);
	}
	/**
	 * 关闭各类资源
	 */
	@AfterSuite
	public void closeResources(){
		//关闭ExcelUtil.workbook资源
		if(ExcelUtil.workbook!=null){
			try {
				ExcelUtil.workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
