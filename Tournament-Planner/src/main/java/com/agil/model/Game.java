package com.agil.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.agil.utility.GameStatus;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Enumerated(EnumType.STRING)
	private GameStatus status;
	private Date startDate;
	
	
	private Set<Team> teams;


	public Set<Team> getTeams() {
		return teams;
	}


	public void setTeams(Set<Team> teams) {
		if(teams.size() <= 2)
			this.teams = teams;
		else
			throw new RuntimeException("Es dürfen nicht mehr als Teams in einem Spiel sein.");
	}
	
}
