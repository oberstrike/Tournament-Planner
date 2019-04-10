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
	private Set<Member> members = new HashSet<>();
	
	@ManyToMany
	private Set<Game> games = new HashSet<>();
	 
	
	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", teamcolor=" + teamcolor + ", members=" + members + ", games="
				+ games + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team(String teamname, String teamcolor, Set<Member> members) {
		super();
		this.name = teamname;
		this.teamcolor = teamcolor;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String teamname) {
		this.name = teamname;
	}

	public String getTeamcolor() {
		return teamcolor;
	}

	public void setTeamcolor(String teamcolor) {
		this.teamcolor = teamcolor;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void Members(Set<Member> members) {
		this.members = members;
	}

	protected Team() {
		// TODO Auto-generated constructor stub
	}

	public void addGame(Game game) {
		this.games.add(game);
	}
	
	public void addMember(Member member) {
		this.members.add(member);
	}

}
