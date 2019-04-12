package com.agil.utility;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.agil.model.Game;

public class GameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Game.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		

	}

}
