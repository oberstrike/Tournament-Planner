package com.agil.dto;


import java.util.Date;

import com.agil.model.Game;
import com.agil.utility.GameStatus;

public class GameDTO {
	private long id;
	
	private long teamA;
	
	private long teamB;

	private Date startDate;

	private String type;

	private GameStatus status;
	
	public GameDTO(Game game) {
		this.id = game.getId();
		this.teamA = game.getTeamA().getId();
		this.teamB = game.getTeamB().getId();
		this.type = game.getType().name();
		this.status =  game.getStatus();
		this.startDate = game.getStartDate();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTeamA() {
		return teamA;
	}

	public void setTeamA(long teamA) {
		this.teamA = teamA;
	}

	public long getTeamB() {
		return teamB;
	}

	public void setTeamB(long teamB) {
		this.teamB = teamB;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}
}
