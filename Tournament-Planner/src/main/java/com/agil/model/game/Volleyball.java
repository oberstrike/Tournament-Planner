package com.agil.model.game;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Team;
import com.agil.service.TeamService;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@PrimaryKeyJoinColumn(referencedColumnName="id")
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

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, Date startDate, Team teamA,
			Team teamB) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		initVolleyballGame();
	}

	public Volleyball(int setsRule, int pointsRule, boolean tiebreakRule, String name,
			Date startDate, String tempTeamAName, String tempTeamBName, Member member) {
		super(GameStatus.PENDING, GameType.VOLLEYBALL, startDate);
		this.setsRule = setsRule;
		this.pointsRule = pointsRule;
		this.tiebreakRule = tiebreakRule;
		this.setName(name);
		this.setCreator(member);
		initVolleyballGame();
	}

	public void addA() {

		if (getStatus().equals(GameStatus.PENDING)) {
			setStatus(GameStatus.RUNNING);
		}
		// Spiel läuft
		if (getStatus().equals(GameStatus.RUNNING)) {
			// Tiebreak möglich?
			if (tiebreakRule) {
				if (setsA + setsB == setsRule - 1 && setsA != setsRule - 1) {
					// Ich bin im Tiebreak
					if (pointsA[currentSet] < 15) {
						// Satz läuft noch
						pointsA[currentSet]++;
					} else {
						if (pointsA[currentSet] - pointsB[currentSet] < 2) {
							// 2 Vorsprung nicht erreicht
							pointsA[currentSet]++;
						} else {
							// 2 Vorsprung erreicht -> Game Over
							setsA++;
							setGameOver(true);
						}
					}
				} else if (setsA == setsRule - 1) {
					// Ich habe die Gewinnsätze erreicht
					setGameOver(true);
				} else {
					// Ich bin nicht im Tiebreak, aber er ist möglich
					if (pointsA[currentSet] >= pointsRule && pointsA[currentSet] - pointsB[currentSet] >= 2) {
						// Ich habe die Punkte Regel und den Vorsprung erreicht
						setsA++;
						if (setsRule - 1 > currentSet) {
							currentSet++;
						}
					} else {
						// Ich bekomme einen Punkt
						pointsA[currentSet]++;
					}
				}

			} else {
				if (setsA == setsRule || setsB == setsRule || setsA + setsB == setsRule) {
					// Ein Team hat die Gewinnsätze erreicht oder die Sätze sind gespielt
					setGameOver(true);
				} else {
					// Das Spiel läuft noch und kein Team hat die Satzregel erreicht
					if (pointsA[currentSet] >= pointsRule && pointsA[currentSet] - pointsB[currentSet] >= 2) {
						// Ich habe die Punkte Regel und den Vorsprung erreicht
						setsA++;
						if (setsRule - 1 > currentSet) {
							currentSet++;
						}
					} else {
						// Ich bekomme einen Punkt
						pointsA[currentSet]++;
					}

				}
			}
		}

	}

	public void addB() {

		if (getStatus().equals(GameStatus.PENDING)) {
			setStatus(GameStatus.RUNNING);
		}
		// Spiel läuft
		if (getStatus().equals(GameStatus.RUNNING)) {
			// Tiebreak möglich?
			if (tiebreakRule) {
				if (setsA + setsB == setsRule - 1 && setsB != setsRule - 1) {
					// Ich bin im Tiebreak
					if (pointsB[currentSet] < 15) {
						// Satz läuft noch
						pointsB[currentSet]++;
					} else {
						if (pointsB[currentSet] - pointsA[currentSet] < 2) {
							// 2 Vorsprung nicht erreicht
							pointsB[currentSet]++;
						} else {
							// 2 Vorsprung erreicht -> Game Over
							setsB++;
							setGameOver(true);
						}
					}
				} else if (setsB == setsRule - 1) {
					// Ich habe die Gewinnsätze erreicht
					setGameOver(true);
				} else {
					// Ich bin nicht im Tiebreak, aber er ist möglich
					if (pointsB[currentSet] >= pointsRule && pointsB[currentSet] - pointsA[currentSet] >= 2) {
						// Ich habe die Punkte Regel und den Vorsprung erreicht
						setsB++;
						if (setsRule - 1 > currentSet) {
							currentSet++;
						}
					} else {
						// Ich bekomme einen Punkt
						pointsB[currentSet]++;
					}
				}

			} else {
				if (setsA == setsRule || setsB == setsRule || setsA + setsB == setsRule) {
					// Ein Team hat die Gewinnsätze erreicht oder die Sätze sind gespielt
					setGameOver(true);
				} else {
					// Das Spiel läuft noch und kein Team hat die Satzregel erreicht
					if (pointsB[currentSet] >= pointsRule && pointsB[currentSet] - pointsA[currentSet] >= 2) {
						// Ich habe die Punkte Regel und den Vorsprung erreicht
						setsB++;
						if (setsRule - 1 > currentSet) {
							currentSet++;
						}
					} else {
						// Ich bekomme einen Punkt
						pointsB[currentSet]++;
					}

				}
			}
		}

	}

	public void initVolleyballGame() {
		this.currentSet = 0;
		this.setsA = 0;
		this.setsB = 0;
		if (tiebreakRule) {
			setsRule++;
		}
		this.pointsA = new int[getSetsRule()];
		this.pointsB = new int[getSetsRule()];
		for (int i = 0; i < this.pointsA.length; i++) {
			this.pointsA[i] = 0;
			this.pointsB[i] = 0;
		}

	}

	public void minusA() {
		if (!isFinished()) {
			if (pointsA[currentSet] == 0 && pointsB[currentSet] == 0) {
				// gehe in letzten Satz
				if (currentSet > 0 && setsA > 0) {
					setsA--;
					currentSet--;
				}
			} else {
				if (pointsA[currentSet] > 0) {
					pointsA[currentSet]--;
				}
			}
		}
	}

	public void minusB() {
		if (!isFinished()) {
			if (pointsB[currentSet] == 0 && pointsA[currentSet] == 0) {
				// gehe in letzten Satz
				if (currentSet > 0 && setsB > 0) {
					setsB--;
					currentSet--;
				}
			} else {
				if (pointsB[currentSet] > 0)
					pointsB[currentSet]--;
			}
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

	public Volleyball() {
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

	public String getCompleteScore() {
		String score_output = "";
		for (int i = 0; i < setsRule; i++) {
			score_output += pointsA[i] + "-" + pointsB[i];
			if (i < setsRule - 1) {
				score_output += ", ";
			}
		}
		return score_output;
	}

	@Override
	public String toString() {
		return "Volleyball [setsRule=" + setsRule + ", pointsRule=" + pointsRule + ", tiebreakRule=" + tiebreakRule
				+ ", pointsA=" + Arrays.toString(pointsA) + ", currentSet=" + currentSet + ", pointsB="
				+ Arrays.toString(pointsB) + ", setsA=" + setsA + ", setsB=" + setsB + "]";
	}

}