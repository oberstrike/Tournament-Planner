package com.agil.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Principal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agil.model.Member;
import com.agil.model.PasswordChange;
import com.agil.model.Player;
import com.agil.service.MemberService;
import com.agil.service.PlayerService;
import com.agil.service.SecurityService;
import com.agil.utility.MapBuilder;
import com.agil.utility.MemberValidator;
import com.agil.utility.PasswordChangeValidator;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private MemberValidator memberValidator;

	@Autowired
	private PasswordChangeValidator passwordChangeValidator;

	private final String ISPNG = "([^\\s]+(\\.(?i)(png))$)";

	@Value("${avatar.upload.path}")
	private String uploadPath;

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

	@ModelAttribute("passwordChange")
	public PasswordChange newPasswordChange() {
		return new PasswordChange();
	}

	@PostMapping("/member/update")
	public String updateMember(@ModelAttribute("passwordChange") final PasswordChange passwordChange,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		passwordChangeValidator.validate(passwordChange, bindingResult);
		String route = "redirect:/profile";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("message",
					new MapBuilder<>().addPair("error", "Überprüfe deine Eingaben.").build());
			return route;

		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			return route;
		String name = auth.getName();

		String oldPassword = (String) passwordChange.getOldPassword();
		Member member = memberService.findByUsername(name);

		if (!memberService.checkIfValidOldPassword(member, oldPassword)) {
			bindingResult.reject("password", "password.old.notequal");
			redirectAttributes.addFlashAttribute("message",
					new MapBuilder<>().addPair("error", "Überprüfe dein eingebenes Passwort.").build());
			return route;
		}

		memberService.changeMemberPassword(member, passwordChange.getPassword());

		redirectAttributes.addFlashAttribute("message",
				new MapBuilder<>().addPair("success", "Das Passwort wurde geändert.").build());

		return route;
	}

	@PostMapping(value = "/profile/upload", consumes = { "multipart/form-data" })
	public String imageUpload(@RequestParam(value = "avatar", required = true) MultipartFile avatar,
			Principal principal, Model model) {
		try {
			if (avatar == null)
				return "redirect:/profile?error";

			String name = avatar.getOriginalFilename();
		
			if (avatar.getName().equals(""))
				return "redirect:/profile?error";

			Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(jpeg|png|jpg))$)");
			Matcher matcher = pattern.matcher(name);
			boolean isAPic = matcher.matches();
			
			if (!isAPic) {
				return "redirect:/profile?error";
			}
			if (avatar.getBytes().length < 200000) {
				Member member = memberService.findByUsername(principal.getName());
				File file = new File(uploadPath + String.valueOf(member.getId()) + ".jpeg");
				file.createNewFile();
				avatar.transferTo(file);
				if (Pattern.compile(ISPNG).matcher(name).matches())
					file = convertPngToJpg(file);
				member.setAvatar(true);
				memberService.save(member);
			}
		} catch (Exception e) {
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
		model.addAttribute("passwordChange", new PasswordChange());
		model.addAttribute("isCreator", true);

		return "member";
	}

	public static File convertPngToJpg(File file) {
		try {
			File output = new File(file.getAbsolutePath());
			BufferedImage image = ImageIO.read(file);
			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
			ImageIO.write(result, "jpeg", output);
			return output;
		} catch (Exception e) {
			return null;
		}
	}

}
