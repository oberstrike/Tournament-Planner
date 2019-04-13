package com.agil.service;

import java.util.Optional;

import com.agil.model.Member;

public interface MemberService {

	void save(Member member);
	
	Member findByUsername(String username);

	Member findByEmail(String email);

	Optional<Member> findById(long id);
	
}
