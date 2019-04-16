package com.agil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agil.model.Player;
import com.agil.repo.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public void save(Player player) {
		playerRepository.save(player);
	}

	@Override
	public Optional<Player> findByName(String name) {
		return playerRepository.findByName(name);
	}

	@Override
	public Optional<Player> findById(long id) {
		return playerRepository.findById(id);
	}

}
