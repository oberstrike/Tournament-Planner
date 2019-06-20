package com.agil;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.agil.model.Game;
import com.agil.model.Member;
import com.agil.model.Player;
import com.agil.model.Team;
import com.agil.model.game.LeagueOfLegends;
import com.agil.model.game.Volleyball;
import com.agil.repo.GameRepository;
import com.agil.repo.MemberRepository;
import com.agil.repo.PlayerRepository;
import com.agil.repo.TeamRepository;
import com.agil.utility.GameStatus;
import com.agil.utility.GameType;
import com.agil.utility.MemberRole;

@SpringBootApplication
public class TournamentPlannerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TournamentPlannerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TournamentPlannerApplication.class);
	}

	@Autowired
	public BCryptPasswordEncoder encoder;

	@Bean
	CommandLineRunner init(TeamRepository teamRepository, MemberRepository memberRepository,
			GameRepository gameRepository, PlayerRepository playerRepository) {
		return (args) -> {

			// Init members
			Member member = new Member("test_admin", encoder.encode("admin123"), "markus.juergens@gmx.de");
			Member member2 = new Member("test_user", encoder.encode("user123"), "oberstrike@gmx.de");

			member.addRoles(MemberRole.ROLE_ADMIN);

			// Save members
			memberRepository.save(member);
			memberRepository.save(member2);

			// Init players
			Player player = new Player("oberstrike");

			Player player2 = new Player("eikorn");

			// Save players
			playerRepository.save(player);
			playerRepository.save(player2);
			member.setPlayer(player);
			member2.setPlayer(player2);

			// Init teams
			Team team = new Team("Team Solo Mid", "#0015FF");
			Team team2 = new Team("Team Liquid", "#FF1E01");
			team.addPlayer(player);
			team.addPlayer(player2);
			team2.addPlayer(player2);
			team.setCreator(member);
			team2.setCreator(member2);

			// Save teams
			teamRepository.save(team);
			teamRepository.save(team2);

			// Init game
			Game game = new Volleyball(3, 25, true, "Spiel 1", GameStatus.PENDING, new Date(System.currentTimeMillis()),
					team.getName(), team2.getName(), member2);
			game.setTeamA(team);
			game.setTeamB(team2);
			game.setVideo("https://www.youtube.com/watch?v=KyWMlJ987jg");
			game.setCreator(member);

			Game game2 = new Volleyball(2, 5, true, "Spiel 2", GameStatus.PENDING, new Date(System.currentTimeMillis()),
					team.getName(), team2.getName(), member2);
			game2.setTeamA(team);
			game2.setTeamB(team2);
			game2.setCreator(member);

			Game game3 = new LeagueOfLegends(GameStatus.PENDING, GameType.LEAGUEOFLEGENDS,
					new Date(System.currentTimeMillis() + 5000));
			game3.setTeamA(team);
			game3.setTeamB(team2);
			game3.setCreator(member2);
			game3.setName("Game3");

			// Save All
			gameRepository.save(game);
			gameRepository.save(game2);
			gameRepository.save(game3);
			teamRepository.save(team);
			teamRepository.save(team2);
			memberRepository.save(member);
			memberRepository.save(member2);

		};
	}

}
