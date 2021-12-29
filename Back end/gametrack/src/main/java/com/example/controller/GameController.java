package com.example.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exceptions.ResourceNotFoundException;
import com.example.model.ERole;
import com.example.model.Game;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserList;
import com.example.model.UserList;
import com.example.payload.request.GenreRequest;
import com.example.payload.request.SignupRequest;
import com.example.payload.request.TitleRequest;
import com.example.payload.request.UserListRequest;
import com.example.payload.response.GameResponse;
import com.example.payload.response.JwtResponse;
import com.example.payload.response.MessageResponse;
import com.example.repository.GameRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserListRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GameController {

		
		@Autowired
		private GameRepository gameRepository;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private UserListRepository userListRepository;


		
		
		
		//Search by game title
		@GetMapping("/games/title/{title}")
		public List<Game> getGameByTitle(@PathVariable String title){
			List<Game> matchedTitles = gameRepository.findAllByTitleContaining(title);
			return matchedTitles;
		}
		//Search by game title
		@GetMapping("/games/genre")
		public List<Game> getGameByGenre(@RequestBody GenreRequest genreRequest){
			List<Game> matchedGenres = gameRepository.findAllByGenre1OrGenre2OrGenre3OrGenre4(genreRequest.getGenre(), genreRequest.getGenre(),
					genreRequest.getGenre(), genreRequest.getGenre());

			return matchedGenres;
		}
		
		
		
		
		//get games  + gets the average score of the game
		@GetMapping("/games")
		public List<Game> getGames(){
			getGameRating();
			return this.gameRepository.findAll();
		}
		
		
		
		
		
		
		// sets the average rating
		public void getGameRating(){
			List<Game> allGames = gameRepository.findAll();
			for (Game a : allGames) {
				a.setScore(getAverageScore(a.getId()));
			}
			gameRepository.saveAll(allGames);
	
		}
		
		private double getAverageScore(Long gameId) {
			
			
			ArrayList<String> avgRating = new ArrayList<String>(); // Create an ArrayList object
			List<UserList> allRatings = userListRepository.findAllByGameId(gameId);
			
			if (allRatings.isEmpty()) {
				return 0;
			}
			
			double sumRatings = 0;
			for(UserList a : allRatings) {
					if (a.getRating()!=null){						
						sumRatings+= a.getRating();
					}
			}

			double averageRating = sumRatings/allRatings.size();
			double roundOff = Math.round(averageRating * 100.0) / 100.0;
			return roundOff ;
		}
		

		
		// create game item
		@PostMapping("/games")
		public Game createGame(@RequestBody Game game) {
			return gameRepository.save(game);
		}

		
		//get game by id
		@GetMapping("/games/{id}")
		public ResponseEntity<Game> getGameById(@PathVariable Long id){
			Game game = gameRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Game does not exist with id:"+id));
			return ResponseEntity.ok(game);
		
		}
		
		//update game details
		@PutMapping("/games/{id}")
		public ResponseEntity<Game> updateGame(@PathVariable Long id,@RequestBody Game gameDetails){
			Game game = gameRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Game does not exist with id:"+id));
			game.setTitle(gameDetails.getTitle());
			game.setGenre1(gameDetails.getGenre1());
			game.setGenre2(gameDetails.getGenre2());
			game.setGenre3(gameDetails.getGenre3());
			game.setGenre4(gameDetails.getGenre4());
			game.setSynopsis(gameDetails.getSynopsis());
			game.setPhotoURL(gameDetails.getPhotoURL());
			
			Game updateGame = gameRepository.save(game);
			return ResponseEntity.ok(updateGame);
		
		}
		
		// delete game  item
		@DeleteMapping("/games/{id}")
		public ResponseEntity<Map<String, Boolean>> deleteGame(@PathVariable Long id){
			Game game = gameRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Game does not exist with id:"+id)); 
			gameRepository.delete(game);
			Map<String,Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
			
		}
		}

