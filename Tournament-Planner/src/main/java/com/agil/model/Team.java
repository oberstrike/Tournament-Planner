package com.agil.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity
public class Team {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// Vom User gesetzt
	@Size(min=6, message="{teamname.badformat}")
	private String name;

	// Kann zum Individualisieren genutzt werden
	private String teamcolor;

	// Member die verknüpft sind
	@ManyToMany(mappedBy = "teams")
	private Set<Player> players = new HashSet<>();

	@OneToMany(mappedBy = "teamA")
	private Set<Game> homegames = new HashSet<>();
	
	@OneToMany(mappedBy = "teamB")
	private Set<Game> awaygames = new HashSet<>();
	
	
	@ManyToOne
	private Member creator;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team(String teamname, String teamcolor) {
		super();
		this.name = teamname;
		this.teamcolor = teamcolor;
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


	protected Team() {
		// TODO Auto-generated constructor stub
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public Set<Game> getHomegames() {
		return homegames;
	}

	public void setHomegames(Set<Game> homegames) {
		this.homegames = homegames;
	}

	public Set<Game> getAwaygames() {
		return awaygames;
	}

	public void setAwaygames(Set<Game> awaygames) {
		this.awaygames = awaygames;
	}

	public void addAwayGame(Game game) {
		this.awaygames.add(game);
	}
	
	public void addHomeGame(Game game) {
		this.homegames.add(game);
	}

	public void addPlayer(Player player) {
		player.addTeam(this);
		this.players.add(player);
	}

}
