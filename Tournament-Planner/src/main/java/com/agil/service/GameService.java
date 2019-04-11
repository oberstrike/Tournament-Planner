package com.agil.service;

import javax.validation.Valid;

import com.agil.model.Game;

public interface GameService {

	void save(@Valid Game game);
	
}
