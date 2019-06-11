package com.agil.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.game.LeagueOfLegends;
import com.agil.model.game.Volleyball;
import com.agil.service.GameService;
import com.agil.service.MemberService;
import com.agil.service.TeamService;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;
import com.agil.utility.GameValidator;
import com.agil.utility.MapBuilder;
import com.agil.utility.MemberRole;

@Controller
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private GameValidator gameValidator;

	@Autowired
	private MemberService memberService;

	@Autowired
	private TeamService teamService;

	public static List<Game> getFirst10(List<Game> games) {
		return games.stream().limit(10).collect(Collectors.toList());
	}
	
	@PostMapping("/game/leagueoflegends")
	public String addLeagueOfLegendsGame(@RequestParam(name = "teamAName", required = true) String teamAName,
			@RequestParam(name = "teamBName", required = true) String teamBName,
			@Valid @ModelAttribute("volleyballForm") LeagueOfLegends leagueForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Principal principal) {
		gameValidator.validate(leagueForm, bindingResult);
		if (bindingResult.hasErrors() | teamAName.equals(teamBName)) {
			redirectAttributes.addFlashAttribute("message", new MapBuilder<>().addPair("error", "Bitte überprüfe deine Eingaben").build());
			return "redirect:/home?type=Leagueoflegends";
		}
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
			RedirectAttributes redirectAttributes, Principal principal) {
		gameValidator.validate(volleyballForm, bindingResult);
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "Das Spiel konnte nicht erstellt werden");
			return "redirect:/home?type=Volleyball";
		}
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

	@PostMapping("/change/Leagueoflegends")
	public String changeLeagueOfLegends(@RequestParam(name = "id", required = true) long id,
			@RequestParam(name = "teamA", required = false) long teamA,
			@RequestParam(name = "teamB", required = false) long teamB,
			@RequestParam(name = "status", required = false) String status) {
		LeagueOfLegends leagueOfLegends = (LeagueOfLegends) gameService.findOne(id)
				.orElseThrow(GameNotFoundException::new);
		leagueOfLegends.setKillsTeamA(teamA);
		leagueOfLegends.setKillsTeamB(teamB);
		leagueOfLegends.setStatus(GameStatus.valueOf(status.toUpperCase()));
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

		if (principal != null) {
			Member member = memberService.findByUsername(principal.getName());
			boolean isCreator = member.getUsername().equals(game.getCreator().getUsername());
			boolean isAdmin = member.getRoles().contains(MemberRole.ROLE_ADMIN);
			model.addAttribute("isCreator", isCreator ? true : isAdmin);
		}

		return "games";
	}

	@GetMapping("/games/search")
	public String getGamesByName(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "type", required = false) String type, Model model) {
		if (name != null)
			model.addAttribute("games", getFirst10(gameService.findByNameIgnoreCaseContaining(name)));
		if (type != null)
			model.addAttribute("games", getFirst10(gameService.findByType(type)));
		return "/games";
	}

	@GetMapping("/games/search/all")
	public String getGames(Model model) {
		model.addAttribute("games", gameService.getAll());
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

	@PostMapping("/game/delete/{gameId}")
	public String removeGame(@RequestParam("gameId") Long gameId, Principal principal, Model model) {
		if(gameId == null)
			return "redirect:/home";
		if(principal == null)
			return "redirect:/home";
		
		Game game = gameService.findOne(gameId).orElseThrow(GameNotFoundException::new);
		Member member = memberService.findByUsername(principal.getName());
		
		if(!member.equals(game.getCreator()))
			return "redirect:/home";
		gameService.delete(game);		
		model.addAttribute("message", new MapBuilder<>().addPair("success", "Das Spiel wurde erfolgreich gelöscht"));
		return "/home";
	}
	
}
