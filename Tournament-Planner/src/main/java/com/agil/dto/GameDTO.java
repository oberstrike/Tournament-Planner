package com.agil.dto;

import java.util.Date;

import com.agil.model.Game;
import com.agil.utility.GameStatus;

public class GameDTO {
	private long id;

	private TeamDTO teamA;

	private TeamDTO teamB;

	private Date startDate;

	private String type;

	private GameStatus status;

	public GameDTO(Game game) {
		this.id = game.getId();
		this.teamA = new TeamDTO(game.getTeamA());
		this.teamB = new TeamDTO(game.getTeamB());
		this.type = game.getType().name();
		this.status = game.getStatus();
		this.startDate = game.getStartDate();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TeamDTO getTeamA() {
		return teamA;
	}

	public void setTeamA(TeamDTO teamA) {
		this.teamA = teamA;
	}

	public TeamDTO getTeamB() {
		return teamB;
	}

	public void setTeamB(TeamDTO teamB) {
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
