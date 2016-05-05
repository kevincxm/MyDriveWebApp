package com.websystique.springmvc.model;

import java.io.File;
import java.util.Date;

public class MyDriveFile{
	
	public MyDriveFile(File file, String name, String type, long orgSize, long compressedSize){
		this.file = file;
		this.FileName = name;
		this.FileType = type;
		this.FileSize = orgSize;
		this.compressedFileSize = compressedSize;
		this.createdDate = new Date();
	}
	
	private String FileName;
	private String FileType;
	private long FileSize;
	private Date createdDate;
	private File file;
	private long compressedFileSize;
	
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
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public long getCompressedFileSize() {
		return compressedFileSize;
	}
	public void setCompressedFileSize(long compressedFileSize) {
		this.compressedFileSize = compressedFileSize;
	}
	
	
}
