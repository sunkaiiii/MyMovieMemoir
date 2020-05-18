package com.example.mymoviememoir.network.reponse;

import java.util.List;

public class MovieSearchListResponse{
	private int page;
	private int totalPages;
	private List<MovieSearchListItem> results;
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<MovieSearchListItem> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}