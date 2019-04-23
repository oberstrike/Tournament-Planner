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
import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.model.game.Volleyball;
import com.agil.repo.GameRepository;
import com.agil.repo.MemberRepository;
import com.agil.repo.PlayerRepository;
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
	CommandLineRunner init(TeamRepository teamRepository,
			MemberRepository memberRepository,
			GameRepository gameRepository,
			PlayerRepository playerRepository) {
		return (args) -> {
			//Init members
			Member member = new Member("oberstrike", encoder.encode("mewtu123"), "markus.juergens@gmx.de");		
			Member member2 = new Member("eikorn", encoder.encode("eic123abc"), "markus.juergens@gmx.de");
			//Save members
			memberRepository.save(member);
			memberRepository.save(member2);
			
			//Init players
			Player player = new Player("oberstrike");
			Player player2 = new Player("eikorn");

			//Save players
			playerRepository.save(player);
			playerRepository.save(player2);
			member.setPlayer(player);
			member2.setPlayer(player2);
			
			
			//Init teams
			Team team = new Team("Team Solo Mid", "Blue");
			Team team2 = new Team("Team Liquid", "Red");
			team.addPlayer(player);
			team.addPlayer(player2);
			team2.addPlayer(player2);
			team.setCreator(member);
			team2.setCreator(member2);
			
			//Save teams
			teamRepository.save(team);
			teamRepository.save(team2);
			
			//Init game
			Game game = new Volleyball(3, 25, true, "Spiel 1",GameStatus.PENDING, GameType.VOLLEYBALL, new Date(System.currentTimeMillis()), team, team2, member2);
			game.setCreator(member);
			
			//Save All
			gameRepository.save(game);
			teamRepository.save(team);
			teamRepository.save(team2);
			memberRepository.save(member);
			memberRepository.save(member2);
		};
	}


}
