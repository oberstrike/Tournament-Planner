package com.agil.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

	@Value(value ="${avatar.upload.path}")
	private String path;
	
	@GetMapping("{filename:.*}")
	public byte[] getAvatar(@PathVariable("filename") String filename) throws FileNotFoundException {
		
		File file = new File(path + filename);
		byte[] bytes;
		try {
			 bytes = Files.readAllBytes( Paths.get(file.getPath()));
			 
		}catch (Exception e) {
			e.printStackTrace();
			
			throw new FileNotFoundException();
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.parseMediaType("application/jpeg"));
		
		headers.setContentDispositionFormData("profile.jpeg", filename);
		
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		
		ResponseEntity<byte[]> response = new ResponseEntity<>(bytes ,headers, HttpStatus.OK);
		
		return response.getBody();
		
		
	
	}
	
	
	@ExceptionHandler
	public ResponseEntity<String> handleException(FileNotFoundException exception){
		return new ResponseEntity<String>("File not Found", HttpStatus.NOT_FOUND);
		
	}
	
}
