package com.agil.service;

import java.util.Optional;

import com.agil.model.Member;
import com.agil.model.Player;

public interface PlayerService {

	void save(Player player);
	
	Player findByName(String username);

	Optional<Player> findById(long id);
	
}
