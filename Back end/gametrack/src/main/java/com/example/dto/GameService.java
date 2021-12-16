package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Game;
import com.example.repository.GameRepository;

@Service
public class GameService {
    
	@Autowired
    private GameRepository gameRepository;

    public List<GameDto> listGames() {
        List<Game> games = gameRepository.findAll();
        List<GameDto> gameDtos = new ArrayList<>();
        for(Game game : games) {
        	GameDto gameDto = getDtoFromGame(game);
            gameDtos.add(gameDto);
        }
        return gameDtos;
    }

    public static GameDto getDtoFromGame(Game game) {
    	GameDto gameDto = new GameDto(game);
        return gameDto;
    }

   

}