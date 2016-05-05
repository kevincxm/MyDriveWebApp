package com.websystique.springmvc.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.model.MyDriveFile;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.MyDriveFileInfo;
import com.websystique.springmvc.model.WebAPIDTO;
import com.websystique.springmvc.mongo.MongoDriver;

@RestController
public class WebServerController {

	private HashMap<String, User> userMap = new HashMap<String, User>();	
	private HashMap<String, ArrayList<MyDriveFileInfo>> fileMap = new HashMap<String, ArrayList<MyDriveFileInfo>>();
	private static boolean dbON = true;
	public static User user = new User("xiaoming", "kevin@gmail.com","123");;
	
	
	// TODO: should remove when db is ready
	public WebServerController(){
		User kevin = new User("Kevin", "kevin@gmail.com","123");
		User piyush = new User("Piyush", "piyush@gmail.com","123");
		userMap.put(kevin.getUserEmail(),kevin);
		userMap.put(piyush.getUserEmail(), piyush);
		
		ArrayList<MyDriveFileInfo> list = null;
		if(dbON)
			list = getAllFiles(user.getUserName());
		else
		{
			list = new ArrayList<MyDriveFileInfo>();
			MyDriveFileInfo file = new MyDriveFileInfo("mp1","pdf",1024);
			list.add(file);
			MyDriveFileInfo file1 = new MyDriveFileInfo("mp2","txt",8024);
			list.add(file1);
			MyDriveFileInfo file2 = new MyDriveFileInfo("music","mp3",1024*6);
			list.add(file2);
			MyDriveFileInfo file3 = new MyDriveFileInfo("newspaper","doc",7024);
			list.add(file3);
			MyDriveFileInfo file4 = new MyDriveFileInfo("Hero","image",1024*20);
			list.add(file4);
		}
		
		fileMap.put("kevin@gmail.com", list);
		fileMap.put("piyush@gmail.com", list);	
	}
	
	@RequestMapping(value="/api/login/{email}/{pw}",method = RequestMethod.GET,headers="Accept=application/json")
	public WebAPIDTO login(@PathVariable String email ,@PathVariable String pw) throws ParseException 
	{ 
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("login");
		dto.setResult("bad");	
		dto.setStatusCode("404");
		// todo: check the db instead
		if(userMap.containsKey(email)){
			if(userMap.get(email).getUserPW().equals(pw)){
				dto.setStatusCode("200");
				dto.setResult("good");
				user = new User("kevin",email, pw);
			}
		}
		return dto;
	  }

	@RequestMapping(value="/api/createmember/{memberName}/{memberEmail}/{memberPassword}",method = RequestMethod.POST,headers="Accept=application/json")
	public WebAPIDTO addMember(@PathVariable String memberName,@PathVariable String memberEmail,@PathVariable String memberPassword) throws ParseException 
	{ 
		System.out.println("reaching addMember API for client :" +memberEmail);
	  	WebAPIDTO dto = new WebAPIDTO();
	  	dto.setMethodName("addMember");
	  	dto.setResult("bad");
	
		if(memberName.isEmpty()|| memberName ==null || memberEmail.isEmpty() ||memberEmail ==null || memberPassword.isEmpty() || memberPassword ==null)			
		{
			dto.setStatusCode("400");
		}
		else{
			if(userMap.containsKey(memberEmail)){
				dto.setStatusCode("400");
				dto.setResult("user exists!");
			}
			else{
				User user = new User(memberName, memberEmail, memberPassword);
				userMap.put(memberEmail, user);
				dto.setStatusCode("200");
				dto.setResult("good");
			}
		}
		return dto;
	  }      
	@RequestMapping(value="/api/getFileListById/{userName}/",method = RequestMethod.POST,headers="Accept=application/json")
	public ArrayList<MyDriveFileInfo> getFileListById(@PathVariable String userName) throws ParseException 
	{ 
		System.out.println("reaching getFileListById API for client :" +userName);
		
		ArrayList<MyDriveFileInfo> list = new ArrayList<MyDriveFileInfo>();;
		if(dbON)
			list = getAllFiles(user.getUserName());
		else if(fileMap.containsKey(userName))
		{
			list = fileMap.get(userName);
		}
		return list;
	}   
	
	public ArrayList<MyDriveFileInfo> getAllFiles(String userName)
	{
		MongoDriver driver = new MongoDriver(userName);
		ArrayList<MyDriveFileInfo> retList = driver.search("fileDtls");
		driver.disConnect();
		return retList;
	}
}




