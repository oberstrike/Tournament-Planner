package com.agil.service;

import java.util.List;
import java.util.Optional;

import com.agil.model.Player;

public interface PlayerService {
	void save(Player player);
	
	Optional<Player> findByName(String name);

	Optional<Player> findById(long id);

	List<Player> findAll();
	
	
	
}
