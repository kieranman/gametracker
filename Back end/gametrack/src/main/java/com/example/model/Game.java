package com.example.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "gametable")
public class Game {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;

	@Column(name="avgScore")
	private Double score;
	
	@Column(name="photoURL")
	private String photoURL;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private List<UserList> userList;
	
  
	public Game() {
		
	}


	public Game(String title,Double score,String photoURL) {
		super();
		this.title = title;
		this.score = score;
		this.photoURL = photoURL;
	}
	
	



	public String getPhotoURL() {
		return photoURL;
	}


	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}


	public Double getScore() {
		return score;
	}


	public void setScore(Double score) {
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


	
	


	
	
}
