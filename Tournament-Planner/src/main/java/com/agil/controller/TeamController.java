package com.agil.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.HttpConstraint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.agil.model.Team;
import com.agil.service.TeamService;
import com.agil.service.TeamServiceImpl;

@Controller
public class TeamController {

	private final TeamService teamService;


	@Autowired
	public TeamController(TeamServiceImpl teamServiceImpl) {
		this.teamService = teamServiceImpl;
	}
	
	
	@GetMapping("/team/search/all")
	public String getTeams(Model model) {
		model.addAttribute("teams", teamService.getAll());
		return "/team";
	}
	
	
	@GetMapping("/team/search")
	public String getTeamById(@RequestParam(name="id", required=false) Long id, Model model) {		
		Optional<Team> team = teamService.findOne(id);
		
		if(team.isPresent()) 
			model.addAttribute("teams", Arrays.asList(team.get()));
		else
			model.addAttribute("teams", new ArrayList<>());
		return "/team";
	}

	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class TeamNotFoundException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	
}
