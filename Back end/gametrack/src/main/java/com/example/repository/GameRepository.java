package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Game;
import com.example.model.User;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
	Game findByTitle(String title);

	List<Game> findAllById(Long id);
	
	List<Game> findAllByTitleContaining(String title);
	
	List<Game> findAllByGenre1OrGenre2OrGenre3OrGenre4(String genre1,String genre2, String genre3, String genre4);

	List<Game> findByTitleIn(List<String> combinedGameListArray);
	

	
}
