package com.example.mymoviememoir.network.reponse;

import com.google.gson.annotations.SerializedName;

public class SearchMetadata{

	@SerializedName("count")
	private int count;

	public int getCount(){
		return count;
	}
}