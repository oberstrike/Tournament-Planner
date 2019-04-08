package com.agil.repo;

import org.springframework.data.repository.CrudRepository;

import com.agil.model.Game;

public interface GameRepository extends CrudRepository<Game, Long>{

}
