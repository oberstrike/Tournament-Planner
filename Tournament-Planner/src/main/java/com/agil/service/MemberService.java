package com.agil.service;

import com.agil.model.Member;

public interface MemberService {

	void save(Member member);
	
	Member findByUsername(String username);
	
}
