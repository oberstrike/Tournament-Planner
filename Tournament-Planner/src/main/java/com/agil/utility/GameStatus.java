package com.agil.utility;

public enum GameStatus {
	STARTED("Gestartet"),
	PENDING("Ausstehend"),
	FINISHED("Fertig"),
	RUNNING("Laufend"),
	CANCELLED("Abgebrochen");
	
	private String description;
	
	private GameStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
