package com.example.dto;

import javax.validation.constraints.NotNull;

import com.example.model.Game;

public class GameDto {

    private Long id;
    private @NotNull String title;
    private @NotNull String photoURL;
    private @NotNull String synopsis;
    private String genre1;
    private String genre2;
    private String genre3;
    private String genre4;
    

    public GameDto(Game game) {
        this.setId(game.getId());
        this.setTitle(game.getTitle());
        this.setPhotoURL(game.getPhotoURL());
        this.setSynopsis(game.getSynopsis());
        this.setGenre1(game.getGenre1());
        this.setGenre2(game.getGenre2());
        this.setGenre3(game.getGenre3());
        this.setGenre4(game.getGenre4());
    }

    public GameDto(@NotNull String title, @NotNull String photoURL, String synopsis, String genre1,String genre2,String genre3 , String genre4) {
        this.title = title;
        this.photoURL = photoURL;
        this.synopsis = synopsis;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.genre3 = genre3;
        this.genre4 = genre4;
    }

    public GameDto() {
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