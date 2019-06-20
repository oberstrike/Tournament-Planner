package com.agil.model;

import java.util.Set;

import com.agil.utility.MemberRole;

public class SimpleMember {

	String email;
	String username;
	Set<MemberRole> roles;
	
	public SimpleMember(String email, String username, Set<MemberRole> roles) {
		super();
		this.email = email;
		this.username = username;
		this.roles = roles;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Set<MemberRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<MemberRole> roles) {
		this.roles = roles;
	}
	
	public String getRolesReadable() {
		String output = "";
		for(MemberRole mr : getRoles()) {
			output += mr.getDescription() + ", ";
		}
		output = output.substring(0, output.length()-2);
		return output;
	}
	
}
