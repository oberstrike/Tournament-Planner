package com.agil.controller;

import java.security.Principal;

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

import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.service.MemberService;
import com.agil.service.TeamService;
import com.agil.service.TeamServiceImpl;
import com.agil.utility.MemberValidator;
import com.agil.utility.TeamValidator;

@Controller
public class TeamController {

	@Autowired
	private final TeamService teamService;
	
	@Autowired
	private final TeamValidator teamValidator;
	
	@Autowired
	private final MemberService memberService;

	@Autowired
	public TeamController(TeamServiceImpl teamServiceImpl, TeamValidator teamValidator, MemberService memberService) {
		super();
		this.teamService = teamServiceImpl;
		this.teamValidator = teamValidator;
		this.memberService = memberService;
	}

	@PostMapping("/team")
	public String addTeam(@Valid @ModelAttribute("teamForm") Team teamForm, BindingResult bindingResult, Principal principal) {
		teamValidator.validate(teamForm, bindingResult);
		if(bindingResult.hasErrors())
			return "/team";
		String username = principal.getName();
		Member creator = memberService.findByUsername(username);
		teamForm.setTeamcolor('#' + teamForm.getTeamcolor());
		teamForm.setCreator(creator);
		teamService.save(teamForm);
		
		return "redirect:/team?id=" + teamForm.getId();

	}
	
	@GetMapping("/team")
	public String getTeamById(@RequestParam(name="id", required=false) Long id, Team team, Player player, Principal principal, Model model) {
		if(id != null)
			team = teamService.findOne(id).orElseThrow(TeamNotFoundException::new);
		model.addAttribute("teamForm", team);
		model.addAttribute("playerForm", player);
		System.out.println("isCreator " + principal.getName().equals(team.getCreator().getUsername()));
		model.addAttribute("isCreator", principal.getName().equals(team.getCreator().getUsername()));
		return "teams";
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
