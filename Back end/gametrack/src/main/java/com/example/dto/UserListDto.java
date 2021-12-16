package com.example.dto;

import javax.validation.constraints.NotNull;

import com.example.model.Game;
import com.example.model.UserList;

public class UserListDto {

    private Long id;
    private String review;
    private String status;
    private Integer rating;

    
    public UserListDto(UserList userList) {
        this.setId(userList.getId());
        this.setReview(userList.getReview());
        this.setStatus(userList.getStatus());
        this.setRating(userList.getRating());
    }

    public UserListDto(String review,String status,Integer rating) {
    	this.rating = rating;
    	this.status = status;
    	this.review = review;
    	
    }

    public UserListDto() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	


}
