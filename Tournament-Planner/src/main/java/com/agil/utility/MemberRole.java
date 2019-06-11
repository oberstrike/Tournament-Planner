package com.agil.utility;

public enum MemberRole {
	ROLE_ADMIN("Administrator"), 
	ROLE_USER("Benutzer");
	
	private String description;
	
	private MemberRole(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
