package com.agil.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agil.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long>{

	Member findByUsername(String username);

	Member findByEmail(String email);

}
