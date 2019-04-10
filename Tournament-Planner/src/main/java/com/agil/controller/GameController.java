package com.agil.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.agil.utility.GameType;

@Controller
public class GameController {
	
	@GetMapping("/game/gametypes")
	public List<GameType> getGameTypes(){
		return Arrays.asList(GameType.values());
	}
}
