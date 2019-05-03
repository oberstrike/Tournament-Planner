package com.agil.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.service.PlayerService;
import com.agil.service.PlayerServiceImpl;
import com.agil.service.SecurityServiceImpl;
import com.agil.service.TeamService;
import com.agil.utility.PlayerValidator;

@Controller
public class PlayerController {


	@Autowired
	private final PlayerService playerService;

	@Autowired
	private final TeamService teamService;
	@Autowired
	private final PlayerValidator playerValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	
	public PlayerController(PlayerServiceImpl playerService,
			PlayerValidator playerValidator, TeamService teamService) {
		super();
		this.playerService = playerService;
		this.playerValidator = playerValidator;
		this.teamService = teamService;
	}
	
	@PostMapping("/player")
	public String addPlayer(@Valid @ModelAttribute("playerForm") Player playerForm, BindingResult bindingResult, @RequestParam(name = "tid") String teamId) {
		playerValidator.validate(playerForm, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "redirect:/team?id=" + teamId;		
		}

		if(!playerService.findByName(playerForm.getName()).isPresent())
			playerService.save(playerForm);
		else
			playerForm = playerService.findByName(playerForm.getName()).get();
		
		Optional<Team> oTeam = teamService.findOne(Long.valueOf(teamId));
		if(oTeam.isPresent()) {
			Team team = oTeam.get();
			team.addPlayer(playerForm);
			playerForm.addTeam(team);
			teamService.save(team);
		}
		return  "redirect:/team?id=" + teamId;
		
	}
	
	
	
	

}
