package com.autotest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

public class JDBCUtil {
	//创建日志记录器logger
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	
	public static final String JDBC_PROPERTIES_PATH = "src/test/java/com/autotest/config/jdbc.properties";
	
    //定义一个全局数据库连接器
    private static Connection connection = null;

    //根据指定sql和可变参数进行数据库查询
    public static Map<String,Object> sqlQuery(String sql, Object ... values) {
    	//创建一个保存数据库查询结果的集合
        Map<String, Object> columnLabelAndValues = null;
        try {
            //连接数据库
            setConnection();
            //获取一个关联sql语句的PreparedStatement对象（此对象可以操作数据库）
            PreparedStatement statement = connection.prepareStatement(sql);
            //设置占位符的值，占位符索引从1开始
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i+1,values[i]);
            }
            //调用查询方法，返回一个结果集：ResultSet
            ResultSet resultSet = statement.executeQuery();
            //获取查询sql中的查询字段
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取总列数（字段）
            int columnCount = metaData.getColumnCount();
            //初始化保存数据库查询结果的集合
            columnLabelAndValues = new LinkedHashMap<String, Object>();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    //根据每一列获取到字段名
                    String columnName = metaData.getColumnName(i);
                    //根据字段名获取到值
                    Object columnValue = resultSet.getObject(columnName);
                    //将查询的字段名和值保存到map集合中
                    columnLabelAndValues.put(columnName,columnValue);
                }
            }
        } catch (SQLException e) {
            logger.info("数据库操作失败"+e);
        } finally {
            if(connection!=null){
                try {
                    connection.close();
                    logger.info("数据库连接关闭");
                } catch (SQLException e) {
                    logger.error("数据库连接关闭失败"+e);
                }
            }
        }
        //返回保存数据库查询结果的集合
        return columnLabelAndValues;
    }
    //建立数据库连接
    private static void setConnection() {
    	Properties properties = new Properties();
    	FileInputStream inputStream = null;
        try {
        	//字节流关联JDBC配置文件
            inputStream = new FileInputStream(new File(JDBC_PROPERTIES_PATH));
            //加载配置文件
            properties.load(inputStream);
            //从配置文件中获取对应的配置信息
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            //连接数据库
            connection = DriverManager.getConnection(url,user,password);
            logger.info("数据库连接成功");
        } catch (FileNotFoundException e) {            
            logger.error("找不到JDBC配置文件:"+e);
        } catch (IOException e) {
        	logger.error("加载JDBC配置文件失败:"+e);
        } catch (SQLException e) {
        	logger.error("数据库连接失败"+e);
		} finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                	logger.error("关闭资源【"+inputStream.getClass().getName()+"】失败"+e);
                }
            }
        }
    }
}
