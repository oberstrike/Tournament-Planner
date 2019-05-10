package com.agil.model.game;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Team;
import com.agil.service.TeamService;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Volleyball extends Game {

	private int setsRule;
	private int pointsRule;
	private boolean tiebreakRule;
	private int pointsA[];
	private int currentSet;
	private int pointsB[];
	private int setsA;
	private int setsB;
	private String tempTeamAName;
	private String tempTeamBName;

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, GameStatus status,
			Date startDate, Team teamA, Team teamB) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		initalizeGame(setsRule);
	}

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, String name, GameStatus gameStatus, Date startDate, String tempTeamAName, String tempTeamBName, Member member) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		this.setName(name);
		this.setCreator(member);
		initalizeGame(setsRule);
	}

	private void initalizeGame(int setsRule) {
		this.currentSet = 0;
		this.pointsA = new int[setsRule];
		this.pointsB = new int[setsRule];
		this.setsA = 0;
		this.setsB = 0;
	}

	public String getTempTeamAName() {
		return tempTeamAName;
	}

	public String getTempTeamBName() {
		return tempTeamBName;
	}

	public void addA() {
		// Punkt hinzufügen, wenn +2 zu B und >= pointsRule -> currentSet++;
		pointsA[currentSet]++;
		if (pointsA[currentSet] > pointsRule && pointsA[currentSet] >= pointsB[currentSet] + 2 && !gameOver()) {
			// neuer Satz
			setsA++;
			currentSet++;
			pointsA[currentSet] = 0;
			pointsB[currentSet] = 0;
		} else {
			pointsA[currentSet]++;
		}
	}

	public void minusA() {
		if (pointsA[currentSet] == 0 && pointsB[currentSet] == 0) {
			// gehe in letzten Satz
			if (currentSet > 0) {
				setsA--;
				currentSet--;
			}
		} else {
			pointsA[currentSet]--;
		}
	}

	public void addB() {
		// Punkt hinzufügen, wenn +2 zu B und >= pointsRule -> currentSet++;
		pointsB[currentSet]++;
		if (pointsB[currentSet] > pointsRule && pointsB[currentSet] >= pointsA[currentSet] + 2 && !gameOver()) {
			// neuer Satz
			setsB++;
			currentSet++;
			pointsB[currentSet] = 0;
			pointsA[currentSet] = 0;
		} else {
			pointsB[currentSet]++;
		}
	}

	public void minusB() {
		if (pointsB[currentSet] == 0 && pointsA[currentSet] == 0) {
			// gehe in letzten Satz
			if (currentSet > 0) {
				setsB--;
				currentSet--;
			}
		} else {
			pointsB[currentSet]--;
		}
	}

	public boolean gameOver() {
		// TODO: implement game over check
		return false;
	}

	protected Volleyball() {
	}

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

	public int getPointsA() {
		return pointsA[currentSet];
	}

	public void setPointsA(int pointsA) {
		this.pointsA[currentSet] = pointsA;
	}

	public int getPointsB() {
		return pointsB[currentSet];
	}

	public void setPointsB(int pointsB) {
		this.pointsB[currentSet] = pointsB;
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

	@Override
	public String toString() {
		return "Volleyball [setsRule=" + setsRule + ", pointsRule=" + pointsRule + ", tiebreakRule=" + tiebreakRule
				+ ", pointsA=" + Arrays.toString(pointsA) + ", currentSet=" + currentSet + ", pointsB="
				+ Arrays.toString(pointsB) + ", setsA=" + setsA + ", setsB=" + setsB + ", tempTeamAName="
				+ tempTeamAName + ", tempTeamBName=" + tempTeamBName + "]";
	}

}