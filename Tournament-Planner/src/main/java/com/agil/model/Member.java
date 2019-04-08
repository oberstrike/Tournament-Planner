package com.agil.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.agil.utility.MemberRole;


@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private Set<MemberRole> roles;
	private String username;
	private String password;
	private String email;

	
	protected Member() {
		
	}
	
	public Member(Set<MemberRole> roles, String username, String password, String email) {
		super();
		this.roles = roles;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public Member(String username, String password, String email) {
		this(new HashSet<>(Arrays.asList(MemberRole.USER)), username, password, email );
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<MemberRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<MemberRole> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
