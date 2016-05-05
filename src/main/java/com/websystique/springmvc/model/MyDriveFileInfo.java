package com.websystique.springmvc.model;

import java.util.Date;

public class MyDriveFileInfo {
	
	public MyDriveFileInfo(){
		this.FileName = "";
		this.FileType = "";
		this.FileSize = 0l;
		this.compressedFileSize = 0l;
		this.createdDate = null;
	}
	
	public MyDriveFileInfo(String name, String type, long orgfileSize, long compressedFileSize, Date date){
		this.FileName = name;
		this.FileType = type;
		this.FileSize = orgfileSize;
		this.compressedFileSize = compressedFileSize;
		this.createdDate = date;
	}
	
	// for testing only
	public MyDriveFileInfo(String name, String type, long orgfileSize){
		this.FileName = name;
		this.FileType = type;
		this.FileSize = orgfileSize;
	}
	
	private String FileName;
	private String FileType;
	private long FileSize;
	private long compressedFileSize;
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
	public long getFileSize() {
		return FileSize;
	}
	public void setFileSize(long fileSize) {
		FileSize = fileSize;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getCompressedFileSize() {
		return compressedFileSize;
	}
	public void setCompressedFileSize(long compressedFileSize) {
		this.compressedFileSize = compressedFileSize;
	}
	
	
}




