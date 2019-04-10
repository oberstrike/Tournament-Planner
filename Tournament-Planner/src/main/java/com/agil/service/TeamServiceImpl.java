package com.agil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agil.model.Team;
import com.agil.repo.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	
	@Override
	public List<Team> getAll() {
		return StreamSupport.stream(teamRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}


	@Override
	public Optional<Team> findOne(Long id) {
		if(id != null)
			return teamRepository.findById(id);
		return Optional.empty();
	}


	@Override
	public Optional<Team> findOne(String name) {
		return teamRepository.findByName(name);
	}

	@Override
	public List<Team> findByMembers_Id(long id){
		return teamRepository.findByMembers_Id(id);
	}


	@Override
	public List<Team> findByNameIgnoreCase(String name) {
		return teamRepository.findByNameIgnoreCase(name);
	}
	
	
}
