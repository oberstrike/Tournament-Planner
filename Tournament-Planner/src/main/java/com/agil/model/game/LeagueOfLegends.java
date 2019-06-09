package com.agil.model.game;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.agil.model.Game;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LeagueOfLegends extends Game {
	private long killsTeamA;
	private long killsTeamB;
	private long winPointA;
	private long winPointB;
	
	public LeagueOfLegends() {
		super.setType(GameType.LEAGUEOFLEGENDS);
	}
	
	public LeagueOfLegends(GameStatus status, GameType gameType, Date startDate) {
		super(status, gameType, startDate);
		// TODO Auto-generated constructor stub
	}

	public LeagueOfLegends(GameType valueOf) {
		super(valueOf);
	}

	public LeagueOfLegends(GameStatus status, GameType gameType, Date startDate, int killsTeamA, int killsTeamB,
			int winPointA, int winPointB) {
		super(status, gameType, startDate);
		this.killsTeamA = killsTeamA;
		this.killsTeamB = killsTeamB;
		this.winPointA = winPointA;
		this.winPointB = winPointB;
	}

	public long getKillsTeamA() {
		return killsTeamA;
	}

	public void setKillsTeamA(long killsTeamA) {
		this.killsTeamA = killsTeamA;
	}

	public long getKillsTeamB() {
		return killsTeamB;
	}

	public void setKillsTeamB(long killsTeamB) {
		this.killsTeamB = killsTeamB;
	}

	public long getWinPointA() {
		return winPointA;
	}

	public void setWinPointA(int winPointA) {
		this.winPointA = winPointA;
	}

	public long getWinPointB() {
		return winPointB;
	}

	public void setWinPointB(int winPointB) {
		this.winPointB = winPointB;
	}

}
