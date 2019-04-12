package com.agil.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.agil.model.Team;
import com.agil.service.TeamService;
@Component
public class TeamValidator implements Validator {

	@Autowired
	private TeamService teamService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Team.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Team team = (Team) target;
		if(teamService.findByNameIgnoreCase(team.getName()) != null) {
			errors.rejectValue("name", "teamname.duplicate");
		}
				
	}





}
