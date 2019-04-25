package com.agil.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agil.utility.GameType;

@RestController
public class ApiController {

	/* 
	 * returns ["VOLLEYBALL", "FUSSBALL"...] 
	 */ 
	
	@GetMapping("/api/gametypes")
	public List<GameType> getTypes(){
		return Arrays.asList(GameType.values());
	}

	
}
