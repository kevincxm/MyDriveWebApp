/**
 * 
 */
package com.websystique.springmvc.storage;

import java.io.File;
import java.util.ArrayList;

import com.websystique.springmvc.compression.CompressionFactory;
import com.websystique.springmvc.model.MyDriveFile;
import com.websystique.springmvc.model.MyDriveFileInfo;

/**
 * @author Piyush
 *
 */
public class FSHandler {
	
	
	
	public void pushFile(MyDriveFile file, String fileType, String name)
	{
		//
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

	public void deleteFile(String userName, String fileName) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<MyDriveFileInfo> getAllFiles(String userName) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
