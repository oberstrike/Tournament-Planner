package com.agil.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.service.MemberService;
import com.agil.service.MemberServiceImpl;
import com.agil.service.PlayerService;
import com.agil.service.SecurityService;
import com.agil.service.SecurityServiceImpl;
import com.agil.utility.MemberValidator;

@Controller
public class MemberController {


	@Autowired
	private final MemberService memberService;
	
	@Autowired
	private final PlayerService playerService;

	@Autowired
	private final SecurityService securityService;

	@Autowired
	private final MemberValidator memberValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	
	public MemberController(MemberServiceImpl memberService, SecurityServiceImpl securityService,
			MemberValidator memberValidator, PlayerService playerService) {
		super();
		this.memberService = memberService;
		this.securityService = securityService;
		this.memberValidator = memberValidator;
		this.playerService = playerService;
	}

	

	@GetMapping("/registration")
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String registration(Member member, Model model) {
		model.addAttribute("memberForm", member);
		return "registration";
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String registration(@Valid @ModelAttribute("memberForm") Member memberForm, BindingResult bindingResult) {
		memberValidator.validate(memberForm, bindingResult);
		String password = memberForm.getPassword();
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		Player player = new Player(memberForm.getUsername());
		memberForm.setPlayer(player);
		memberService.save(memberForm);
		playerService.save(player);
		securityService.autoLogin(memberForm.getUsername(), password);

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
	
	@GetMapping("/member")
	public String getMemberById(@RequestParam long id, Model model) {
		Optional<Member> member = memberService.findById(id);
		if(!member.isPresent())
			return "redirect:/home";
		model.addAttribute("memberForm", member.get());
		return "member";
		
	}
	
	@GetMapping("/profile")
	public String getMember(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null)
			return "redirect:/login";
		String name = auth.getName();
		Member member = memberService.findByUsername(name);
		model.addAttribute("memberForm", member);
		model.addAttribute("isCreator", true);

		return "member";
	}
	
	
	

}
