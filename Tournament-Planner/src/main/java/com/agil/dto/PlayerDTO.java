package com.agil.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.agil.model.Player;

public class PlayerDTO {

	private long id;
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

	public List<TeamDTO> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamDTO> teams) {
		this.teams = teams;
	}



	private String name;
	private List<TeamDTO> teams;

	
	public PlayerDTO(Player player) {
		this(player.getId(), player.getName(),player.getTeams().stream().map(TeamDTO::new).collect(Collectors.toList()));
	}
	
	public PlayerDTO(long id, String name, List<TeamDTO> teams) {
		super();
		this.id = id;
		this.name = name;
		this.teams = teams;
	}



	public PlayerDTO() {
		// TODO Auto-generated constructor stub
	}
}
