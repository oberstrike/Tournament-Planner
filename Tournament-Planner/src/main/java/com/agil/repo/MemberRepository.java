package com.agil.repo;

import org.springframework.data.repository.CrudRepository;

import com.agil.model.Member;

public interface MemberRepository extends CrudRepository<Member, Long>{

	Member findByUsername(String username);

}
