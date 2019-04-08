package com.agil.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.agil.model.Team;
import com.agil.repo.TeamRepository;

public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	
	@Override
	public List<Team> getAll() {
		return StreamSupport.stream(teamRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

}
