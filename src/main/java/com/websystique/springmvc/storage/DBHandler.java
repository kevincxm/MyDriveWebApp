/**
 * 
 */
package com.websystique.springmvc.storage;

import java.io.File;
import java.util.ArrayList;

import com.websystique.springmvc.compression.CompressionFactory;
import com.websystique.springmvc.model.MyDriveFile;
import com.websystique.springmvc.model.MyDriveFileInfo;
import com.websystique.springmvc.storage.mongo.MongoDriver;

/**
 * @author Piyush
 *
 */


public class DBHandler {
	
	public DBHandler(String user, String loc){
		this.setUser(user);
		this.UPLOAD_LOCATION = loc;
	}
	
	private String user;
	private String UPLOAD_LOCATION;
	
	public void pushFile(MyDriveFile file, String fileType, String name)
	{
		MongoDriver driver = new MongoDriver(name);
		System.out.println("File name is : "+ file.getFileName());
		//driver.insert(fileType, file.getName(), file);
		driver.insert(file);
		driver.disConnect();
	}
	
	public void compressAndPush(File file, String fileType, String userName)
	{
		System.out.println("File compressed is "+file.getAbsolutePath()+".zip");
		CompressionFactory cFactory = new CompressionFactory();
		cFactory.compressUsingGzip(file.getAbsolutePath());
		File compressedfile = new File(file.getAbsoluteFile()+".zip");
		MyDriveFile mdFile = new MyDriveFile(compressedfile, file.getName(),
											fileType, 
											file.length(),
											compressedfile.length());
		pushFile(mdFile, fileType, userName);
	}
	
	public ArrayList<MyDriveFileInfo> getAllFiles(String userName) {
		MongoDriver driver = new MongoDriver(userName);
		ArrayList<MyDriveFileInfo> retList = driver.search("fileDtls");
		driver.disConnect();
		return retList;
	}

	public boolean deleteFile(String userName, String fileName) {
		MongoDriver driver = new MongoDriver(userName);
		boolean retVal = driver.deleteFile(fileName);
		driver.disConnect();
		if (retVal)
			deleteFromWebService(userName, fileName);
		return retVal;
	}

	public void deleteFromWebService(String userName, String fileName) {
		File orgfileToDelete = new File(UPLOAD_LOCATION + fileName);
		File zipfileToDelete = new File(UPLOAD_LOCATION + fileName + ".zip");

		if (orgfileToDelete.exists())
			orgfileToDelete.delete();
		if (zipfileToDelete.exists())
			zipfileToDelete.delete();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
