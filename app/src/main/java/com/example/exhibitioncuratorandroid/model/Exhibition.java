package com.example.exhibitioncuratorandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Exhibition implements Parcelable {
    private Long id;
    private String title;
    private String description;
    private List<Artwork> artworks;

    public Exhibition(String title, String description, List<Artwork> artworks) {
        this.title = title;
        this.description = description;
        this.artworks = artworks;
    }

    protected Exhibition(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        description = in.readString();
        artworks = in.createTypedArrayList(Artwork.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeString(description);
        dest.writeTypedList(artworks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exhibition> CREATOR = new Creator<Exhibition>() {
        @Override
        public Exhibition createFromParcel(Parcel in) {
            return new Exhibition(in);
        }

        @Override
        public Exhibition[] newArray(int size) {
            return new Exhibition[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public Long getId() {
        return id;
    }
}
