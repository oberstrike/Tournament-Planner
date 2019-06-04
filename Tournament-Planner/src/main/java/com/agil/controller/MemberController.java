package com.agil.controller;

import java.io.File;
import java.security.Principal;
import java.util.Optional;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.agil.model.Member;
import com.agil.model.PasswordChange;
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

	@PostMapping("/registration")
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
		if (!member.isPresent())
			return "redirect:/home";
		model.addAttribute("memberForm", member.get());
		return "member";

	}

	@PostMapping("/member/update")
	public String updateMember(@ModelAttribute PasswordChange passwordChange) throws InvalidActivityException {
		if (!passwordChange.getConfirmPassword().equals(passwordChange.getPassword()))
			throw new InvalidActivityException();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return "redirect:/login";
		String name = auth.getName();

		String oldPassword = (String) passwordChange.getOldPassword();
		Member member = memberService.findByUsername(name);

		if (!memberService.checkIfValidOldPassword(member, oldPassword))
			throw new InvalidActivityException();
		memberService.changeMemberPassword(member, passwordChange.getPassword());

		return "redirect:/home";
	}
	
	@Value("${avatar.upload.path}")
	private String uploadPath;
	
	@PostMapping(value =  "/profile/upload", consumes = {"multipart/form-data"})
	public String imageUpload(@Valid @RequestParam("file") MultipartFile file, Principal principal, Model model) {
		try {
			int length = file.getBytes().length;
			if(length < 80000) {
				Member member = memberService.findByUsername(principal.getName());
				member.setAvatar(true);
				File newFile = new File(uploadPath + String.valueOf( member.getId() ) + ".jpeg");
				newFile.createNewFile();
				file.transferTo(newFile);
				memberService.save(member);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/profile";
	}

	@GetMapping("/profile")
	public String getMember(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return "redirect:/login";
		String name = auth.getName();
		Member member = memberService.findByUsername(name);
		model.addAttribute("memberForm", member);
		model.addAttribute("changePassword", new PasswordChange());
		model.addAttribute("isCreator", true);

		return "member";
	}



}
