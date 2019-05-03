package com.agil.model.game;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.agil.model.Game;
import com.agil.utility.GameType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LeagueOfLegends extends Game {
	private int killsTeamA;
	private int killsTeamB;
	private int winPointA;
	private int winPointB;
	
	public LeagueOfLegends() {
		super.setType(GameType.LEAGUEOFLEGENDS);
	}
	
	public int getKillsTeamA() {
		return killsTeamA;
	}

	public void setKillsTeamA(int killsTeamA) {
		this.killsTeamA = killsTeamA;
	}

	public int getKillsTeamB() {
		return killsTeamB;
	}

	public void setKillsTeamB(int killsTeamB) {
		this.killsTeamB = killsTeamB;
	}

	public int getWinPointA() {
		return winPointA;
	}

	public void setWinPointA(int winPointA) {
		this.winPointA = winPointA;
	}

	public int getWinPointB() {
		return winPointB;
	}

	public void setWinPointB(int winPointB) {
		this.winPointB = winPointB;
	}

}
