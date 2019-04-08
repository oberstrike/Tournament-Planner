package com.agil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.agil.service.TeamService;
import com.agil.service.TeamServiceImpl;

@Controller
public class TeamController {

	private final TeamService teamService;

	@Autowired
	public TeamController(TeamServiceImpl teamServiceImpl) {
		this.teamService = teamServiceImpl;
	}
	
	
	@GetMapping("/teams")
	public String getTeams(Model model) {
		model.addAttribute("teams", teamService.getAll());
		return "/Teams";
	}
}
