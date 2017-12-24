package com.challenge.resizephoto.util;

public enum ConstantsEnum {
	
	SMALL(320, 240, "small"),
	MEDIUM(384, 288, "medium"),
	LARGE(640, 480, "large");
	
	public int x;
	
	public int y;
	
	public String name;
	
	ConstantsEnum(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}

}
