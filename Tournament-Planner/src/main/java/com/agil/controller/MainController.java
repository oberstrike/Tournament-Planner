package com.agil.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@GetMapping("/home")
	public String getHome(Model model) {
		return "home";
	}
	
	@GetMapping("/**")
	public String getMain(Model model) {
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Principal user) {
		if(user != null)
		{
			return "redirect:/home";
		}
		return "redirect:/login";
		
	}
}
