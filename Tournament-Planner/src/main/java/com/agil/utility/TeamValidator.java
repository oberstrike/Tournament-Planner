package com.agil.utility;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.agil.model.Team;
@Component
public class TeamValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Team.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}





}
