package com.agil.repo;

import org.springframework.data.repository.CrudRepository;

import com.agil.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long>{

}
