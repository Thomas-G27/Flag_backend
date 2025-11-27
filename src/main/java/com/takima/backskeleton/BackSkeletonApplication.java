package com.takima.backskeleton;

import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.services.CountryService;
import com.takima.backskeleton.services.UtilisateurService;
import com.takima.backskeleton.DAO.CountryDao;
import com.takima.backskeleton.DAO.UtilisateurDao;
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
	CommandLineRunner initDatabase(CountryService countryService, UtilisateurService utilisateurService,  CountryDao countryDao, UtilisateurDao utilisateurDao) {
		return args -> {
			if (countryDao.count() == 0) {
				countryService.fillDatabaseFromAPI();
			} else {
				System.out.println("DB already has country data — skipping country init.");
			}
			if (utilisateurDao.count() == 0) {
				utilisateurService.fillUserAndGameInit();
			} else {
				System.out.println("DB already has user data — skipping user init.");
			}
			System.out.println("Country count: " + countryDao.count() + ", utilisateur count: " + utilisateurDao.count());
		};
	}
}
