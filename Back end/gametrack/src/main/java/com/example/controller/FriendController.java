package com.example.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Friends;
import com.example.model.User;
import com.example.payload.request.FriendRequest;
import com.example.payload.response.MessageResponse;
import com.example.repository.FriendsRepository;
import com.example.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FriendController {
	  @Autowired
	  FriendsRepository friendsRepository;
	  
	  @Autowired
	  UserRepository userRepository;
	  
	  
	  @PostMapping("/addfriend")
	  public ResponseEntity addFriend(@RequestBody FriendRequest friendRequest) {
		  	
		  
		  User user1 =userRepository.getById(friendRequest.getFirstUserId());
		  User user2 =userRepository.getById(friendRequest.getSecondUserId());

		  
		  String response = "";
		  Friends friend = new Friends();
	        if( !(friendsRepository.existsByFirstUserAndSecondUser(user1,user2)) && (user1.getId()!=user2.getId()) ){
	        	friend.setCreatedDate(new Date());
	        	friend.setFirstUser(user1);
	        	friend.setSecondUser(user2);
	            friendsRepository.save(friend);
	            response = "Added Friend";
	        }
	        else {
	        	response = "Failed to add friend";
	        }
	        return ResponseEntity.ok(new MessageResponse(response));
	  }
	  
	  
	  
	  
	  	@GetMapping("/getfriends/{id}")
		public Set<Long> getFriends(@PathVariable Long id) {

	  		Set<Long> friendsList = new HashSet<>();
	        List<Friends> firstUserFriends = friendsRepository.findByfirstUserId(id);
	        for (Friends friend :firstUserFriends ) {

	        	friendsList.add(friend.getSecondUser().getId());
	        }

	        List<Friends> secondUserFriends = friendsRepository.findBysecondUserId(id);
	        for (Friends friend :secondUserFriends ) {
	        	friendsList.add(friend.getFirstUser().getId());
	        }


	        return friendsList;
	        
	        
	        
	        
		  
	  }
	  
}
