package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class APIController {

	@PostMapping("gameapi/access-token")
	public ResponseEntity<String> getAccessToken(){
		try {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Header", "header1");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/token")
		        .queryParam("client_id", "sxmfe1vyjh3j8p9bp5rcc9lzjz6o8x")
		        .queryParam("client_secret", "fyw3xxw5iop9deqhcd2u2a61sgtra5")
				.queryParam("grant_type", "client_credentials");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
		        builder.toUriString(), 
		        HttpMethod.POST, 
		        entity, 
		        String.class);
		
		
		return response;
		}
		catch (Exception e) {
			return new ResponseEntity<>(
				"Cannot get access-token", 
			     HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping("/gameapi/games/{accessToken}")
	public ResponseEntity<String> getAllGames(@PathVariable String accessToken){


		String url = "https://api.igdb.com/v4/games/?fields="
				+ "name,"
				+ "genres.name"
				+ ",platforms.name,"
				+ "rating,summary,cover.url"
				+ "&limit=100"
				+ "&filter[rating][gt]=0"
				+ "&order=total_rating_count:desc";
		
		ResponseEntity<String> response = urlRequest(accessToken,url);

		return response;
	}
	
	@PostMapping("/gameapi/games/{accessToken}/{id}")
	public ResponseEntity<String> getGame(@PathVariable String accessToken,@PathVariable String id){


		String url = "https://api.igdb.com/v4/games/?fields="
				+ "name,"
				+ "genres.name,"
				+ "platforms.name,"
				+ "rating,summary,"
				+ "cover.url,"
				+ "artworks.url"
				+ "&limit=100"
				+ "&filter[id][eq]="
				+ id;
		
		ResponseEntity<String> response = urlRequest(accessToken,url);

		return response;
	}
	
	@PostMapping("/gameapi/genres/{accessToken}")
	public ResponseEntity<String> getGenres(@PathVariable String accessToken){


		String url = "https://api.igdb.com/v4/genres/?fields="
				+ "name"
				+ "&limit=100";
		
		ResponseEntity<String> response = urlRequest(accessToken,url);

		return response;
	}
	
	@PostMapping("/gameapi/gamesByGenre/{accessToken}/{genreId}")
	public ResponseEntity<String> getGameByGenre(@PathVariable String accessToken,@PathVariable String genreId){


		String url = "https://api.igdb.com/v4/games/?fields="
				+ "name,"
				+ "genres.name,"
				+ "platforms.name,"
				+ "rating,summary,"
				+ "cover.url,"
				+ "artworks.url"
				+ "&limit=100"
				+ "&filter[rating][gt]=0"
				+ "&filter[genres.id][eq]=" + genreId
				+ "&order=total_rating_count:desc";

		
		ResponseEntity<String> response = urlRequest(accessToken,url);

		return response;
	}
	
	@PostMapping("/gameapi/gamesByTitle/{accessToken}/{title}")
	public ResponseEntity<String> getGameByTitle(@PathVariable String accessToken,@PathVariable String title){


		String url = "https://api.igdb.com/v4/games/?fields="
				+ "name,"
				+ "genres.name,"
				+ "platforms.name,"
				+ "rating,summary,"
				+ "cover.url,"
				+ "artworks.url"
				+ "&limit=100"
				+ "&filter[rating][gt]=0"
				+ "&search=" + title;

		
		ResponseEntity<String> response = urlRequest(accessToken,url);

		return response;
	}
	
	
	
	public ResponseEntity<String> urlRequest(String accessToken, String url){
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Client-ID", "sxmfe1vyjh3j8p9bp5rcc9lzjz6o8x");
		headers.set("Authorization", "Bearer " +accessToken);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		
		return response;
		
	}
}
