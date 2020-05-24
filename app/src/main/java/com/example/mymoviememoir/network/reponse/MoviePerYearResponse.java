package com.example.mymoviememoir.network.reponse;

import com.google.gson.annotations.SerializedName;

public class MoviePerYearResponse{

	@SerializedName("number")
	private int number;

	@SerializedName("month")
	private String month;

	public int getNumber(){
		return number;
	}

	public String getMonth(){
		return month;
	}
}