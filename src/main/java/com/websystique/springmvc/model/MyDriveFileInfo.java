package com.websystique.springmvc.model;

import java.util.Date;

public class MyDriveFileInfo {
	
	public MyDriveFileInfo(String name, String type, int size){
		this.FileName = name;
		this.FileType = type;
		this.FileSize = size;
	}
	
	private String FileName;
	private String FileType;
	private int FileSize;
	private Date modifiedDate;
	private Date createdDate;
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	public int getFileSize() {
		return FileSize;
	}
	public void setFileSize(int fileSize) {
		FileSize = fileSize;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}




