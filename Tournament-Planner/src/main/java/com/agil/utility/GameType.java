package com.agil.utility;

public enum GameType {
	VOLLEYBALL, LEAGUEOFLEGENDS;

	public static GameType fromLowerCase(String lowerCase) {
		return valueOf(lowerCase.toUpperCase().trim());
	}

	public String toReadeable() {
		String lower = name().toLowerCase();
		char[] result = new char[lower.length()];
		for (int i = 0; i < lower.toCharArray().length; i++) {
			if (i == 0)
				result[i] = Character.toUpperCase(lower.toCharArray()[i]);
			else
				result[i] = lower.toCharArray()[i];
		}
		return new String(result);
	}
	


}
