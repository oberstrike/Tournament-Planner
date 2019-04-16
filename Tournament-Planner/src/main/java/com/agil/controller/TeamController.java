package com.agil.controller;

import javax.validation.Valid;

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
import com.agil.utility.TeamValidator;

@Controller
public class TeamController {

	@Autowired
	private final TeamService teamService;
	
	@Autowired
	private final TeamValidator teamValidator;

	@Autowired
	public TeamController(TeamServiceImpl teamServiceImpl, TeamValidator teamValidator) {
		super();
		this.teamService = teamServiceImpl;
		this.teamValidator = teamValidator;
	}

	@PostMapping("/team")
	public String addTeam(@Valid @ModelAttribute("teamForm") Team teamForm, BindingResult bindingResult) {
		teamValidator.validate(teamForm, bindingResult);
		System.out.println(bindingResult);
		if(bindingResult.hasErrors())
			return "/team";
		teamService.save(teamForm);
		
		return "redirect:/team/search?name" + teamForm.getName();
	}
	
	@GetMapping("/team")
	public String getTeamById(@RequestParam(name="id", required=false) Long id, Team team, Model model) {
		if(id != null)
			team = teamService.findOne(id).orElseThrow(TeamNotFoundException::new);
		System.out.println(team.getPlayers());
		model.addAttribute("teamForm", team);
		return "team";
	}
	
	
	@GetMapping("/teams/search")
	public String getTeamsByName(@RequestParam(name="name", required=true) String name, Model model) {
		model.addAttribute("teams", teamService.findByNameIgnoreCaseContaining(name));
		return "/teams";
	}

	@GetMapping("/teams/search/all")
	public String getTeams(Model model) {
		model.addAttribute("teams", teamService.getAll());
		return "/teams";
	}
	
	@GetMapping("/teams")
	public String teams(Model model) {
		return "/teams";
	}
	

	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class TeamNotFoundException extends RuntimeException{
		private static final long serialVersionUID = 1L;		
	}
	

}
