package com.example.exhibitioncuratorandroid.model;

import java.util.List;

public class Exhibition {
    private String title;
    private String description;
    private List<Artwork> artworkList;

    public Exhibition(String title, String description, List<Artwork> artworkList) {
        this.title = title;
        this.description = description;
        this.artworkList = artworkList;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Artwork> getArtworkList() {
        return artworkList;
    }
}
