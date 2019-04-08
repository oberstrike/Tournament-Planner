package com.agil.model;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// Vom User gesetzt
	private String teamname;

	// Kann gesetzt werden
	private String teamcolor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Team(String teamname, String teamcolor, Set<String> teamMembers, Set<Member> connectedMembers) {
		super();
		this.teamname = teamname;
		this.teamcolor = teamcolor;
		this.teamMembers = teamMembers;
		this.connectedMembers = connectedMembers;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamcolor() {
		return teamcolor;
	}

	public void setTeamcolor(String teamcolor) {
		this.teamcolor = teamcolor;
	}

	public Set<String> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(Set<String> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public Set<Member> getConnectedMembers() {
		return connectedMembers;
	}

	public void setConnectedMembers(Set<Member> connectedMembers) {
		this.connectedMembers = connectedMembers;
	}

	// Namen von allen Mitspielern (müssen nicht registriert)
	private Set<String> teamMembers;

	// Member die verknüpft
	private Set<Member> connectedMembers;

	protected Team() {
		// TODO Auto-generated constructor stub
	}

}
