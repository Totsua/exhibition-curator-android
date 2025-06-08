package com.example.exhibitioncuratorandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Artwork implements Parcelable {

    // todo: change 'id' to 'apiId'
    private Long id;
    private String title;
    private String description;
    private String altText;
    private String apiOrigin;
    private String imageUrl;
    private Artist artist;

    public Artwork(Long id, String title, String description, String altText, String apiOrigin, String imageUrl, Artist artist) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.altText = altText;
        this.apiOrigin = apiOrigin;
        this.imageUrl = imageUrl;
        this.artist = artist;
    }

    protected Artwork(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        description = in.readString();
        altText = in.readString();
        apiOrigin = in.readString();
        imageUrl = in.readString();
        artist = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAltText() {
        return altText;
    }

    public String getApiOrigin() {
        return apiOrigin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Artist getArtist() {
        return artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(altText);
        parcel.writeString(apiOrigin);
        parcel.writeString(imageUrl);
        parcel.writeParcelable(artist, i);
    }
}
