package com.agil.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// Vom User gesetzt
	private String name;

	// Kann gesetzt werden
	private String teamcolor;


	// Member die verknüpft sind
	@ManyToMany
	private Set<Member> connectedMembers = new HashSet<>();
	
	@ManyToMany
	private Set<Game> games = new HashSet<>();
	
	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public Team(String teamname, String teamcolor, Set<Member> connectedMembers) {
		super();
		this.name = teamname;
		this.teamcolor = teamcolor;
		this.connectedMembers = connectedMembers;
	}

	public String getTeamname() {
		return name;
	}

	public void setTeamname(String teamname) {
		this.name = teamname;
	}

	public String getTeamcolor() {
		return teamcolor;
	}

	public void setTeamcolor(String teamcolor) {
		this.teamcolor = teamcolor;
	}



	public Set<Member> getConnectedMembers() {
		return connectedMembers;
	}

	public void setConnectedMembers(Set<Member> connectedMembers) {
		this.connectedMembers = connectedMembers;
	}


	protected Team() {
		// TODO Auto-generated constructor stub
	}

}
