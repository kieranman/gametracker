package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exceptions.ResourceNotFoundException;
import com.example.dto.GameDto;
import com.example.dto.GameService;
import com.example.dto.UserListDto;
import com.example.dto.UserListService;
import com.example.model.Game;
import com.example.model.User;
import com.example.model.UserList;
import com.example.payload.request.UserListRequest;
import com.example.payload.response.MessageResponse;
import com.example.repository.GameRepository;
import com.example.repository.UserListRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/userlist")
public class UserListController {
	
	@Autowired
	UserListRepository userListRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	GameService gameService;
	
	
	@Autowired
	JwtUtils jwtUtils;
	
	//save product
	@PostMapping("/add/{token}")
	public ResponseEntity<MessageResponse> createUserList(@RequestBody UserListRequest userListRequest, @PathVariable("token") String token) {
		
		
		//gets the user from the token
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		Long userId = user.getId();
		
		// gets the game from the game id
		Game game = gameRepository.findById(userListRequest.getGameId())
				.orElseThrow(() -> new ResourceNotFoundException("Game does not exist with id:"+userListRequest.getGameId()));
		

		if (userListRepository.existsByUserIdAndGameId(userId,userListRequest.getGameId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error you've already added this game before!"));
		}
		
		UserList userList = new UserList(user,game, userListRequest.getReview(), userListRequest.getStatus(), userListRequest.getRating());	

		
		
		userListRepository.save(userList);
		return ResponseEntity.ok(new MessageResponse("Added Successfully!"));
	}
	
	
	
	@GetMapping("/{token}")
	public List<UserList> getUserList(@PathVariable("token") String token){
			String username = jwtUtils.getUserNameFromJwtToken(token);
			
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			Long userId = user.getId();
			List<UserList> body = userListRepository.findAllByUserIdOrderByRatingDesc(userId);
			return body;
	
			
			
			
			
//			List<GameDto> games = new ArrayList<GameDto>();
//			for (UserList userList : body) {
//				
//		       
//				games.add(GameService.getDtoFromGame(userList.getGame()));
//			}
//			
//			return new ResponseEntity<List<GameDto>>(games,HttpStatus.OK);
	}	
	
}
