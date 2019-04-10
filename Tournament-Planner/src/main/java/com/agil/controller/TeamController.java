package com.agil.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/team/search")
	public String getTeamById(@RequestParam(name="id", required=false) Long id, Model model) {
		model.addAttribute("teams", teamService.getAll());
		return "/teams";
	}

	@GetMapping("/teams/search/all")
	public String getTeams(Model model) {
		model.addAttribute("teams", teamService.getAll());
		return "/teams";
	}
	
	
	@GetMapping("/teams/search")
	public String getTeamByName(@RequestParam(name="name", required=false) String name, Model model) {		
		List<Team> team = teamService.findByNameIgnoreCase(name);
		model.addAttribute("teams", team);
		return "/teams";
	}

	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class TeamNotFoundException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	
	@PostMapping("/team")
	public String addTeam(@ModelAttribute("teamForm") Team team, BindingResult bindResult) {
		
		return "/team";
	}
}
