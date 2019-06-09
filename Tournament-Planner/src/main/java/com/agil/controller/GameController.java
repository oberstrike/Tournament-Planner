package com.agil.controller;

import java.security.Principal;
import java.util.stream.Collectors;

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
import com.agil.model.game.LeagueOfLegends;
import com.agil.model.game.Volleyball;
import com.agil.service.GameService;
import com.agil.service.GameServiceImpl;
import com.agil.service.MemberService;
import com.agil.service.TeamService;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;
import com.agil.utility.GameValidator;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private GameValidator gameValidator;

	@Autowired
	private final MemberService memberService;

	@Autowired
	private final TeamService teamService;

	@Autowired
	public GameController(GameServiceImpl GameServiceImpl, MemberService memberService, TeamService teamService) {
		this.gameService = GameServiceImpl;
		this.memberService = memberService;
		this.teamService = teamService;
	}

	@PostMapping("/game")
	public String addGame(@Valid @ModelAttribute("gameForm") Game gameForm, BindingResult bindingResult,
			Principal principal) {
		gameValidator.validate(gameForm, bindingResult);
		if (bindingResult.hasErrors())
			return "/game";
		String username = principal.getName();
		Member creator = memberService.findByUsername(username);
		gameForm.setCreator(creator);
		gameService.save(gameForm);

		return "redirect:/games/search?name=" + gameForm.getName();
	}
	
	@PostMapping("/game/leagueoflegends")
	public String addLeagueOfLegendsGame(@RequestParam(name = "teamAName", required = true) String teamAName,
			@RequestParam(name = "teamBName", required = true) String teamBName,
			@Valid @ModelAttribute("volleyballForm") LeagueOfLegends leagueForm, BindingResult bindingResult,
			Principal principal) {
		if (bindingResult.hasErrors())
			return "/home";
		String username = principal.getName();
		Member creator = memberService.findByUsername(username);
		leagueForm.setTeamA(teamService.findByName(teamAName).get());
		leagueForm.setTeamB(teamService.findByName(teamBName).get());
		leagueForm.setCreator(creator);
		leagueForm.setStatus(GameStatus.PENDING);
		leagueForm.setType(GameType.LEAGUEOFLEGENDS);
		gameService.save(leagueForm);

		return "redirect:/game?id=" + leagueForm.getId();
	}
	
	

	@PostMapping("/game/Volleyball")
	public String addVolleyballGame(@RequestParam(name = "teamAName", required = true) String teamAName,
			@RequestParam(name = "teamBName", required = true) String teamBName,
			@Valid @ModelAttribute("volleyballForm") Volleyball volleyballForm, BindingResult bindingResult,
			Principal principal) {
		if (bindingResult.hasErrors())
			return "/home";
		String username = principal.getName();
		Member creator = memberService.findByUsername(username);
		volleyballForm.setTeamA(teamService.findByName(teamAName).get());
		volleyballForm.setTeamB(teamService.findByName(teamBName).get());
		volleyballForm.setCreator(creator);
		volleyballForm.setStatus(GameStatus.PENDING);
		volleyballForm.setType(GameType.VOLLEYBALL);
		volleyballForm.initVolleyballGame();
		gameService.save(volleyballForm);

		return "redirect:/game?id=" + volleyballForm.getId();
	}

	@PostMapping("change/Leagueoflegends")
	public String changeLeagueOfLegends(@RequestParam(name = "id", required = true) long id, @RequestParam(name = "teamA", required = false) long teamA, @RequestParam(name = "teamB", required = false) long teamB) {
		LeagueOfLegends leagueOfLegends = (LeagueOfLegends) gameService.findOne(id).orElseThrow(GameNotFoundException::new);
		leagueOfLegends.setKillsTeamA(teamA);
		leagueOfLegends.setKillsTeamB(teamB);
		gameService.save(leagueOfLegends);
		
		return "redirect:/game?id=" + id;
	}
	
	@PostMapping("/change/Volleyball")
	public String changeVolleyballGame(@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "optionID", required = true) int optionID) {
		Volleyball volleyball = (Volleyball) gameService.findOne(Long.parseLong(id))
				.orElseThrow(GameNotFoundException::new);
		if (volleyball.getType() != GameType.VOLLEYBALL) {
			System.out.println("GameType incorrect");
			return "/home";
		}
		switch (optionID) {
		case 1:
			// add A
			System.out.println("Add A");
			volleyball.addA();
			System.out.println(volleyball.toString());
			break;
		case 2:
			System.out.println("Minus B");
			volleyball.minusA();
			System.out.println(volleyball.toString());
			// substract A
			break;
		case 3:
			System.out.println("Add B");
			volleyball.addB();
			System.out.println(volleyball.toString());
			// add B
			break;
		case 4:
			System.out.println("Minus B");
			volleyball.minusB();
			System.out.println(volleyball.toString());
			// subtract B
			break;
		default:
			break;
		}
		gameService.save(volleyball);

		return "redirect:/game?id=" + volleyball.getId();
	}

	@GetMapping("/game")
	public String getGameById(@RequestParam(name = "id", required = false) Long id, Game game, Principal principal,
			Model model) {
		if (id != null)
			game = gameService.findOne(id).orElseThrow(GameNotFoundException::new);
		model.addAttribute("gameForm", game);
		if (principal != null)
			model.addAttribute("isCreator", principal.getName().equals(game.getCreator().getUsername()));
		return "games";
	}

	@GetMapping("/games/search")
	public String getGamesByName(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "type", required = false) String type, Model model) {
		if (name != null)
			model.addAttribute("games",
					gameService.findByNameIgnoreCaseContaining(name).stream().limit(10).collect(Collectors.toList()));
		if (type != null)
			model.addAttribute("games", gameService.findByType(type).stream().limit(10).collect(Collectors.toList()));

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
	public String getVolleyballGames() {
		return "redirect:/games/search?type=volleyball";
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class GameNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

}
