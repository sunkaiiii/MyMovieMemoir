package com.example.mymoviememoir.network.reponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchTwtitterResponse{

	@SerializedName("statuses")
	private List<StatusesItem> statuses;

	@SerializedName("search_metadata")
	private SearchMetadata searchMetadata;

	public List<StatusesItem> getStatuses(){
		return statuses;
	}

	public SearchMetadata getSearchMetadata(){
		return searchMetadata;
	}
}