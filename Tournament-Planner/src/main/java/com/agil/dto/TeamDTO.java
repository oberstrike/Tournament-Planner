package com.agil.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.agil.model.Team;

public class TeamDTO {

	private long id;
	
	private String name;
	
	private List<Long> players;

	private long creator;
	
	public TeamDTO(Team team) {
		this.id = team.getId();
		this.name = team.getName();
		this.players = team.getPlayers().stream().map(each -> each.getId()).collect(Collectors.toList());
		this.creator = team.getCreator().getId();
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getPlayers() {
		return players;
	}

	public void setPlayers(List<Long> players) {
		this.players = players;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}
	
}
