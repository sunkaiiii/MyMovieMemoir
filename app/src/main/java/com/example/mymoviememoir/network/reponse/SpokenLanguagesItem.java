package com.example.mymoviememoir.network.reponse;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguagesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("iso_639_1")
	private String iso6391;

	public String getName(){
		return name;
	}

	public String getIso6391(){
		return iso6391;
	}
}