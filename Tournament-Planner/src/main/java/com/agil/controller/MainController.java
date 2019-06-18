package com.agil.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.game.LeagueOfLegends;
import com.agil.model.game.Volleyball;
import com.agil.service.GameService;
import com.agil.service.MemberService;
import com.agil.service.TeamService;
import com.agil.service.TeamServiceImpl;
import com.agil.utility.GameType;
import com.agil.utility.MemberRole;

@Controller
public class MainController {

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private GameService gameService;
	
	@GetMapping("/home")
	public String getHome(@RequestParam(name = "type", required = false) String type, Model model, Principal principal, HttpServletRequest request) {
		if (type != null) {
			model.addAttribute("type", type.toUpperCase());
			model.addAttribute("gameForm", new Game());
			model.addAttribute("volleyballForm", new Volleyball());
			model.addAttribute("leagueForm", new LeagueOfLegends());
		}
		model.addAttribute("gametypes", GameType.values());
		model.addAttribute("teams", teamService.getAll());
		
		List<Game> allGames = gameService.getAll();
		List<Game> myGames = new ArrayList<>();
		
		if (principal != null) {
			Member member = memberService.findByUsername(principal.getName());
			for(Game game : allGames) {
				if(game.getCreator().getUsername().equals(member.getUsername())) {
					myGames.add(game);
				}
			}
			if(myGames != null) {
				model.addAttribute("myGames", myGames);
			}
		}
		return "home";
	}

	
	@GetMapping("/")
	public String getStandard() {
		return "redirect:home";
	}
	
	@GetMapping("/dataprotection")
	public String getDataprotection() {
		return "dataprotection";
	}
	
	@GetMapping("/impressum")
	public String getImpressum() {
		return "impressum";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Principal user) {
		if (user != null) {
			return "redirect:/home";
		}
		return "redirect:/login";

	}
}
