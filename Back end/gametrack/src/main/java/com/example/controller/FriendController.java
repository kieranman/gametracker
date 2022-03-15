package com.example.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
	  
	  
	  @PostMapping("/addFriend")
	  public ResponseEntity addFriend(@RequestBody FriendRequest friendRequest) {
		  User user1 =userRepository.getById(friendRequest.getFirstUserId());
		  User user2 =userRepository.getById(friendRequest.getSecondUserId());
		  String response = "";
		  Friends friend = new Friends();
	        if( !(friendsRepository.existsByFirstUserAndSecondUser(user1,user2)) && (user1.getId()!=user2.getId()) ){
	        	friend.setCreatedDate(new Date());
	        	friend.setFirstUser(user1);
	        	friend.setSecondUser(user2);
	        	friend.setRequestStatus("pending");
	            friendsRepository.save(friend);
	            response = "Request Sent";
	        }
	        else {
	        	response = "Failed to add friend";
	        }
	        return ResponseEntity.ok(new MessageResponse(response));
	  }
	  	@GetMapping("/getFriends/{id}")
		public Set<String> getFriends(@PathVariable Long id) {
	  		Set<String> friendsList = new HashSet<>();
	        List<Friends> firstUserFriends = friendsRepository.findByfirstUserIdAndRequestStatus(id,"accepted");
	        for (Friends friend :firstUserFriends ) {
	        	friendsList.add(friend.getSecondUser().getUsername());
	        }
	        List<Friends> secondUserFriends = friendsRepository.findBysecondUserIdAndRequestStatus(id,"accepted");
	        for (Friends friend :secondUserFriends ) {
	        	friendsList.add(friend.getFirstUser().getUsername());
	        }
	        return friendsList;

	  } 
	  	
	  	@GetMapping("/getRequests/{id}")
		public List<Friends> getRequests(@PathVariable Long id) {
	        List<Friends> friendRequests = friendsRepository.findBysecondUserIdAndRequestStatus(id,"pending");
	        return friendRequests ;

	  } 
	  	
	  	@PutMapping("/acceptRequest/{id}")
	  	public ResponseEntity<String> acceptRequest(@PathVariable Long id){
			Friends friend = friendsRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Request does not exist with id:"+id));
			friend.setRequestStatus("accepted");
			Friends updateFriend = friendsRepository.save(friend);
			return ResponseEntity.ok("Request has been accepted");
	  		
	  	}

	  	@DeleteMapping("/declineRequest/{id}")
	  	public ResponseEntity<String> deleteRequest(@PathVariable Long id){	
	  		friendsRepository.deleteById(id);
	  		return ResponseEntity.ok("Request has been declined");
	  			
	  	}
	  	

}
