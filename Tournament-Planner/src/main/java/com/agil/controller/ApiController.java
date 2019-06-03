package com.agil.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agil.dto.GameDTO;
import com.agil.dto.TeamDTO;
import com.agil.model.Game;
import com.agil.model.Team;
import com.agil.service.GameService;
import com.agil.service.TeamService;
import com.agil.utility.GameType;

@RestController
public class ApiController {

	@Autowired
	private GameService gameService;

	@Autowired
	private TeamService teamService;

	@GetMapping("/api/gametypes")
	public List<GameType> getTypes() {
		return Arrays.asList(GameType.values());
	}

	@GetMapping("/api/teams")
	public List<TeamDTO> getTeams(@RequestParam(required = false) Long id) {
		if (id != null) {
			Team team = teamService.findOne(id).orElse(new Team());
			return new ArrayList<>(Arrays.asList(new TeamDTO(team)));
		}
		return teamService.getAll().stream().map(TeamDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/api/games")
	public List<GameDTO> getGames(@RequestParam(required = false) Long id, Principal principal) {
		if (id != null) {
			Game game = gameService.findOne(id).orElse(new Game());
			return new ArrayList<>(Arrays.asList(new GameDTO(game)));
		}else if(id == null && principal != null) {
			List<Game> gameA = gameService.findByTeamA_Players_Name(principal.getName());
			List<Game> gameB = gameService.findByTeamB_Players_Name(principal.getName());
			gameA.addAll(gameB);
			return gameA.stream().sorted().map(GameDTO::new).collect(Collectors.toList());
		
		}
		
		
		return gameService.getAll().stream().map(GameDTO::new).collect(Collectors.toList());
	}	
	
	@GetMapping("/api/uhrzeit")
	public long getDate() {
		return System.currentTimeMillis();
	}

}
