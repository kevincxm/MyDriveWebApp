package com.websystique.springmvc.model;

public class User {
	public User(String name, String email, String pw){
		this.userName = name;
		this.userEmail = email;
		this.userPW = pw;
	}
	private String userName;
	private String userEmail;
	private String userPW;
	private int totalSpace;
	private int usedSpace;
	
	public int getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(int totalSpace) {
		this.totalSpace = totalSpace;
	}
	public int getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(int usedSpace) {
		this.usedSpace = usedSpace;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPW() {
		return userPW;
	}
	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
}
