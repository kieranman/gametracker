package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Game;
import com.example.model.User;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
	Game findByTitle(String title);
}