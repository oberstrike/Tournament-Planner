package com.agil.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.agil.utility.MemberRole;

/***
 * 
 * @author Markus 
 * @category Model
 *
 */


@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private boolean avatar;
	
	//Profilbild
	private byte[] avatarFile;

	@ElementCollection(targetClass = MemberRole.class)
	@JoinTable(name = "memberRoles", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "role", nullable = true)
	@Enumerated(EnumType.STRING)
	private Set<MemberRole> roles = new HashSet<>(Arrays.asList(MemberRole.ROLE_USER));

	@NotEmpty(message = "{username.notempty}")
	@Size(min=6, max=32, message="{username.badformat}")
	private String username;

	@NotEmpty(message = "{password.notempty}")
	private String password;
	
	@Email
	@NotEmpty(message = "{email.notempty}")
	private String email;
	
	@Transient
	@Size(min=8, max=32, message="{password.badformat}")
	private String passwordConfirm;
	
	@OneToMany(mappedBy = "creator")
	private Set<Team> teams = new HashSet<>();

	@OneToMany(mappedBy = "creator")
	private Set<Game> games = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "player_id", referencedColumnName = "id")
	private Player connectedPlayer;
	
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
		this(new HashSet<>(Arrays.asList(MemberRole.ROLE_USER)), username, password, email);
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

	public Set<Game> getGames() {
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
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

	public Player getPlayer() {
		return connectedPlayer;
	}

	public void setPlayer(Player player) {
		player.setMember(this);
		this.connectedPlayer = player;
	}

	public void addGame(Game game) {
		if(games.contains(game))
			return;
		
		this.games.add(game);
	}

	public void addTeam(Team team) {
		if(teams.contains(team))
			return;
		this.teams.add(team);
	}

	public boolean isAvatar() {
		return avatar;
	}

	public void setAvatar(boolean hasAvatar) {
		this.avatar = hasAvatar;
	}

	public byte[] getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(byte[] avatarFile) {
		this.avatarFile = avatarFile;
	}
	
	public void addRoles(MemberRole...memberRoles) {
		this.roles.addAll(Arrays.asList(memberRoles));
	}
	


}
