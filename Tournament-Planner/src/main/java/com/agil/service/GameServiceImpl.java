package com.agil.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.agil.model.Game;
import com.agil.repo.GameRepository;

public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository repo;

	@Override
	public void save(@Valid Game game) {
		repo.save(game);
	}
	
	
}
