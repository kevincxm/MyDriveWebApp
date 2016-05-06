package com.websystique.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.model.MyDriveFile;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.MyDriveFileInfo;
import com.websystique.springmvc.model.WebAPIDTO;
import com.websystique.springmvc.storage.DBHandler;
import com.websystique.springmvc.storage.FSHandler;
import com.websystique.springmvc.storage.StorageManager;
import com.websystique.springmvc.storage.mongo.MongoDriver;

@RestController
public class WebServerController {

	private HashMap<String, User> userMap = new HashMap<String, User>();
	private HashMap<String, ArrayList<MyDriveFileInfo>> fileMap = new HashMap<String, ArrayList<MyDriveFileInfo>>();
	private static boolean dbON = false;
	public static User user = new User("xiaoming", "kevin@gmail.com", "123");;
	private static String UPLOAD_LOCATION = "C:/local/";
	DBHandler dbHandler = null;
	FSHandler fsHandler = null;
	// TODO: should remove when db is ready
	public WebServerController() {
		loadSystemProperties();
		User kevin = new User("Kevin", "kevin@gmail.com", "123");
		User piyush = new User("Piyush", "piyush@gmail.com", "123");
		userMap.put(kevin.getUserName(), kevin);
		userMap.put(piyush.getUserName(), piyush);

		ArrayList<MyDriveFileInfo> list = null;
		if (dbON)
			list = getAllFiles(user.getUserName());
		else {
			list = new ArrayList<MyDriveFileInfo>();
			MyDriveFileInfo file = new MyDriveFileInfo("i_mark_bold5.png", "png", 1024);
			list.add(file);
			MyDriveFileInfo file1 = new MyDriveFileInfo("mp2", "txt", 8024);
			list.add(file1);
			MyDriveFileInfo file2 = new MyDriveFileInfo("music", "mp3", 1024 * 6);
			list.add(file2);
			MyDriveFileInfo file3 = new MyDriveFileInfo("newspaper", "doc", 7024);
			list.add(file3);
			MyDriveFileInfo file4 = new MyDriveFileInfo("Hero", "image", 1024 * 20);
			list.add(file4);
		}

		fileMap.put("kevin", list);
		fileMap.put("piyush", list);
	}

	@RequestMapping(value = "/api/login/{name}/{pw}", method = RequestMethod.GET, headers = "Accept=application/json")
	public WebAPIDTO login(@PathVariable String name, @PathVariable String pw) throws ParseException {
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("login");
		dto.setResult("bad");
		dto.setStatusCode("404");
		// todo: check the db instead
		if (userMap.containsKey(name)) {
			if (userMap.get(name).getUserPW().equals(pw)) {
				dto.setStatusCode("200");
				dto.setResult("good");
			}
		}
		return dto;
	}

	@RequestMapping(value = "/api/createmember/{memberName}/{memberEmail}/{memberPassword}", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebAPIDTO addMember(@PathVariable String memberName, @PathVariable String memberEmail,
			@PathVariable String memberPassword) throws ParseException {
		System.out.println("reaching addMember API for client :" + memberEmail);
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("addMember");
		dto.setResult("bad");

		if (memberName.isEmpty() || memberName == null || memberEmail.isEmpty() || memberEmail == null
				|| memberPassword.isEmpty() || memberPassword == null) {
			dto.setStatusCode("400");
		} else {
			if (userMap.containsKey(memberName)) {
				dto.setStatusCode("400");
				dto.setResult("user exists!");
			} else {
				User user = new User(memberName, memberEmail, memberPassword);
				userMap.put(memberName, user);
				dto.setStatusCode("200");
				dto.setResult("good");
			}
		}
		return dto;
	}

	@RequestMapping(value = "/api/getFileListById/{userName}/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ArrayList<MyDriveFileInfo> getFileListById(@PathVariable String userName) throws ParseException {
		System.out.println("reaching getFileListById API for client :" + userName);

		ArrayList<MyDriveFileInfo> list = new ArrayList<MyDriveFileInfo>();
		if (dbON)
			list = getAllFiles(user.getUserName());
		else if (fileMap.containsKey(userName)) {
			list = fileMap.get(userName);
		}
		return list;
	}

	@RequestMapping(value = "/api/deleteFile/{userName}/{fileName}/", method = RequestMethod.POST, headers = "Accept=application/json")
	public WebAPIDTO deleteFileByName(@PathVariable String userName, @PathVariable String fileName)
			throws ParseException {
		System.out.println(
				"reaching deleteFileByName API for client :" + userName + "and try to delete the file: " + fileName);
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("deleteFile");
		dto.setResult("bad");
		dto.setStatusCode("400");
		// For piyush, add the db logic here, if success, use following two
		// lines.
		if (deleteFile(userName, fileName))
			dto.setResult("good");
		dto.setStatusCode("200");

		return dto;
	}

	public ArrayList<MyDriveFileInfo> getAllFiles(String userName) 
	{	
		System.out.println("File to be fetched for user "+userName);
		StorageManager sm = new StorageManager(dbON, userName, UPLOAD_LOCATION);
		ArrayList<MyDriveFileInfo> retList = null;
		if(dbON)
		{
			dbHandler = sm.getDBHandler();
			retList = dbHandler.getAllFiles(userName);
		}
		else
		{
			fsHandler = sm.getFSHandler();
			retList = fsHandler.getAllFiles(userName);
		}
		
		return retList;
	}
	
	private boolean loadSystemProperties() 
	{
		boolean flag = false;
		try 
		{
			File file = new File("properties/system.properties");
			System.out.println(file.getAbsolutePath());
			FileInputStream fis = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fis);
			fis.close();
			Enumeration<Object> keys = properties.keys();
			while(keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				String val = properties.getProperty(key);
				if(key.equals("localFilePath"))
					UPLOAD_LOCATION = val;
				else if(key.equals("db"))
				{
					if(val.equalsIgnoreCase("ON"))
						dbON = true;
				}
			}
			if(UPLOAD_LOCATION==null || UPLOAD_LOCATION.equals(""))
			{
				System.out.println("System properties missing! Check configurations!!");
				//_logger.error("System properties missing! Check configurations!!");
				flag = false;
			}
			else
			{
				//checkAndCreateDirectory();
				flag = true;
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean deleteFile(String userName, String fileName)
	{
		System.out.println("File to be deleted is "+fileName);
		StorageManager sm = new StorageManager(dbON, userName, UPLOAD_LOCATION);
		try
		{
			if(dbON)
			{
				dbHandler = sm.getDBHandler();
				dbHandler.deleteFile(userName, fileName);
			}
			else
			{
				fsHandler = sm.getFSHandler();
				fsHandler.deleteFile(userName, fileName);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
