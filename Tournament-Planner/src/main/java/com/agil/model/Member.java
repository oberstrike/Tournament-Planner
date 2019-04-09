package com.agil.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;

import com.agil.utility.MemberRole;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ElementCollection(targetClass = MemberRole.class)
	@JoinTable(name = "memberRoles", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "role", nullable = true)
	@Enumerated(EnumType.STRING)
	private Set<MemberRole> roles = new HashSet<>(Arrays.asList(MemberRole.USER));

	private String username;
	private String password;
	
	@Email
	private String email;
	
	private String passwordConfirm;
	
	@ManyToMany
	private Set<Team> teams = new HashSet<>();



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Member() {

	}

	public Member(Set<MemberRole> roles, String username, String password, String email) {
		super();
		this.roles = roles;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Member(String username, String password, String email) {
		this(new HashSet<>(Arrays.asList(MemberRole.USER)), username, password, email);
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

	public void setPasswordConfirm(String password) {
		this.passwordConfirm = password;
	}

	public Object getPasswordConfirm() {
		return passwordConfirm;
	}

}
