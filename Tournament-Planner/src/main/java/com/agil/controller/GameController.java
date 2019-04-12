package com.agil.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.agil.model.Game;
import com.agil.service.GameService;
import com.agil.service.GameServiceImpl;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;
	
	@Autowired
	public GameController(GameServiceImpl GameServiceImpl) {
		this.gameService = GameServiceImpl;
	}

	
	@PostMapping("/game")
	public String addGame(@Valid @ModelAttribute("gameForm") Game gameForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return "/game";
		gameService.save(gameForm);
		
		return "redirect:/games/search?name" + gameForm.getName();
	}

	@GetMapping("/game")
	public String getGameById(@RequestParam(name="id", required=false) Long id, Game game, Model model) {
		if(id != null)
			game = gameService.findOne(id).orElseThrow(GameNotFoundException::new);
		model.addAttribute("gameForm", game);
		return "game";
	}
	
	
	@GetMapping("/games/search")
	public String getGamesByName(@RequestParam(name="name", required=true) String name, Model model) {
		model.addAttribute("games", gameService.findByNameIgnoreCaseContaining(name));
		return "/games";
	}

	@GetMapping("/games/search/all")
	public String getGames(Model model) {
		model.addAttribute("games", gameService.getAll());
		return "/games";
	}
	
	@GetMapping("/games")
	public String teams(Model model) {
		return "/games";
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class GameNotFoundException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	
}
