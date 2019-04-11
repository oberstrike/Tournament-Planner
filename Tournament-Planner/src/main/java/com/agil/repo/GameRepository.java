package com.agil.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agil.model.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{

}
