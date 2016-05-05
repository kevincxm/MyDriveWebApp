package com.websystique.springmvc.controller;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.WebAPIDTO;

@RestController
public class WebServerController {

	private HashMap<String, User> userMap = new HashMap<String, User>();	
	// TODO: should remove when db is ready
	public WebServerController(){
		User kevin = new User("Kevin", "kevin@gmail.com","123");
		User piyush = new User("Piyush", "piyush@gmail.com","123");
		
		userMap.put(kevin.getUserEmail(),kevin);
		userMap.put(piyush.getUserEmail(), piyush);
		
	}
	
	@RequestMapping(value="/api/login/{email}/{pw}",method = RequestMethod.GET,headers="Accept=application/json")
	public WebAPIDTO login(@PathVariable String email ,@PathVariable String pw) throws ParseException 
	{ 
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("login");
		dto.setResult("bad");	
		dto.setStatusCode("404");
		if(userMap.containsKey(email)){
			if(userMap.get(email).getUserPW().equals(pw)){
				dto.setStatusCode("200");
				dto.setResult("good");
			}
		}
		return dto;
	  }
	/****************************************
	 * Method that allows user to register 
	 * @param memberName
	 * @param memberEmail
	 * @param memberPassword
	 * @return WebAPIDTO
	 * @throws ParseException
	 ****************************************/
	@RequestMapping(value="/api/createmember/{memberName}/{memberEmail}/{memberPassword}",method = RequestMethod.POST,headers="Accept=application/json")
	public WebAPIDTO addMember(@PathVariable String memberName,@PathVariable String memberEmail,@PathVariable String memberPassword) throws ParseException 
	{ 

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
}
