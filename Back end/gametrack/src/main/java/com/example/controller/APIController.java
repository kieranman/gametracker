package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class APIController {

	

	@PostMapping("/GameApi/games/{page}")
	public ResponseEntity<String> getAllGames(@PathVariable Integer page){
		RestTemplate restTemplate = new RestTemplate();
		int nthTerm =4;
		int offsetVal = nthTerm*page-4;
		
		String url = "https://api.igdb.com/v4/games/?fields=name,genres.name,platforms.name,aggregated_rating,summary,cover.url&limit=4&offset="+String.valueOf(offsetVal);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Client-ID", "5wice72ltv907mw90npdte7zsmtp99");
		headers.set("Authorization", "Bearer 0vt05xzve36n5frc86xr0apyvtdqdo");
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);



		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return response;
	}
}
