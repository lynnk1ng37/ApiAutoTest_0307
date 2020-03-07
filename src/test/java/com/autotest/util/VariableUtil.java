package com.autotest.util;

import static com.autotest.util.ExcelUtil.EXCEL_PATH;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.autotest.pojo.Variable;
import com.autotest.pojo.WriteBackData;

import static com.autotest.util.ExcelUtil.*;

public class VariableUtil {
	//创建日志记录器logger
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	//保存所有参数化变量名和实际参数值的variableList(每一行数据作为一个对象)
	public static List<Variable> variableList = new ArrayList<Variable>();
	//定义一个存储参数化变量名和实际参数值的集合
	public static Map<String, String> variableValueMapping = new HashMap<String, String>();
	//静态restClass对象用来使用反射
	public static Class<Variable> variableClass = Variable.class;
	
	//通过静态代码块在本类被加载时就将数据解析封装到restList中
	static{
		logger.info("加载参数化变量信息");
		variableList = ExcelUtil.load(EXCEL_PATH,VARIABLE_SHEET_NAME,variableClass);
		//将变量名和变量值存储到Map中
		storeVariableAndValue();
	}
	/**
	 * 将参数化的变量替换为实际参数
	 * @param requestParam
	 */
	public static String replaceVariables(String requestParam) {
		//遍历保存参数化变量名和变量值的集合
		Set<Map.Entry<String, String>> entries = variableValueMapping.entrySet();
		for(Map.Entry<String, String> entry:entries){
			String key = entry.getKey();
			//判断请求参数中是否包含参数化的变量名
			if(requestParam.contains(key)){
				//如果包含，则将变量名替换成变量值
				String value = entry.getValue();
				requestParam = requestParam.replace(key, value);
				logger.info("将参数化变量【"+key+"】替换为【"+value+"】");
			}
		}
		return requestParam;
	}
	//将变量名和变量值存储到Map中
	private static void storeVariableAndValue() {
		//从对象集合中取出数据
		for(Variable variable:variableList){
			String variableName = variable.getName();
			String variableValue = variable.getValue();
			//判断变量值是否为空
			if(variableValue==null||variableValue.trim().length()==0){
				//为空则需要调用反射来动态生成数据，获取反射类名和反射类方法
				String reflectClass = variable.getReflectClass();
				String reflectMethod = variable.getReflectMethod();
				try {
					Class<?> clazz = Class.forName(reflectClass);//获取字节码文件对象
					Object obj = clazz.newInstance();//创建一个本类对象
					Method method = clazz.getMethod(reflectMethod);//根据方法名获取方法
					variableValue = (String) method.invoke(obj);//执行方法返回结果
					//将动态生成的数据保存到集合中，用于回写到excel
					ExcelUtil.writeBackDatas.add(new WriteBackData(VARIABLE_SHEET_NAME,variableName,EXCEL_DATA_REFLECT_VALUE,variableValue));
				} catch (Exception e) {					
					e.printStackTrace();
				}
			}
			//将变量名和变量值存储到Map中
			variableValueMapping.put(variableName, variableValue);
		}
	}
}
