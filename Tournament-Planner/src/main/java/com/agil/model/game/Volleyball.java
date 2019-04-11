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
	
	
}