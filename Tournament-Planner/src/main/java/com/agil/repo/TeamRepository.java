package com.agil.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.agil.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{

	Optional<Team> findByName(String name);

	List<Team> findByMembers_Id(long id);
}
