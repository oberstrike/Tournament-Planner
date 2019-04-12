package com.agil.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agil.utility.GameType;

@RestController
public class GameTypeController {

	/* 
	 * returns ["VOLLEYBALL", "FUSSBALL"...] 
	 */ 
	
	@GetMapping("/gametypes")
	@PreAuthorize("")
	public List<GameType> getTypes(){
		return Arrays.asList(GameType.values());
	}
	
}
