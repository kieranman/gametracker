package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.model.Game;

import com.example.repository.GameRepository;
//import com.example.repository.UserListRepository;


@SpringBootApplication
@EnableAutoConfiguration
public class GametrackApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GametrackApplication.class, args);
	}

	
	@Autowired
	private GameRepository gameRepository;
	
//	@Autowired
//	private UserListRepository userListRepository;
	
	@Override
	public void run(String... args) throws Exception {
//		this.userListRepository.save(new UserList("good game",10,"completed"));
	}
	
}
