package com.challenge.resizephoto.service.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.challenge.resizephoto.resource.model.Imagem;
import com.challenge.resizephoto.resource.model.RequestList;
import com.challenge.resizephoto.service.ResizePhotoService;
import com.challenge.resizephoto.util.ConstantsEnum;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class ResizePhotoServiceBean implements ResizePhotoService {
	
	@Override
	public void savePhotosResized(RequestList request, Mongo mongo) {
		
		DB db = mongo.getDB("imagedb");
		
		int count = 0;
		
		for (Imagem img : request.getImages()) {
			
			count++;
			
			try {
				URL url = new URL(img.getUrl());
				BufferedImage bufferedImage = ImageIO.read(url);
				
				BufferedImage bufferedImageSmallResized = resizeImage(
						bufferedImage, bufferedImage.getType(), ConstantsEnum.SMALL.getX(), ConstantsEnum.SMALL.getY());
				
				BufferedImage bufferedImageMediumResized = resizeImage(
						bufferedImage, bufferedImage.getType(), ConstantsEnum.MEDIUM.getX(), ConstantsEnum.MEDIUM.getY());
				
				BufferedImage bufferedImageLargeResized = resizeImage(
						bufferedImage, bufferedImage.getType(), ConstantsEnum.LARGE.getX(), ConstantsEnum.LARGE.getY());
				
				System.out.println(bufferedImage);
				System.out.println(bufferedImageSmallResized);
				System.out.println("\n\n");
				
				String smallName = "img_"+count+"_"+ ConstantsEnum.SMALL.getName();
				String mediumName = "img_"+count+"_"+ ConstantsEnum.MEDIUM.getName();
				String largeName = "img_"+count+"_"+ ConstantsEnum.LARGE.getName();
				
				File imageSmallFile = new File(smallName + ".jpg");
				File imageMediumFile = new File(mediumName + ".jpg");
				File imageLargeFile = new File(largeName + ".jpg");
				
				ImageIO.write(bufferedImageSmallResized, "jpg", imageSmallFile);
				ImageIO.write(bufferedImageMediumResized, "jpg", imageMediumFile);
				ImageIO.write(bufferedImageLargeResized, "jpg", imageLargeFile);
				
				GridFS gfsPhoto = new GridFS(db, "photo");
				
				GridFSInputFile gfsFile = gfsPhoto.createFile(imageSmallFile);
				gfsFile.setFilename(smallName);
				gfsFile.save();
				
				gfsFile = gfsPhoto.createFile(imageMediumFile);
				gfsFile.setFilename(mediumName);
				gfsFile.save();
				
				gfsFile = gfsPhoto.createFile(imageSmallFile);
				gfsFile.setFilename(mediumName);
				gfsFile.save();
				
				gfsFile = gfsPhoto.createFile(imageLargeFile);
				gfsFile.setFilename(largeName);
				gfsFile.save();
				
				
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		String newFileName = "img_1_small";
		GridFS gfsPhoto = new GridFS(db, "photo");
		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
		System.out.println("@@@@@ " + imageForOutput);
		
	}
	
	@Override
	public GridFSDBFile getPhotosSaved(Mongo mongo, String img) {
		
		DB db = mongo.getDB("imagedb");
		
		GridFS gfsPhoto = new GridFS(db, "photo");
		
		GridFSDBFile imageForOutput = gfsPhoto.findOne(img);
		
		return imageForOutput;
	}
	
	
	private BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D graphic2d = resizedImage.createGraphics();
		graphic2d.drawImage(originalImage, 0, 0, width, height, null);
		graphic2d.dispose();

		return resizedImage;
	}

	@Override
	public List<String> getPhotosNames(Mongo mongo) {
		
		List<String> returnList = new ArrayList<>();
		
		DB db = mongo.getDB("imagedb");
		
		GridFS gfsPhoto = new GridFS(db, "photo");
		DBCursor cursor = gfsPhoto.getFileList();
		
		GridFSDBFile imageForOutput;
		
		int qty = cursor.count();
		
		String smallName;
		String mediumName;
		String largeName;
		
		for (int i = 1; i < qty; i++) {
			smallName = "img_"+i+"_"+ ConstantsEnum.SMALL.getName();
			mediumName = "img_"+i+"_"+ ConstantsEnum.MEDIUM.getName();
			largeName = "img_"+i+"_"+ ConstantsEnum.LARGE.getName();
			
			imageForOutput = gfsPhoto.findOne(smallName);
			if (imageForOutput != null) {
				returnList.add(smallName);
			}
			imageForOutput = gfsPhoto.findOne(mediumName);
			if (imageForOutput != null) {
				returnList.add(mediumName);
			}
			imageForOutput = gfsPhoto.findOne(largeName);
			if (imageForOutput != null) {
				returnList.add(largeName);
			}
		}
		return returnList;
	}

}
