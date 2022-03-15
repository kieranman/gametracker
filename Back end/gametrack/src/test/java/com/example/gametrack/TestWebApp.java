package com.example.gametrack;

import com.example.GametrackApplication;
import com.example.dto.UserDto;
import com.example.model.Game;
import com.example.model.User;
import com.example.model.UserList;
import com.example.payload.request.FriendRequest;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.SignupRequest;
import com.example.payload.request.UserListRequest;
import com.example.repository.GameRepository;
import com.example.repository.UserListRepository;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWebApp extends GametrackApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	UserListRepository userListRepository;
	
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	
	@Test
	public void testUserslistWithGamesPresent() throws Exception {
		
		// Input data
		Long userId = (long) 1;
		String username = "test";
		List<UserList> userlist = userListRepository.findAllByUserId(userId);
		ArrayList<String> titleList = new ArrayList<>();
		ArrayList<String> statusList = new ArrayList<>();
		ArrayList<String> reviewList = new ArrayList<>();
		ArrayList<Integer> ratingList = new ArrayList<>();
		
		try {
		for (UserList game : userlist) {
			titleList.add(game.getGame().getTitle());
			statusList.add(game.getStatus());
			reviewList.add(game.getReview());
			ratingList.add(game.getRating());
		}
		}
		catch(Exception e) {
			
		}
		mockMvc.perform(get("/userlist/"+ username)).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$[0].game.title").value(titleList.get(0)))
			.andExpect(jsonPath("$[0].status").value(statusList.get(0)))
			.andExpect(jsonPath("$[0].review").value(reviewList.get(0)))
			.andExpect(jsonPath("$[0].rating").value(ratingList.get(0)));
	}
	
	@Test
	public void testUserlistWithGamesAbsent() throws Exception {
		// Input data
		Long userId = (long) 4;
		String username = "test4";
		List<UserList> userlist = userListRepository.findAllByUserId(userId);
		ArrayList<String> titleList = new ArrayList<>();
		ArrayList<String> statusList = new ArrayList<>();
		ArrayList<String> reviewList = new ArrayList<>();
		ArrayList<Integer> ratingList = new ArrayList<>();
		
		try {
		for (UserList game : userlist) {
			titleList.add(game.getGame().getTitle());
			statusList.add(game.getStatus());
			reviewList.add(game.getReview());
			ratingList.add(game.getRating());
		}
		}
		catch(Exception e) {
			
		}
		mockMvc.perform(get("/userlist/"+ username)).andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.[0].game.title").doesNotExist());
	}
	
	
	

	@Test
	public void testGetGames() throws Exception {
		
		// Input data
		String accessToken = "bwdwus26w9eva0h0m3j95m8a5s6v30";
		
		mockMvc.perform(post("/gameapi/games/"+accessToken)).andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[99].name").exists())
            .andExpect(status().is2xxSuccessful());

	}
	
	@Test
	public void testGetGameDetails() throws Exception {
		
		// Input data
		String accessToken = "bwdwus26w9eva0h0m3j95m8a5s6v30";
		int gameId = 1020;
		
		mockMvc.perform(post("/gameapi/games/"+accessToken+"/"+gameId)).andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].cover").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].genres").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].summary").exists());

	}
	
	
	@Test
	public void testGetGamesByGenre() throws Exception {
		
		// Input data
		String accessToken = "bwdwus26w9eva0h0m3j95m8a5s6v30";
		String genreId = "15";
		
		mockMvc.perform(post("/gameapi/gamesByGenre/"+accessToken+"/"+genreId)).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].genres.id").value(genreId));

	}
	
	@Test
	public void testGetGamesByTitle() throws Exception {
		
		// Input data
		String accessToken = "bwdwus26w9eva0h0m3j95m8a5s6v30";
		String title = "uncharted";
		
		mockMvc.perform(post("/gameapi/gamesByTitle/"+accessToken+"/"+title)).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value("Uncharted: Golden Abyss"));

	}
	
	
	

	@Test
	public void testAddGameToUserlistWhenGameAlreadyAdded() throws Exception { 
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0Ijo"
				+ "xNjQ2OTY2MjY0LCJleHAiOjE2NDY5NzYyNjR9.96KP8F"
				+ "SY-DbxzO-ZirUYLEjyf0rTlPIsz3yU2tpL5-cs9zbNR"
				+ "MFGKZ2r7cVhdxsrthW4zzhAUvS7RmGbHvoGvw";
		
	    String url = "/userlist/add/"+token;
	    UserListRequest userListRequest = new UserListRequest();
	    userListRequest.setGameId((long) 1020);
	    userListRequest.setStatus("Completed");
	    userListRequest.setReview("Good Game");
	    userListRequest.setRating(8);
	    
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(userListRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.message").value("Error you've already added this game before!"));

	}
	
	@Test
	public void testAddGameToUserlistWhenGameNotAdded() throws Exception { 
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF"
				+ "0IjoxNjQ2OTY2MjY0LCJleHAiOjE2NDY5NzYyNjR9.96KP8FSY-Dbx"
				+ "zO-ZirUYLEjyf0rTlPIsz3yU2tpL5-cs9zbNRMFGKZ2r7cVhdxsrthW4zzhAUvS7RmGbHvoGvw";
		
	    String url = "/userlist/add/"+token;
	    UserListRequest userListRequest = new UserListRequest();
	    userListRequest.setGameId((long) 72);
	    userListRequest.setStatus("Completed");
	    userListRequest.setReview("Good Game");
	    userListRequest.setRating(8);
	    

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(userListRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.message").value("Added Successfully!"));

	}
	
	@Test
	public void testGetRecommendationsWhenRecommendationsAvaliable() throws Exception {
		
		String userId = "2";
		mockMvc.perform(get("/userlist/recommendations/"+userId))
			.andExpect(jsonPath("$.[0].title").exists());

	}
	@Test
	public void testGetRecommendationsWhenRecommendationsNotAvaliable() throws Exception {
		
		String userId = "1";
		
		mockMvc.perform(get("/userlist/recommendations/"+userId))
			.andExpect(jsonPath("$.[0].title").doesNotExist());
	}
	
	
	
	@Test
	public void testAddFriendWhenAlreadyFriends() throws Exception { 

	    String url = "/addFriend";
	    
	    FriendRequest friendRequest = new FriendRequest();
	    friendRequest.setFirstUserId((long) 1);
	    friendRequest.setSecondUserId((long) 3);
	    
	    //... more
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(friendRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.message").value("Failed to add friend"));

	}
	@Test
	public void testAddFriendWhenNotFriends() throws Exception { 

	    String url = "/addFriend";
	    
	    FriendRequest friendRequest = new FriendRequest();
	    friendRequest.setFirstUserId((long) 3);
	    friendRequest.setSecondUserId((long) 4);
	    
	    //... more
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(friendRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.message").value("Request Sent"));

	}
	
	@Test
	public void testGetFriendsWhenUserHasFriends() throws Exception {
		
		// Input data
		String userId = "1";
		mockMvc.perform(get("/getFriends/"+userId))
			.andExpect(jsonPath("$.[0]").exists());

	}
	
	@Test
	public void testGetFriendsWhenUserDoesntHaveFriends() throws Exception {
		
		// Input data
		String userId = "5";
		mockMvc.perform(get("/getFriends/"+userId))
			.andExpect(jsonPath("$.[0]").doesNotExist());

	}
	
	@Test
	public void testGetRequestsWhenUserHasRequests() throws Exception {
		
		// Input data
		String userId = "2";
		mockMvc.perform(get("/getRequests/"+userId))
			.andExpect(jsonPath("$.[0]").exists());

	}
	
	@Test
	public void testGetRequestsWhenUserDoesntHaveRequests() throws Exception {
		
		// Input data
		String userId = "1";
		mockMvc.perform(get("/getRequests/"+userId))
			.andExpect(jsonPath("$.[0]").doesNotExist());
	}

	@Test
	public void testAcceptRequest() throws Exception {
		
		// Input data
		String id = "10";
		mockMvc.perform(put("/acceptRequest/"+id))
			.andExpect(content().string("Request has been accepted"));
	}
	
	@Test
	public void testDeclineRequest() throws Exception {
		
		// Input data
		String id = "9";
		mockMvc.perform(delete("/declineRequest/"+id))
			.andExpect(content().string("Request has been declined"));
	}
	
	@Test
	public void testGetUsers() throws Exception {
		
		// Input data
		mockMvc.perform(get("/api/auth/users"))
			.andExpect(jsonPath("$.[0].username").exists());
	}
	
	@Test
	public void testSignIn() throws Exception { 

	    String url = "/api/auth/signin";
	    
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setUsername("test");
	    loginRequest.setPassword("test");
	    
	    //... more
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(loginRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.accessToken").exists());

	}
	
	@Test
	public void testSignUp() throws Exception { 

	    String url = "/api/auth/signup";
	    
	    SignupRequest signUpRequest = new SignupRequest();
	    signUpRequest.setUsername("test11");
	    signUpRequest.setPassword("test11");
	    signUpRequest.setEmail("test11@gmail.com");
	    
	    //... more
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(signUpRequest );

	    mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson))
	    	.andExpect(jsonPath("$.message").value("User registered successfully!"));

	}
//	
//	@Test
//	public void testPublicChat() throws Exception {
//	    Session session = container.connectToServer(new TestEndpoint(),
//	            URI.create("ws://127.0.0.1:8080/test"));
//	}
//	
	
}