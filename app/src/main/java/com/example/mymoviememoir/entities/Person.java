package com.example.mymoviememoir.entities;

import com.example.mymoviememoir.network.request.base.BasePostModel;
import com.google.gson.annotations.SerializedName;

public class Person extends BasePostModel {

	@SerializedName("fname")
	private String fname;

	@SerializedName("address")
	private String address;

	@SerializedName("gender")
	private String gender;

	@SerializedName("dob")
	private String dob;

	@SerializedName("surname")
	private String surname;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("credentialsId")
	private Credentials credentialsId;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	public void setFname(String fname){
		this.fname = fname;
	}

	public String getFname(){
		return fname;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setSurname(String surname){
		this.surname = surname;
	}

	public String getSurname(){
		return surname;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return postcode;
	}

	public void setCredentialsId(Credentials credentialsId){
		this.credentialsId = credentialsId;
	}

	public Credentials getCredentialsId(){
		return credentialsId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}
}