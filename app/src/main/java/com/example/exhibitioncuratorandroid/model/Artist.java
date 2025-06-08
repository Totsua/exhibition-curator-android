package com.example.exhibitioncuratorandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    private Long apiID;
    private String name;


    public Artist(Long apiID, String name) {
        this.apiID = apiID;
        this.name = name;
    }

    protected Artist(Parcel in) {
        if (in.readByte() == 0) {
            apiID = null;
        } else {
            apiID = in.readLong();
        }
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (apiID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(apiID);
        }
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public String getName() {
        return name;
    }

    public Long getApiID() {
        return apiID;
    }
}
