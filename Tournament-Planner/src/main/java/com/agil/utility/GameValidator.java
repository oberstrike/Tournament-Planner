package com.agil.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.agil.model.Game;
import com.agil.service.GameService;
@Component
public class GameValidator implements Validator {

	@Autowired
	private GameService gameService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Game game = (Game) target;
		
		if(game.getTeamA().getId() == game.getTeamB().getId()) {
			errors.rejectValue("game", "game.teamduplicate");
		}
		
		if(gameService.findOne(game.getName()).isPresent()) {
			errors.rejectValue("game" , "game.duplicate");
		}
		// game.badformat

	}

}
