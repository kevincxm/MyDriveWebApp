package com.websystique.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.websystique.springmvc.model.FileBucket;
import com.websystique.springmvc.model.MultiFileBucket;
import com.websystique.springmvc.model.WebAPIDTO;
import com.websystique.springmvc.util.FileValidator;
import com.websystique.springmvc.util.MultiFileValidator;

@Controller
public class FileUploadController {

	private static String UPLOAD_LOCATION="/Users/kevinchen/Desktop/";
	
	/****************************************************************************
	 * Project routing section
	 ****************************************************************************/
	
	@Autowired
	FileValidator fileValidator;

	@Autowired
	MultiFileValidator multiFileValidator;

	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@InitBinder("multiFileBucket")
	protected void initBinderMultiFileBucket(WebDataBinder binder) {
		binder.setValidator(multiFileValidator);
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String getHomePage(ModelMap model) {
		return "welcome";
	}
	
	@RequestMapping(value =  "/login", method = RequestMethod.GET)
	public String getLogin(ModelMap model) {
		return "login";
	}
	@RequestMapping(value =  "/upload", method = RequestMethod.GET)
	public String getUpload(ModelMap model) {
		return "upload";
	}
	@RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return "singleFileUploader";
	}
	@RequestMapping(value = "/multiUpload", method = RequestMethod.GET)
	public String getMultiUploadPage(ModelMap model) {
		MultiFileBucket filesModel = new MultiFileBucket();
		model.addAttribute("multiFileBucket", filesModel);
		return "multiFileUploader";
	}

	@RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
	public String multiFileUpload(@Valid MultiFileBucket multiFileBucket,
			BindingResult result, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			System.out.println("validation errors in multi upload");
			return "multiFileUploader";
		} else {
			System.out.println("Fetching files");
			List<String> fileNames = new ArrayList<String>();
			// Now do something with file...
			for (FileBucket bucket : multiFileBucket.getFiles()) {
				FileCopyUtils.copy(bucket.getFile().getBytes(), new File(UPLOAD_LOCATION + bucket.getFile().getOriginalFilename()));
				fileNames.add(bucket.getFile().getOriginalFilename());
			}

			model.addAttribute("fileNames", fileNames);
			return "multiSuccess";
		}
	}

	@RequestMapping(value = "/singleUpload", method = RequestMethod.POST)
	public String singleFileUpload(@Valid FileBucket fileBucket,
			BindingResult result, ModelMap model) throws IOException {
		System.out.println("reach the singleUpload api");
		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "singleFileUploader";
		} else {
			System.out.println("Fetching file");
			MultipartFile multipartFile = fileBucket.getFile();
			// Now do something with file...
			File file = new File("/Users/kevinchen/Desktop/testdownload/"+fileBucket.getFile().getOriginalFilename());
			multipartFile.transferTo(file);
			
			//FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File( UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename()));
			System.out.println("the path: "+UPLOAD_LOCATION + fileBucket.getFile().getOriginalFilename());
			System.out.println("the size: "+multipartFile.getSize());
			String fileName = multipartFile.getOriginalFilename();
			model.addAttribute("fileName", fileName);
			return "success";
		}
	}

}
