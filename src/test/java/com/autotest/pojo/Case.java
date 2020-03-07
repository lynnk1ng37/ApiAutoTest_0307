package com.autotest.pojo;

public class Case {
	private String caseId;
	private String apiId;
	private String desc;
	private String requestParam;
	private String expected;
	private String actual;
	private String preValidateSql;
	private String afterValidateSql;
	private String preValidateResult;
	private String afterValidateResult;
	
	
	public String getPreValidateSql() {
		return preValidateSql;
	}

	public void setPreValidateSql(String preValidateSql) {
		this.preValidateSql = preValidateSql;
	}

	public String getAfterValidateSql() {
		return afterValidateSql;
	}

	public void setAfterValidateSql(String afterValidateSql) {
		this.afterValidateSql = afterValidateSql;
	}

	public String getPreValidateResult() {
		return preValidateResult;
	}

	public void setPreValidateResult(String preValidateResult) {
		this.preValidateResult = preValidateResult;
	}

	public String getAfterValidateResult() {
		return afterValidateResult;
	}

	public void setAfterValidateResult(String afterValidateResult) {
		this.afterValidateResult = afterValidateResult;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Case [caseId=" + caseId + ", apiId=" + apiId + ", desc=" + desc + ", requestParam=" + requestParam
				+ ", expected=" + expected + ", actual=" + actual + ", preValidateSql=" + preValidateSql
				+ ", afterValidateSql=" + afterValidateSql + ", preValidateResult=" + preValidateResult
				+ ", afterValidateResult=" + afterValidateResult + "]";
	}	
	
}
