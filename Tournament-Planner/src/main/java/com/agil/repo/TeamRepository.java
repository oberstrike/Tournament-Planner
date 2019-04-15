package com.agil.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agil.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

	Optional<Team> findByName(String name);

	List<Team> findByPlayers_Id(long id);

	List<Team> findByNameIgnoreCase(String name);

	List<Team> findByNameIgnoreCaseContaining(String name);
}
