package com.agil.utility;

public enum GameType {
	VOLLEYBALL("Volleyball"),
	LEAGUEOFLEGENDS("League of Legends");

	private String description;
	
	private GameType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public static GameType fromLowerCase(String lowerCase) {
		return valueOf(lowerCase.toUpperCase().trim());
	}
	
}
