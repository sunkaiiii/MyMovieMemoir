package com.example.mymoviememoir.network.reponse;

import com.google.gson.annotations.SerializedName;

public class MovieSuburbResponse{

	@SerializedName("count")
	private int count;

	@SerializedName("suburb")
	private String suburb;

	public int getCount(){
		return count;
	}

	public String getSuburb(){
		return suburb;
	}
}