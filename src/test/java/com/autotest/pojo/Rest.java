package com.autotest.pojo;

public class Rest {
	private String apiId; 
	private String apiName;
	private String methodType;
	private String url;
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Rest [apiId=" + apiId + ", apiName=" + apiName + ", methodType=" + methodType + ", url=" + url + "]";
	}
	
}
