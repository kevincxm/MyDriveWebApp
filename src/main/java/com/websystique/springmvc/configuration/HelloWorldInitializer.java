package com.websystique.springmvc.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class HelloWorldInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { HelloWorldConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(getMultipartConfigElement());
	}

	private MultipartConfigElement getMultipartConfigElement() {
		loadSystemProperties();
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(	LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
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
					LOCATION = val;
//				else if(key.equals("db"))
//				{
//					if(val.equalsIgnoreCase("ON"))
//						dbON = true;
//				}
			}
			if(LOCATION==null || LOCATION.equals(""))
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
	private static String LOCATION = ""; // Temporary location where files will be stored

	private static final long MAX_FILE_SIZE = 555242880; // 5MB : Max file size.
														// Beyond that size spring will throw exception.
	private static final long MAX_REQUEST_SIZE = 555242880; // 20MB : Total request size containing Multi part.
	
	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
}