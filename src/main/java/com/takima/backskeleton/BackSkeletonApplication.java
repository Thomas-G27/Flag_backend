package com.takima.backskeleton;

import com.takima.backskeleton.services.CountryService;
import com.takima.backskeleton.services.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackSkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackSkeletonApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CountryService countryService, UtilisateurService utilisateurService) {
		return args -> {
			countryService.fillDatabaseFromAPI();
			utilisateurService.fillUserAndGameInit();
		};
	}
}
