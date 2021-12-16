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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="photoURL")
	private String photoURL;
	
	@Column(name="genre1")
	private String genre1;
	
	@Column(name="genre2")
	private String genre2;
	
	@Column(name="genre3")
	private String genre3;
	
	@Column(name="genre4")
	private String genre4;
	
	@Column(name="synopsis", columnDefinition = "LONGTEXT")
	private String synopsis;
	

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private List<UserList> userList;
	
  
	public Game() {
		
	}


	public Game(String title, String synopsis,String photoURL,String genre1,String genre2,String genre3,String genre4) {
		super();
		this.title = title;
		this.synopsis = synopsis;
		this.photoURL = photoURL;
		this.genre1 = genre1;
		this.genre2 =genre2;
		this.genre3 = genre3;
		this.genre4 = genre4;
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
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
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
	
	

	
	


	
	
}
