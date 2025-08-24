package com.br.alura.Challenge_ForumHub;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
public class ChallengeForumHubApplication {

	public static void main(String[] args) {
//        Dotenv dotenv = Dotenv.load();
//        dotenv.entries().forEach(entry ->
//                System.setProperty(entry.getKey(), entry.getValue())
//        );

        SpringApplication.run(ChallengeForumHubApplication.class, args);
	}
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
    }

}
