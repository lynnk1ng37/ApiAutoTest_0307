package com.autotest.pojo;

public class WriteBackData {
	private String actualResult;
	private String sheetName;
	private String rowIdentifier;
	private String cellName;	
	
	public WriteBackData() {
		super();
		
	}
	public WriteBackData(String sheetName, String rowIdentifier, String cellName, String actualResult) {
		super();
		this.actualResult = actualResult;
		this.sheetName = sheetName;
		this.rowIdentifier = rowIdentifier;
		this.cellName = cellName;
	}
	public String getActualResult() {
		return actualResult;
	}
	public void setActualResult(String actualResult) {
		this.actualResult = actualResult;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public String getRowIdentifier() {
		return rowIdentifier;
	}
	public void setRowIdentifier(String rowIdentifier) {
		this.rowIdentifier = rowIdentifier;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	@Override
	public String toString() {
		return "WriteBackData [actualResult=" + actualResult + ", sheetName=" + sheetName + ", rowIdentifier="
				+ rowIdentifier + ", cellName=" + cellName + "]";
	}
	
	
}
