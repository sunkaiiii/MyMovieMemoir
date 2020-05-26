package com.example.mymoviememoir.network.reponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StatusesItem implements Parcelable {

    @SerializedName("id_str")
    private String idStr;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private long id;

    @SerializedName("text")
    private String text;

    @SerializedName("user")
    private User user;

    public String getIdStr() {
        return idStr;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idStr);
        dest.writeString(this.createdAt);
        dest.writeLong(this.id);
        dest.writeString(this.text);
        dest.writeParcelable(this.user, flags);
    }

    public StatusesItem() {
    }

    protected StatusesItem(Parcel in) {
        this.idStr = in.readString();
        this.createdAt = in.readString();
        this.id = in.readLong();
        this.text = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<StatusesItem> CREATOR = new Creator<StatusesItem>() {
        @Override
        public StatusesItem createFromParcel(Parcel source) {
            return new StatusesItem(source);
        }

        @Override
        public StatusesItem[] newArray(int size) {
            return new StatusesItem[size];
        }
    };
}