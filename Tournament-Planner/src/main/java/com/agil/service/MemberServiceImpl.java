package com.agil.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agil.model.Member;
import com.agil.repo.MemberRepository;
import com.agil.utility.MemberRole;
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public void save(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
		member.setRoles(new HashSet<>(Arrays.asList(MemberRole.USER)));
		memberRepo.save(member);
	}

	@Override
	public Member findByUsername(String username) {
		return memberRepo.findByUsername(username);
	}

}
