package com.agil.service;

import java.util.List;
import java.util.Optional;

import com.agil.model.Member;

public interface MemberService {

	void save(Member member);
	
	Member findByUsername(String username);
	
	Member findByEmail(String email);

	Optional<Member> findById(long id);

	boolean checkIfValidOldPassword(Member member, String password);

	void changeMemberPassword(Member member, String password);
	
}
