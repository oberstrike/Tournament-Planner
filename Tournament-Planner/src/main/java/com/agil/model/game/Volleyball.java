package com.agil.model.game;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Team;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

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
	
	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, GameStatus status, GameType type, Date startDate, Set<Team> teams) {
		super(status, type, startDate, teams);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;		
		initalizeGame();
	}
	
	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, String name, GameStatus status, GameType type, Date startDate, Set<Team> teams, Member member) {
		super(name, status, type, startDate, teams, member);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;	
		initalizeGame();
	}
	
	private void initalizeGame() {
		this.pointsA = 0;
		this.pointsB = 0;
		this.setsA = 0;
		this.setsB = 0;
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