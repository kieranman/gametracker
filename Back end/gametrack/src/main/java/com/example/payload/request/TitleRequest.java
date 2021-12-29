package com.example.payload.request;

import javax.validation.constraints.NotBlank;

public class TitleRequest {
	String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
