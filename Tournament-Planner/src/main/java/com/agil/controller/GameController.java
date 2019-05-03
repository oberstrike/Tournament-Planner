package com.agil.controller;

import java.security.Principal;

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
import com.agil.model.Member;
import com.agil.service.GameService;
import com.agil.service.GameServiceImpl;
import com.agil.service.MemberService;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private final MemberService memberService;

	@Autowired
	public GameController(GameServiceImpl GameServiceImpl, MemberService memberService) {
		this.gameService = GameServiceImpl;
		this.memberService = memberService;
	}

	@PostMapping("/game")
	public String addGame(@Valid @ModelAttribute("gameForm") Game gameForm, BindingResult bindingResult,
			Principal principal) {
		System.out.println(bindingResult);
		if (bindingResult.hasErrors())
			return "/game";
		String username = principal.getName();
		Member creator = memberService.findByUsername(username);
		gameForm.setCreator(creator);
		gameService.save(gameForm);

		return "redirect:/games/search?name=" + gameForm.getName();
	}

	@GetMapping("/game")
	public String getGameById(@RequestParam(name = "id", required = false) Long id, Game game, Principal principal,
			Model model) {
		if (id != null)
			game = gameService.findOne(id).orElseThrow(GameNotFoundException::new);
		model.addAttribute("gameForm", game);
		model.addAttribute("isCreator", principal.getName().equals(game.getCreator().getUsername()));
		return "games";
	}

	@GetMapping("/games/search")
	public String getGamesByName(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "type", required = false) String type, Model model) {
		if(name != null)
			model.addAttribute("games", gameService.findByNameIgnoreCaseContaining(name));
		if(type != null) 
			model.addAttribute("games", gameService.findByType(type));
		
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

	@GetMapping("/game/Volleyball")
	public String volleybal() {
		return "/volleyball";
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class GameNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
