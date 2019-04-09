package com.agil.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/home")
	public String getHome(Model model) {
		return "home";
	}
	
	@GetMapping("/**")
	public String getMain(Model model) {
		return getHome(model);
	}
}
