package com.example.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 
public class UserListRequest {

    private Integer rating;

    private String review;
    
    private String status;

    private Long gameId;

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
    

    
}
