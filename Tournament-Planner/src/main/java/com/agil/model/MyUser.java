package com.agil.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5010761307397329186L;

	private SimpleMember member;
	
	public MyUser(Member member, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(member.getUsername(), member.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.member = new SimpleMember(member.getEmail(), member.getUsername(), member.getRoles());
	}

	public SimpleMember getMember() {
		return member;
	}	

}
