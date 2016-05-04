package com.websystique.springmvc.controller;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.model.WebAPIDTO;

@RestController
public class WebServerController {

	private HashMap<String, String> userMap = new HashMap<String, String>();	
	public WebServerController(){
		userMap.put("kevin@gmail.com", "123");
		userMap.put("piyush@gmail.com", "123");
	}
	
	@RequestMapping(value="/api/login/{email}/{pw}",method = RequestMethod.GET,headers="Accept=application/json")
	public WebAPIDTO login(@PathVariable String email ,@PathVariable String pw) throws ParseException 
	{ 
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("login");
		dto.setResult("bad");	
		dto.setStatusCode("404");
		for(String s: userMap.keySet()){
			if(s.equals(email) && userMap.get(email).equals(pw)){
				dto.setResult("good");
				dto.setStatusCode("200");
			}
		}		
		return dto;
	  }
	
	@RequestMapping(value="/api/test",method = RequestMethod.GET,headers="Accept=application/json")
	public String test() throws ParseException 
	{ 
		System.out.println("Here is the testing api");
		return"Hi there!!!";
	}
	
	@RequestMapping(value="/api/test1",method = RequestMethod.GET,headers="Accept=application/json")
	public WebAPIDTO test1() throws ParseException 
	{ 
		WebAPIDTO dto = new WebAPIDTO();
		dto.setMethodName("test 1 ");
		dto.setResult("good");
		dto.setStatusCode("200");
		return dto;
	}
}
