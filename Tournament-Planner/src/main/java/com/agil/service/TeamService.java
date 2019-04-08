package com.agil.service;

import java.util.List;
import java.util.Optional;

import com.agil.model.Team;

public interface TeamService {
	public List<Team> getAll();

	public Optional<Team> findOne(long id);

	public Optional<Team> findOne(String name);
}

