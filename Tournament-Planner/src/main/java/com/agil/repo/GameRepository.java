package com.agil.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agil.model.Game;
import com.agil.utility.GameType;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{

	Optional<Game> findByName(String name);
	
	List<Game> findByNameIgnoreCase(String name);

	List<Game> findByNameIgnoreCaseContaining(String name);

	List<Game> findByType(GameType type);
	
}
