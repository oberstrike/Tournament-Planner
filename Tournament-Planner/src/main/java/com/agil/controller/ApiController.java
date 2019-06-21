package com.agil.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agil.dto.GameDTO;
import com.agil.dto.PlayerDTO;
import com.agil.dto.TeamDTO;
import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.model.game.Volleyball;
import com.agil.service.GameService;
import com.agil.service.MemberService;
import com.agil.service.PlayerService;
import com.agil.service.TeamService;
import com.agil.utility.GameType;


//öffentliche Rest-API

@RestController
public class ApiController {

	@Autowired
	private GameService gameService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PlayerService playerService;

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

	@GetMapping("/api/players")
	public List<PlayerDTO> getPlayers(@RequestParam(required = false) Long id) {
		if(id != null) {
			Player player = playerService.findById(id).orElse(new Player());
			return new ArrayList<>(Arrays.asList(new PlayerDTO(player)));
		}	
		return this.playerService.findAll().stream().map(PlayerDTO::new).collect(Collectors.toList());
	}

	@GetMapping("/api/games")
	public List<GameDTO> getGames(@RequestParam(required = false) Long id, Principal principal) {
		if (id != null) {
			Game game = gameService.findOne(id).orElse(new Game());
			return new ArrayList<>(Arrays.asList(new GameDTO(game)));
		}
		return gameService.getAll().stream().map(GameDTO::new).collect(Collectors.toList());
	}
	
	//Wichtig für die Anwendung
	@GetMapping("/api/id")
	public long getMyId(Principal principal) {
		if (principal == null)
			return 0;
		Member member = memberService.findByUsername(principal.getName());
		if (member.isAvatar())
			return member.getId();
		return 0;
	}

	@GetMapping("/api/uhrzeit")
	public long getDate() {
		return System.currentTimeMillis();
	}
	
	@GetMapping("/api/nextgame")
	public String getNextGame(Principal principal) {
		if (principal == null)
			return "";
		List<Game> gameA = gameService.findByTeamA_Players_Name(principal.getName());
		List<Game> gameB = gameService.findByTeamB_Players_Name(principal.getName());
		gameA.addAll(gameB);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return gameA.stream().sorted().map(GameDTO::new).map(GameDTO::getStartDate)
				.filter(each -> each.after(new Date(System.currentTimeMillis()))).map(sdf::format).findFirst()
				.orElse("");
	}

}
