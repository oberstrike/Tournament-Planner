package com.agil.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{game.status.notempty}")
	private GameStatus status;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{game.type.notempty}")
	private GameType type;
	
	@NotNull(message = "{game.startDate.notempty}")
	private Date startDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATOR_ID")
	private Member creator;

	@ManyToMany
	private Set<Team> teams = new HashSet<>();

	private String name;
	
	public Game(GameStatus status, GameType type, Date startDate, Set<Team> teams, Member member) {
		super();
		this.status = status;
		this.type = type;
		this.startDate = startDate;
		this.teams = teams;
		this.creator = member;	
		
		for (Team team : teams) {
			team.addGame(this);
		}
		if(teams.size() == 2)
			this.name = teams.toArray()[0] + " vs " + teams.toArray()[1];	
	}
	
	public Game(String name, GameStatus status, GameType type, Date startDate, Set<Team> teams, Member member) {
		this(status, type, startDate, teams, member);
		this.name = name;
	}
	
	public Game(String name, GameStatus status, GameType type, Date startDate, Member member) {
		this(name, status, type, startDate, new HashSet<>(), member);
	}
	

	public Game() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		if (teams.size() <= 2)
			this.teams = teams;
		else
			throw new RuntimeException("Es dürfen nicht mehr als Teams in einem Spiel sein.");
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
