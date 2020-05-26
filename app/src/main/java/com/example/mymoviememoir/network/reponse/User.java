package com.example.mymoviememoir.network.reponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

	@SerializedName("profile_image_url_https")
	private String profileImageUrlHttps;

	@SerializedName("screen_name")
	private String screenName;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	public String getProfileImageUrlHttps(){
		return profileImageUrlHttps;
	}

	public String getScreenName(){
		return screenName;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public String getCreatedAt(){
		return createdAt;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.profileImageUrlHttps);
		dest.writeString(this.screenName);
		dest.writeString(this.name);
		dest.writeString(this.description);
		dest.writeString(this.createdAt);
	}

	public User() {
	}

	protected User(Parcel in) {
		this.profileImageUrlHttps = in.readString();
		this.screenName = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.createdAt = in.readString();
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}