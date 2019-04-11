package com.agil.model.game;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.agil.model.Game;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Volleyball extends Game{

	private int setsRule;
	
	private int pointsRule;
	
	private boolean tiebreakRule;
	
	private int pointsA;
	private int pointsB;
	
	private int setsA;
	private int setsB;
	
	private String teamA;
	private String teamB;
	
	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule) {
		super();
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;		
	}
	
	protected Volleyball() {}
	
	public int getSetsRule() {
		return this.setsRule;
	}
	
	public int getPointsRule() {
		return this.pointsRule;
	}
	
	public boolean getTiebreakRule() {
		return this.tiebreakRule;
	}
	
	public void setSets(int setsRule) {
		this.setsRule = setsRule;
	}
	
	public void setPoints(int pointsRule) {
		this.pointsRule = pointsRule;
	}
	
	public void setTiebreak(boolean tiebreakRule) {
		this.tiebreakRule = tiebreakRule;
	}
	
	public void addPoint(String team) {
		if(team.equals("a")) {
			
		} else if(team.equals("b")) {
			
		}
	}
	
	public int getPointsA() {
		return pointsA;
	}

	public void setPointsA(int pointsA) {
		this.pointsA = pointsA;
	}

	public int getPointsB() {
		return pointsB;
	}

	public void setPointsB(int pointsB) {
		this.pointsB = pointsB;
	}

	public int getSetsA() {
		return setsA;
	}

	public void setSetsA(int setsA) {
		this.setsA = setsA;
	}

	public int getSetsB() {
		return setsB;
	}

	public void setSetsB(int setsB) {
		this.setsB = setsB;
	}

	public String getTeamA() {
		return teamA;
	}

	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}

	public void setSetsRule(int setsRule) {
		this.setsRule = setsRule;
	}

	public void setPointsRule(int pointsRule) {
		this.pointsRule = pointsRule;
	}

	public void setTiebreakRule(boolean tiebreakRule) {
		this.tiebreakRule = tiebreakRule;
	}
	
}