package com.agil.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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


	@Override
	public List<Player> findAll() {
		return StreamSupport.stream(playerRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

}
