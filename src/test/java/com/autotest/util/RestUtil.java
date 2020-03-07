package com.autotest.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.autotest.pojo.Rest;

import static com.autotest.util.ExcelUtil.*;

public class RestUtil {
	//创建日志记录器logger
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	//保存所有接口信息的infoList(每一行数据作为一个对象)
	public static List<Rest> restList = new ArrayList<Rest>();
	//静态restClass对象用来使用反射
	public static Class<Rest> restClass = Rest.class;	
	//通过静态代码块在本类被加载时就将数据解析封装到restList中
	static{
		logger.info("加载接口信息");
		restList = ExcelUtil.load(EXCEL_PATH,REST_SHEET_NAME,restClass);
	}
	
	/**
	 * 根据指定apiId返回对应的url
	 * @param apiId
	 * @return
	 */
	public static String getUrlByApiId(String apiId) {
		//遍历restList集合匹配
		for(Rest rest:restList){
			//根据指定的apiId获取值
			if (rest.getApiId().equals(apiId)){
				return rest.getUrl();
			}
		}
		return null;
	}
	/**
	 * 根据指定apiId返回对应的methodType
	 * @param apiId
	 * @return
	 */
	public static String getMethodTypeByApiId(String apiId) {
		//遍历restList集合匹配
		for(Rest rest:restList){
			//根据指定的apiId获取值
			if (rest.getApiId().equals(apiId)){
				return rest.getMethodType();
			}
		}
		return null;
	}
}
