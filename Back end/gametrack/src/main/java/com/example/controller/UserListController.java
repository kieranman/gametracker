package com.example.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
import com.example.model.Game;
import com.example.model.User;
import com.example.model.UserList;
import com.example.payload.request.UserListRequest;
import com.example.payload.response.MessageResponse;
import com.example.repository.GameRepository;
import com.example.repository.UserListRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
//import com.example.service.RecommendationService;
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
	JwtUtils jwtUtils;
	//save product
	
	@PostMapping("/add/{token}")
	public ResponseEntity<MessageResponse> createUserList(@RequestBody UserListRequest userListRequest, @PathVariable("token") String token) {
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		Long userId = user.getId();
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

	@GetMapping("/{username}")
	public List<UserList> getUserList(@PathVariable("username") String username){
			User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
			Long userId = user.getId();
			List<UserList> body = userListRepository.findAllByUserIdOrderByRatingDesc(userId);
			return body;
	}	
	
	@GetMapping("/recommendations/{userId}")
	public List<Game> getRecommendations(@PathVariable("userId") Long userId){

		List<UserList> userCompleted = userListRepository.findByStatusAndUserId("Completed",userId);
		List<UserList> allCompleted = userListRepository.findByStatus("Completed");
		
		Set<Long>otherUserCompleted = getAllSimmilarUsers(userId,userCompleted,allCompleted);
		
		List<String> combinedGameListArray = makeCombinedGameList( userCompleted, otherUserCompleted);
		
		Double[] returnVector =  makeVector(userCompleted,otherUserCompleted,userId,combinedGameListArray);
		for (int i = 0; i < combinedGameListArray.size()-1; i++)    
		    for (int j = 0; j < combinedGameListArray.size()-i-1; j++)
		        if (returnVector[j] > returnVector[j+1]) {
		    	    Double temp = returnVector[j];
		    	    returnVector[j]= returnVector[j+1];
		    	    returnVector[j+1] = temp;
		    	    String temp2 = combinedGameListArray.get(j);
		    	    combinedGameListArray.set(j,combinedGameListArray.get(j+1));
		    	    combinedGameListArray.set(j+1,temp2);
		        }
		
		List<String> top5Games = new ArrayList<>();
		int counter =0;
		for(int i =0;counter!=5;i++) {
			try {
				if (userListRepository.existsByUserIdAndGameTitle(userId,combinedGameListArray.get(combinedGameListArray.size()-i-1))==false)
				{
					top5Games.add(combinedGameListArray.get(combinedGameListArray.size()-i-1));
					counter+=1;
				}
			}
			catch(Exception e) {
				counter=5;
			}
		}
		List<Game> getRecommendations = gameRepository.findByTitleIn(top5Games);		
		return getRecommendations;
	}	

	private List<String> makeCombinedGameList(List<UserList> userCompleted,
			Set<Long> otherUserCompleted) {
		List<Long> getIndexFromSet = new ArrayList<>(otherUserCompleted);
		Set<String> combinedGameList = new HashSet();
		for (int i=0;i<getIndexFromSet.size();i++) {
			List<UserList> otherUserCompletedList = userListRepository.findByStatusAndUserId
					("Completed",getIndexFromSet.get(i));
			
			for (UserList a:otherUserCompletedList) {
				combinedGameList.add(a.getGame().getTitle());
			}
		}
		List<String> combinedGameListArray = new ArrayList<>(combinedGameList);
		return combinedGameListArray;
	}

	private Double[] makeVector(List<UserList> userCompleted,Set<Long> otherUserCompleted,Long userId,List<String> combinedGameListArray) {
		Double weightedRating [] = new Double[combinedGameListArray.size()];;
		Double sumOfCosine = 0.0;
		List<Long> getIndexFromSet = new ArrayList<>(otherUserCompleted);
		for (int i=0;i<getIndexFromSet.size();i++) {
			List<Integer> UserRatings = new ArrayList<Integer>();
			List<Integer> otherUserRating =  new ArrayList<Integer>();
			List<UserList> otherUserCompletedList = userListRepository.findByStatusAndUserId("Completed",getIndexFromSet.get(i));
			for (int j =0;j<combinedGameListArray.size();j++) {
				for (UserList a: userCompleted) {
					if(a.getGame().getTitle()==combinedGameListArray.get(j)) {
						UserRatings.add(a.getRating());
					}
				}
				if (userListRepository.existsByUserIdAndGameTitle(userId,combinedGameListArray.get(j))== false){
					UserRatings.add(0);
				}
				for (UserList a: otherUserCompletedList) {
					if(a.getGame().getTitle()==combinedGameListArray.get(j)) {
						otherUserRating.add(a.getRating());
					}
				}
				if (userListRepository.existsByUserIdAndGameTitle(getIndexFromSet.get(i),combinedGameListArray.get(j))== false){
					otherUserRating.add(0);
				}
			}
			Double cosineValue = cosineSimmilarity(UserRatings,otherUserRating);
				if (cosineValue<0.99) { // 0.99 is it identifying exact match which is the target user
					for (int j =0;j<combinedGameListArray.size();j++) {
						Double otherUserRatingDouble = new Double(otherUserRating.get(j));
						if (weightedRating[j]==null) {
							weightedRating[j]=cosineValue* otherUserRatingDouble;
						}
						else {weightedRating[j]+=cosineValue* otherUserRatingDouble;}
					}
					sumOfCosine+=cosineValue;
				}
		}
		for (int j =0;j<combinedGameListArray.size();j++) {
			weightedRating[j]=weightedRating[j]/sumOfCosine;
		}
		return weightedRating;
	}
	
	
	private Double cosineSimmilarity(List<Integer> userRatings,List<Integer> otherUserRating) {
		double userRatingsSum =0;
		double userRatingSize=0;
		double otherUserRatingSum=0;
		double otherUserRatingSize=0;
		for (int i = 0; i < userRatings.size(); i++) {
			userRatingsSum+=userRatings.get(i);
			otherUserRatingSum+=otherUserRating.get(i);
			if(userRatings.get(i)!=0) {
				userRatingSize+=1;
			}
			if(otherUserRating.get(i)!=0) {
				otherUserRatingSize+=1;
			}
		}
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < userRatings.size(); i++) {
	        dotProduct += (userRatings.get(i)-(userRatingsSum/userRatingSize)) *
	        		(otherUserRating.get(i)-(otherUserRatingSum/otherUserRatingSize));
	        normA += Math.pow(userRatings.get(i), 2);
	        normB += Math.pow(otherUserRating.get(i), 2);
	    }   
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	
	
	private Set<Long> getAllSimmilarUsers(Long userId,List<UserList> userCompleted ,
		List<UserList> allCompleted) {
		
		Set<Long> otherUserCompleted = new HashSet<>();
		for(UserList a : allCompleted) {
				for(UserList b: userCompleted) {
					if(a.getGame().getTitle()==b.getGame().getTitle()) { 
						otherUserCompleted.add(a.getUser().getId());
					}
				}
		}
		return otherUserCompleted;
	}
}
