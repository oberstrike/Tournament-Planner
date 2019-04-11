package com.agil.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.agil.model.Game;
import com.agil.service.GameService;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	
	@PostMapping("/game")
	public String addGame(@Valid @ModelAttribute("gameForm") Game game, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "redirect:/home";
		gameService.save(game);
		return "/home";
	}

}
