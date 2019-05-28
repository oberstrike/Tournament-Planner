package com.agil.service;

import java.util.List;
import java.util.Optional;

import com.agil.model.Member;
import com.agil.model.Team;

public interface TeamService {
	public List<Team> getAll();

	public Optional<Team> findOne(String name);
	
	public List<Team> findByMembers_Id(long id);

	Optional<Team> findOne(Long id);

	List<Team> findByNameIgnoreCase(String name);
	
	List<Team> findByNameIgnoreCaseContaining(String name);

	public void save(Team teamForm);

	public Optional<Team> findByName(String name);

	public void createByMemberName(Team teamForm, Member name);

}

