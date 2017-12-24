package com.challenge.resizephoto.resource.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestList {
	
	private final List<Imagem> images;
	
	
	public RequestList(@JsonProperty("images") List<Imagem> images) {
		this.images = images;
	}
	
	public List<Imagem> getImages() {
		return images;
	}

	@Override
	public String toString() {
		return images.stream().map(Imagem::toString).collect(Collectors.joining(","));
	}
	
	
}
