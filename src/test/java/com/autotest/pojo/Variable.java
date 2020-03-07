package com.autotest.pojo;

public class Variable {
	private String name;
	private String value;
	private String reflectClass;
	private String reflectMethod;
	private String reflectValue;
	private String remark;
	
	public String getReflectClass() {
		return reflectClass;
	}
	public void setReflectClass(String reflectClass) {
		this.reflectClass = reflectClass;
	}
	public String getReflectMethod() {
		return reflectMethod;
	}
	public void setReflectMethod(String reflectMethod) {
		this.reflectMethod = reflectMethod;
	}
	public String getReflectValue() {
		return reflectValue;
	}
	public void setReflectValue(String reflectValue) {
		this.reflectValue = reflectValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Variable [name=" + name + ", value=" + value + ", reflectClass=" + reflectClass + ", reflectMethod="
				+ reflectMethod + ", reflectValue=" + reflectValue + ", remark=" + remark + "]";
	}
		
}
