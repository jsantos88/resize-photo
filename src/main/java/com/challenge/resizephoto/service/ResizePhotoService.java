package com.challenge.resizephoto.service;

import java.util.List;

import com.challenge.resizephoto.resource.model.RequestList;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFSDBFile;

public interface ResizePhotoService {
	
	void savePhotosResized(RequestList request, Mongo mongo);
	
	List<String> getPhotosNames(Mongo mongo);
	
	GridFSDBFile getPhotosSaved(Mongo mongo, String img);

}
