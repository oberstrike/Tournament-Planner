package com.agil;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agil.model.Member;
import com.agil.model.Team;
import com.agil.repo.MemberRepository;
import com.agil.repo.TeamRepository;

@SpringBootApplication
public class TournamentPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentPlannerApplication.class, args);
	}
	
	@Autowired
	public BCryptPasswordEncoder encoder;
	
	@Bean
	CommandLineRunner init(TeamRepository repository, MemberRepository memberRepository) {
		return (args) -> {
			Member member = new Member("oberstrike", encoder.encode("mewtu123"), "markus.juergens@gmx.de");
			memberRepository.save(member);
			repository.save(new Team("Team Solo Mid", "Blau", new HashSet<>(Arrays.asList(member))));
		};
	}


}
