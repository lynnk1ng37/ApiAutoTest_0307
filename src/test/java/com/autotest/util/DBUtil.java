package com.autotest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.autotest.pojo.DBChecker;
import com.autotest.pojo.DBQueryResult;

public class DBUtil {

	public static String doQuery(String validateSql) {
		//创建一个保存查询编号和查询结果的集合
		List<DBQueryResult>  dbQueryResults = new ArrayList<DBQueryResult>();
		//将字符串脚本解析
		List<DBChecker> dbCheckers = JSONObject.parseArray(validateSql,DBChecker.class);
		//根据集合的长度遍历执行
		for(DBChecker dbChecker:dbCheckers){
			//获取no和sql的值
			String no = dbChecker.getNo();
			String sql = dbChecker.getSql();
			//执行sql语句查询获取查询结果
			Map<String, Object> columnLabelAndValues = JDBCUtil.sqlQuery(sql);
			//将no和返回的结果封装到对象中保存起来
			DBQueryResult dbQueryResult = new DBQueryResult(no,columnLabelAndValues);
			dbQueryResults.add(dbQueryResult);
		}
		//将集合转换成字符串并返回
		return JSONObject.toJSONString(dbQueryResults);
	}
}
