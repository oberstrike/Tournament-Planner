package com.agil.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table
@Inheritance( strategy = InheritanceType.JOINED )
public class Game implements Comparable<Game> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{game.status.notempty}")
	private GameStatus status;

	@Enumerated(EnumType.STRING)
//	@NotNull(message = "{game.type.notempty}")
	private GameType type;

	@NotNull(message = "{game.startDate.notempty}")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	private Date startDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "game_teama", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
	private Team teamA;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "game_teamb", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
	private Team teamB;

	@NotNull
	private String name;

	private String video;

	public Game(GameStatus status, GameType gameType, Date startDate) {
		super();
		this.status = status;
		this.type = gameType;
//		this.type = GameType.VOLLEYBALL;
		this.startDate = startDate;
	}

	public Game() {
		this.status = GameStatus.PENDING;
	}

	public Game(GameType valueOf) {
		this();
		this.type = valueOf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		creator.addGame(this);
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		// https://www.youtube.com/watch?v=KyWMlJ987jg
		String youtubeId = video.split("=")[1];
		this.video = "https://www.youtube.com/embed/" + youtubeId;
	}
	
	public boolean isFinished() {
		if (this.status.equals(GameStatus.FINISHED)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", status=" + status + ", type=" + type + ", startDate=" + startDate + "]";
	}

	@Override
	public int compareTo(Game o) {
		return startDate.compareTo(o.getStartDate());
	}

}
