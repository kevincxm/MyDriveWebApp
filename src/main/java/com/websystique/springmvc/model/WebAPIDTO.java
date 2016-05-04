package com.websystique.springmvc.model;

public class WebAPIDTO {
	private String result;
	private String statusCode;
	private String methodName;
	public enum Status {SUCESS, FAILURE};
	
	public void setMethodName(String m){
		this.methodName = m;
	}
	public String getMethodName(){
		return methodName;
	}
	public void setResult(String r){
		this.result = String.valueOf(r);
	}
	public String getResult(){
		return result;
	}
	public void setStatusCode(String code){
		this.statusCode = code;
	}
	public String getStatusCode(){
		return statusCode;
	}

}