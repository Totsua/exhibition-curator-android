package com.example.exhibitioncuratorandroid.model;

public class Artwork {

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
}
