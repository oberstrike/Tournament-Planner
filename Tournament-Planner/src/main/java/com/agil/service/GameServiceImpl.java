package com.agil.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.agil.repo.GameRepository;

public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository repo;
	
	
}
