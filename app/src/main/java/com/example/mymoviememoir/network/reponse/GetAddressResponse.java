package com.example.mymoviememoir.network.reponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAddressResponse{

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private String status;

	public List<ResultsItem> getResults(){
		return results;
	}

	public String getStatus(){
		return status;
	}
}