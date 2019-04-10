package com.agil.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.agil.model.Member;
import com.agil.service.MemberService;
import com.agil.service.SecurityService;
import com.agil.utility.MemberValidator;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private MemberValidator memberValidator;

	@GetMapping("/registration")
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String registration(Model model) {
		model.addAttribute("userForm", new Member());
		return "registration";
	}

	@PostMapping
	public String registration(@ModelAttribute("memberForm") Member memberForm, BindingResult bindingResult) {
		memberValidator.validate(memberForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		memberService.save(memberForm);
		securityService.autoLogin(memberForm.getUsername(), memberForm.getPassword());
		return "redirect:/home";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username or password is invalid");
		if (logout != null)
			model.addAttribute("message", "you have been logged out successfull");
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

}
