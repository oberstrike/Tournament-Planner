package com.agil.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agil.model.Player;
import com.agil.repo.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepo;

	@Override
	public void save(Player player) {
		playerRepo.save(player);
	}

	@Override
	public Player findByName(String username) {
		return playerRepo.findByName(username);
	}

	@Override
	public Optional<Player> findById(long id) {
		return playerRepo.findById(id);
	}

}
