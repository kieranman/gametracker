//package com.example.service;
//
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.model.Game;
//import com.example.repository.GameRepository;
//
//@Service
//public class GameServiceImpl implements IService<Game> {
//
//	
//	@Autowired
//	private GameRepository gameRepository;
//	
//	private Long gameId =100L;
//	private Map<Long,Game> gameMap = new HashMap<Long, Game>();
//	{
//		Game game = new Game();
//		game.setId(gameId);
//		game.setTitle("title");
//		game.setSynopsis("synopsis");
//		game.setPhotoURL("photoURL");
//		game.setGenre("genre");
//		gameMap.put(game.getId(),game);
//		
//	}
	
//	@Override
//	public Collection<Game> findAll() {
//		return gameRepository.findAll();
//	}
//
//	@Override
//	public Game findById(Long id) {
//		return gameRepository.findById(id).get();
//	}
//
//	@Override
//	public Game saveOrUpdate(Game game) {
//		return gameRepository.saveAndFlush(game);
//	}
//
//
//	@Override
//	public String deleteById(Long id) {
//		gameRepository.deleteById(id);
//		return null;
//	}
//
//}
