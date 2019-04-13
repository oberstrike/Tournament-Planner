package com.agil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Team;
import com.agil.repo.GameRepository;
import com.agil.repo.MemberRepository;
import com.agil.repo.TeamRepository;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;

@SpringBootApplication
public class TournamentPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentPlannerApplication.class, args);
	}
	
	@Autowired
	public BCryptPasswordEncoder encoder;
	
	@Bean
	CommandLineRunner init(TeamRepository teamRepository, MemberRepository memberRepository, GameRepository gameRepository) {
		return (args) -> {
			Member member = new Member("oberstrike", encoder.encode("mewtu123"), "markus.juergens@gmx.de");		
			Member member2 = new Member("Bjergsen", encoder.encode("mewtu123"), "markus.juergens@gmx.de");		
			memberRepository.save(member);
			memberRepository.save(member2);
			
			Team team = new Team("Team Solo Mid", "Blue", new HashSet<>(Arrays.asList(member, member2)));
			Team team2 = new Team("Team Liquid", "Red", new HashSet<>(Arrays.asList(member)));
			teamRepository.save(team);
			teamRepository.save(team2);
			
			
			Game game = new Game("Spiel 1",GameStatus.PENDING, GameType.VOLLEYBALL, new Date(System.currentTimeMillis()), new HashSet<>(), member);
			game.setTeams(new HashSet<>(Arrays.asList(team, team2)));
			game.setCreator(member);
			gameRepository.save(game);
			

			Game game2 = new Game("Spiel 1",GameStatus.PENDING, GameType.VOLLEYBALL, new Date(System.currentTimeMillis()), new HashSet<>(), member);
			game2.setTeams(new HashSet<>(Arrays.asList(team, team2)));
			game2.setCreator(member);
			gameRepository.save(game2);
			
			memberRepository.save(member);
			memberRepository.save(member2);
			
		};
	}


}
