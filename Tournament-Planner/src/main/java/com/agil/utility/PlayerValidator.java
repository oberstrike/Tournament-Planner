package com.agil.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.service.MemberService;
import com.agil.service.PlayerService;

@Component
public class PlayerValidator implements Validator {

	@Autowired
	private PlayerService playerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Player.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Player player = (Player) target;
		
		if (playerService.findByName(player.getName()).isPresent()) {
			errors.rejectValue("name", "playername.duplicate");
		}
		
		if(player.getName().length() < 4) {
			errors.reject("name", "playername.badformat");
		}
		

	}

}
