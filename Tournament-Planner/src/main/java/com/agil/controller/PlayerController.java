package com.agil.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.service.MemberService;
import com.agil.service.MemberServiceImpl;
import com.agil.service.PlayerService;
import com.agil.service.PlayerServiceImpl;
import com.agil.service.SecurityService;
import com.agil.service.SecurityServiceImpl;
import com.agil.service.TeamService;
import com.agil.utility.MemberValidator;
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
		
		if(bindingResult.hasErrors())
			return "redirect:/team?id=" + teamId;
		
		//TODO Hier ist ein Fehler da der vorhandene Wert überschrieben wird.
		playerForm = new Player(playerForm.getName());
		playerService.save(playerForm);
		
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
