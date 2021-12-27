package com.example.payload.response;

import java.util.List;

import javax.persistence.Column;

public class GameResponse {
	private Long id;
	private String title;
	private String photoURL;
	private String genre1;
	private String genre2;
	private String genre3;
	private String genre4;
	private String synopsis;
	private Double score;
	
	
	public GameResponse(Long id, String title, String photoURL, String genre1, String genre2, String genre3,
			String genre4, String synopsis, Double score) {
		super();
		this.id = id;
		this.title = title;
		this.photoURL = photoURL;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.genre3 = genre3;
		this.genre4 = genre4;
		this.synopsis = synopsis;
		this.score = score;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPhotoURL() {
		return photoURL;
	}


	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}


	public String getGenre1() {
		return genre1;
	}


	public void setGenre1(String genre1) {
		this.genre1 = genre1;
	}


	public String getGenre2() {
		return genre2;
	}


	public void setGenre2(String genre2) {
		this.genre2 = genre2;
	}


	public String getGenre3() {
		return genre3;
	}


	public void setGenre3(String genre3) {
		this.genre3 = genre3;
	}


	public String getGenre4() {
		return genre4;
	}


	public void setGenre4(String genre4) {
		this.genre4 = genre4;
	}


	public String getSynopsis() {
		return synopsis;
	}


	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}


	public Double getScore() {
		return score;
	}


	public void setScore(Double score) {
		this.score = score;
	}
	
	




}
