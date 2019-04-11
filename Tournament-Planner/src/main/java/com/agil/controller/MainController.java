package com.agil.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.agil.utility.GameType;

@Controller
public class MainController {

	@GetMapping("/home")
	public String getHome(@RequestParam(name = "type", required=false) String type, Model model) {
		GameType[] types = GameType.values();
		String[] sArray = new String[types.length];
		for(int i = 0; i < types.length; i++) {
			sArray[i] = types[i].name();
		}
		if(type != null) {
			model.addAttribute("type", type.toUpperCase());
		}
		model.addAttribute("gametypes", Arrays.asList(sArray));
		return "home";
	}

	@GetMapping("/**")
	public String getMain(Model model) {
		return "redirect:/home";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Principal user) {

		if (user != null) {
			return "redirect:/home";
		}
		return "redirect:/login";

	}
}
