package com.agil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.agil.model.Team;
import com.agil.repo.TeamRepository;

import antlr.Token;

@SpringBootApplication
public class TournamentPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentPlannerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(TeamRepository repository) {
		return (args) -> repository.save(new Team("Team Solo Mid", "Blau", null));		
	}


}
