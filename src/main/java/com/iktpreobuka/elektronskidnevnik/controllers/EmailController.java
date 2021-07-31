package com.iktpreobuka.elektronskidnevnik.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktpreobuka.elektronskidnevnik.models.EmailObject;
import com.iktpreobuka.elektronskidnevnik.services.AdminService;
import com.iktpreobuka.elektronskidnevnik.services.EmailService;
import com.iktpreobuka.elektronskidnevnik.services.FileHandler;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private FileHandler fileHandler;
	
//	@PostMapping(value = "/simpleEmail")
//	public String sendSimpleMessage(@RequestBody EmailObject object) {
//
//		if (object == null || object.getTo() == null || object.getText() == null)
//			return null;
//		emailService.sendSimpleMessage(object);
//		logger.info("A simple email has been sent to : " + object.getTo());
//		return "Your simple email has been sent!";
//	}
//
//	@PostMapping(value = "/templateEmail")
//	public String sendTemplateMessage(@RequestBody EmailObject object) throws Exception {
//
//		if (object == null || object.getTo() == null || object.getText() == null)
//			return null;
//		emailService.sendTemplateMessage(object);
//		logger.info("A template email has been sent to : " + object.getTo());
//		return "Your template email has been sent!";
//	}
//	
//	
//	@PostMapping(value = "/emailWithAttachment")
//	public String sendMessageWithAttachment(@RequestParam String path, @RequestBody EmailObject object) throws Exception {
//		
//		if (object == null || object.getTo() == null || object.getText() == null)
//			return null;
//		emailService.sendMessageWithAttachment(object, path);
//		logger.info("An email with attachment has been sent to : " + object.getTo());
//		return "Your email with attachment has been sent!";
//	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/user_details")
	public ResponseEntity<List<String>> getAllUsernames() {
		return new ResponseEntity<List<String>>(this.adminService.getAllUsernames(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}
	@RequestMapping(path = "/uploadStatus" ,method = RequestMethod.GET)
	public String uploadStatus() {
		logger.debug("This is error message.");
		logger.info("This is info message.");
		logger.warn("This is a warn message");
		logger.error("This is an error message");
		return "uploadStatus";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping (method = RequestMethod.POST, path = "/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		return fileHandler.singleFileUpload(file, redirectAttributes);
	}

}
