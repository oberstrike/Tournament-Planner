package com.agil.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.agil.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long>{

	Optional<Player> findByName(String name);

}
