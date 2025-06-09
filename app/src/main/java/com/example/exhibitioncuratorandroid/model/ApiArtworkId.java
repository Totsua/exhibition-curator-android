package com.example.exhibitioncuratorandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiArtworkId implements Parcelable {
    long artId;
    String apiOrigin;

    public ApiArtworkId(long artId, String apiOrigin) {
        this.artId = artId;
        this.apiOrigin = apiOrigin;
    }

    protected ApiArtworkId(Parcel in) {
        artId = in.readLong();
        apiOrigin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(artId);
        dest.writeString(apiOrigin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiArtworkId> CREATOR = new Creator<ApiArtworkId>() {
        @Override
        public ApiArtworkId createFromParcel(Parcel in) {
            return new ApiArtworkId(in);
        }

        @Override
        public ApiArtworkId[] newArray(int size) {
            return new ApiArtworkId[size];
        }
    };

    public long getArtId() {
        return artId;
    }

    public String getApiOrigin() {
        return apiOrigin;
    }
}
