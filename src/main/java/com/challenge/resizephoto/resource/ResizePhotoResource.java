package com.challenge.resizephoto.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.challenge.resizephoto.resource.model.Imagem;
import com.challenge.resizephoto.resource.model.RequestList;
import com.challenge.resizephoto.resource.model.ResponseList;
import com.challenge.resizephoto.service.ResizePhotoService;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
@RequestMapping("/resizephoto")
public class ResizePhotoResource {
	
	@Autowired
	private ResizePhotoService resizePhotoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public void getListPhotos(Mongo mongo) {
		
		String uri = "http://54.152.221.29/images.json";
		
		RestTemplate restTemplate = new RestTemplate();
		RequestList request = restTemplate.getForObject(uri, RequestList.class);
		
		resizePhotoService.savePhotosResized(request, mongo);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "resized/{img}")
	public ResponseEntity<InputStreamResource> resized(Mongo mongo, @PathVariable String img) {
		
		GridFSDBFile photo = resizePhotoService.getPhotosSaved(mongo, img);
		
		return ResponseEntity.ok()
                .contentLength(photo.getLength())
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .body(new InputStreamResource(photo.getInputStream()));
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "urls")
	public ResponseEntity<ResponseList> getUrls(Mongo mongo) {
		
		List<String> listPhotoNames = resizePhotoService.getPhotosNames(mongo);
		
		ResponseList response = new ResponseList(new ArrayList<>());
		
		for (String name : listPhotoNames) {
			Imagem imagem = new Imagem();
			imagem.setUrl("http://localhost:8080/resizephoto/resized/" + name);
			response.getImages().add(imagem);
		}
		
		return ResponseEntity.ok().body(response);
		
	}
	
}
