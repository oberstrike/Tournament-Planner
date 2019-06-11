package com.agil.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.agil.model.Member;
import com.agil.service.MemberService;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

	@Value(value ="${avatar.upload.path}")
	private String path;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("{id}")
	public byte[] getAvatarById(@PathVariable("id") Long id) throws IOException {
		if(id == null)
			return null;
		Member member = memberService.findById(id).orElseThrow(FileNotFoundException::new);
		
		if(!member.isAvatar())
			return null;
		byte[] data = Files.readAllBytes(new File(path + id + ".jpeg").toPath());
	
		HttpHeaders headers = new HttpHeaders();	
		headers.setContentType(MediaType.parseMediaType("application/jpeg"));
		headers.setContentDispositionFormData("profile.jpeg", "profile.jpeg");		
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(data ,headers, HttpStatus.OK).getBody();
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleException(FileNotFoundException exception){
		return new ResponseEntity<String>("File not Found", HttpStatus.NOT_FOUND);
		
	}
	
}
