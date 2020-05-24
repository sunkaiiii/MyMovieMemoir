package com.example.mymoviememoir.network.reponse;

import com.google.gson.annotations.SerializedName;

public class Geometry{

	@SerializedName("viewport")
	private Viewport viewport;

	@SerializedName("location")
	private Location location;

	@SerializedName("location_type")
	private String locationType;

	public Viewport getViewport(){
		return viewport;
	}

	public Location getLocation(){
		return location;
	}

	public String getLocationType(){
		return locationType;
	}
}