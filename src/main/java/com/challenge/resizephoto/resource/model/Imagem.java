package com.challenge.resizephoto.resource.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Imagem {
	
	@Id
	@JsonIgnore
	public String id;
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Imagem [id=" + id + ", url=" + url + "]";
	}

}
