package com.agil.model.game;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
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

	@Column(name = "sets_rule", columnDefinition = "int default 0")
	private int setsRule;

	@Column(name = "points_rule", columnDefinition = "int default 0")
	private int pointsRule;

	@Column(name = "tiebreak_rule", columnDefinition = "boolean default true")
	private boolean tiebreakRule;

	private int pointsA[];

	@Column(name = "current_set", columnDefinition = "int default 0")
	private int currentSet;

	private int pointsB[];

	@Column(name = "sets_a", columnDefinition = "int default 0")
	private int setsA;

	@Column(name = "sets_b", columnDefinition = "int default 0")
	private int setsB;

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, GameStatus status, Date startDate, Team teamA,
			Team teamB) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		initalizeGame(setsRule);
	}

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, String name, GameStatus gameStatus,
			Date startDate, String tempTeamAName, String tempTeamBName, Member member) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		this.setName(name);
		this.setCreator(member);
		initalizeGame(setsRule);
	}

	/*
	 * Testen nach Satztyp (Norm, Tie)? Testen ob Satz vorbei? Testen ob Spiel
	 * vorbei? Punkt hinzufügen! if(!gameOver()) -> Spiel läuft else if(setsA +
	 * setsB == setsRule) -> Tiebreak -> if(tiebreakRule) else -> GameOver else
	 * if(setsA == setsRule || setsB == setsRule) -> GameOver else -> Game Running
	 */

	public void addA() {
		if (getStatus() == GameStatus.PENDING) {
			setStatus(GameStatus.RUNNING);
		}
		if (getStatus() == GameStatus.RUNNING) {
			if (setsA + setsB == setsRule) {
				if (tiebreakRule) {
					if (pointsA[pointsA.length - 1] >= 15
							&& pointsA[pointsA.length - 1] >= pointsB[pointsB.length - 1] + 2
							|| pointsB[pointsB.length - 1] >= 15
									&& pointsB[pointsB.length - 1] >= pointsA[pointsA.length - 1] + 2) {
						setsA++;
						setGameOver(true);
					}
					pointsA[currentSet]++;
				} else {
					setGameOver(true);
				}
			} else if (setsA == setsRule || setsB == setsRule) {
				setGameOver(true);
			} else {
				// Check current Set and Points
				if (pointsA[currentSet] >= pointsRule && pointsA[currentSet] >= pointsB[currentSet] + 2) {
					setsA++;
					if (currentSet < setsRule - 1) {
						currentSet++;
						pointsA[currentSet] = 0;
						pointsB[currentSet] = 0;
					}
				} else {
					pointsA[currentSet]++;
				}
			}

		}
	}

	public void addB() {
		if (getStatus() == GameStatus.PENDING) {
			setStatus(GameStatus.RUNNING);
		}
		if (getStatus() == GameStatus.RUNNING) {
			if (setsA + setsB == setsRule) {
				if (tiebreakRule) {
					if (pointsA[pointsA.length - 1] >= 15
							&& pointsA[pointsA.length - 1] >= pointsB[pointsB.length - 1] + 2
							|| pointsB[pointsB.length - 1] >= 15
									&& pointsB[pointsB.length - 1] >= pointsA[pointsA.length - 1] + 2) {
						setsB++;
						setGameOver(true);
					}
					pointsB[currentSet]++;
				} else {
					setGameOver(true);
				}
			} else if (setsA == setsRule || setsB == setsRule) {
				setGameOver(true);
			} else {
				// Check current Set and Points
				if (pointsB[currentSet] >= pointsRule && pointsB[currentSet] >= pointsA[currentSet] + 2) {
					// neuer Satz
					setsB++;
					if (currentSet < setsRule - 1) {
						currentSet++;
						pointsA[currentSet] = 0;
						pointsB[currentSet] = 0;
					}
				} else {
					pointsB[currentSet]++;
				}
			}

		}
	}

	private void initalizeGame(int setsRule) {
		this.currentSet = 0;
		this.pointsA = new int[setsRule];
		this.pointsB = new int[setsRule];
		for (int i = 0; i < pointsA.length; i++) {
			pointsA[i] = 0;
			pointsB[i] = 0;
		}
		this.setsA = 0;
		this.setsB = 0;
	}

	public void initVolleyballGame() {
		this.pointsA = new int[getSetsRule()];
		this.pointsB = new int[getSetsRule()];
		for (int i = 0; i < this.pointsA.length; i++) {
			this.pointsA[i] = 0;
			this.pointsB[i] = 0;
		}
	}

//	public void addA() {
//		// Punkt hinzufügen, wenn +2 zu B und >= pointsRule -> currentSet++;
//		if (getStatus() == GameStatus.PENDING) {
//			setStatus(GameStatus.RUNNING);
//		}
//		if (getStatus() == GameStatus.RUNNING) {
////			pointsA[currentSet]++;
//			if (pointsA[currentSet] > pointsRule && pointsA[currentSet] >= pointsB[currentSet] + 2 && !getGameOver()) {
//				// neuer Satz
//				setsA++;
//				currentSet++;
//				pointsA[currentSet] = 0;
//				pointsB[currentSet] = 0;
//			} else if (isInTiebreak()) {
//
//			} else {
//				pointsA[currentSet]++;
//			}
////			pointsA[currentSet]++;
//
//		}
//
//	}

	public void minusA() {
		if (!getGameOver()) {
			if (pointsA[currentSet] == 0 && pointsB[currentSet] == 0) {
				// gehe in letzten Satz
				if (currentSet > 0) {
					setsA--;
					currentSet--;
					pointsA[currentSet]--;
				}
			} else {
				if (pointsA[currentSet] > 0) {
					pointsA[currentSet]--;
				}
			}
		}
	}

//	public void addB() {
//		if (getStatus() == GameStatus.PENDING) {
//			setStatus(GameStatus.RUNNING);
//		}
//		if (getStatus() == GameStatus.RUNNING) {
//			// Punkt hinzufügen, wenn +2 zu B und >= pointsRule -> currentSet++;
//			if (pointsB[currentSet] > pointsRule && pointsB[currentSet] >= pointsA[currentSet] + 2 && !getGameOver()) {
//				// neuer Satz
//				setsB++;
//				currentSet++;
//				pointsB[currentSet] = 0;
//				pointsA[currentSet] = 0;
//			} else if (isInTiebreak()) {
//
//			} else {
//				pointsB[currentSet]++;
//			}
//		}
//	}

	public void minusB() {
		if (!getGameOver()) {
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
	}

	private boolean getGameOver() {
		if (setsA == setsRule || setsB == setsRule) {
			setStatus(GameStatus.FINISHED);
		}
		if (setsA + setsB == setsRule && !tiebreakRule) {
			setStatus(GameStatus.FINISHED);
		}
		if (getStatus() == GameStatus.FINISHED) {
			return true;
		} else {
			return false;
		}
	}

	private void setGameOver(boolean state) {
		if (state) {
			setStatus(GameStatus.FINISHED);
		}
		if (!state) {
			setStatus(GameStatus.RUNNING);
		}
	}

	public boolean isInTiebreak() {
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
				+ Arrays.toString(pointsB) + ", setsA=" + setsA + ", setsB=" + setsB + "]";
	}

}