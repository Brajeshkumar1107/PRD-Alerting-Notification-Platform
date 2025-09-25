package com.brajesh.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.brajesh.repository.TeamRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.model.Team;
import com.brajesh.model.User;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SeedConfig {

	@Bean
	CommandLineRunner seedData(TeamRepository teamRepository, UserRepository userRepository) {
		return args -> {
			if (teamRepository.count() == 0) {
				Team eng = new Team();
				eng.setName("Engineering");
				Team mkt = new Team();
				mkt.setName("Marketing");
				teamRepository.saveAll(Arrays.asList(eng, mkt));
			}

			if (userRepository.count() == 0) {
				List<Team> teams = teamRepository.findAll();
				Team eng = teams.stream().filter(t -> "Engineering".equals(t.getName())).findFirst().orElse(teams.get(0));
				Team mkt = teams.stream().filter(t -> "Marketing".equals(t.getName())).findFirst().orElse(teams.get(0));

				User u1 = new User();
				u1.setName("Alice");
				u1.setEmail("alice@example.com");
				u1.setTeam(eng);

				User u2 = new User();
				u2.setName("Bob");
				u2.setEmail("bob@example.com");
				u2.setTeam(eng);

				User u3 = new User();
				u3.setName("Carol");
				u3.setEmail("carol@example.com");
				u3.setTeam(mkt);

				userRepository.saveAll(Arrays.asList(u1, u2, u3));
			}
		};
	}
}


