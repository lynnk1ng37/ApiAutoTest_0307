package com.autotest.pojo;

import java.util.Map;

public class DBQueryResult {
	//sql脚本编号
	private String no;
	//sql脚本执行查询后得到的数据，按照键(表的字段)值(查询出的数据)对存储起来
	private Map<String, Object> columnLabelAndValue;
	
	
	public DBQueryResult(String no, Map<String, Object> columnLabelAndValue) {
		super();
		this.no = no;
		this.columnLabelAndValue = columnLabelAndValue;
	}
	public DBQueryResult() {
		super();
		
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Map<String, Object> getColumnLabelAndValue() {
		return columnLabelAndValue;
	}
	public void setColumnLabelAndValue(Map<String, Object> columnLabelAndValues) {
		this.columnLabelAndValue = columnLabelAndValues;
	}
	@Override
	public String toString() {
		return "DBQueryResult [no=" + no + ", columnLabelAndValue=" + columnLabelAndValue + "]";
	}
	
}
