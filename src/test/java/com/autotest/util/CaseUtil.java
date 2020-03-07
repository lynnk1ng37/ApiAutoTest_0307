package com.autotest.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.autotest.pojo.Case;

import static com.autotest.util.ExcelUtil.*;

public class CaseUtil {
	//创建日志记录器logger
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	//保存所有用例数据的caseList(每一行数据作为一个对象)
	public static List<Case> caseList = new ArrayList<Case>();
	//静态caseClass对象用来使用反射
	public static Class<Case> caseClass = Case.class;
	//通过静态代码块在本类被加载时就将数据解析封装到caseList中
	static{
		logger.info("加载用例信息");
		caseList = ExcelUtil.load(EXCEL_PATH,CASE_SHEET_NAME,caseClass);
	}

	/**
	 * 根据接口编号获取对应接口的测试数据
	 * @param apiId
	 * @param cellNames
	 * @return
	 */
	public static Object[][] getCaseDataByApiId(String apiId,String[] cellNames){
		//遍历出符合指定接口编号的用例对象并将其存到集合中
		List<Case> csList = new ArrayList<Case>(); 
		for(Case cs:caseList){
			if (apiId.equals(cs.getApiId())) {
				csList.add(cs);
			}
		}
		//根据集合的长度、列的长度指定二维数组的大小
		Object[][] datas = new Object[csList.size()][cellNames.length];
		//遍历集合中用例对象
		for (int i = 0; i < csList.size(); i++) {
			Case cs = csList.get(i);
			//从对象中取出指定的列
			for (int j = 0; j < cellNames.length; j++) {
				String cellName = cellNames[j];
				//根据列名拼接get方法名
				String bString = cellName.substring(0,1).toUpperCase();
				cellName = bString+cellName.substring(1);
				String methodName = STRJOINT_GET+cellName;	
				try {
					//通过反射获取get方法
					Method method = CaseUtil.caseClass.getMethod(methodName);
					//执行get方法获取属性值
					String value = (String) method.invoke(cs);
					//将属性值存储到数组中
					datas[i][j] = value;
				} catch (Exception e) {		
					e.printStackTrace();
				}
			}
		}
		//返回数组
		return datas;
	}
}
