package com.example.exhibitioncuratorandroid.model;

import java.util.ArrayList;

public class ArtworkResults {
    private String query;
    private Integer page;
    private ArrayList<Artwork> artworks;

    public ArtworkResults(String query, Integer page, ArrayList<Artwork> artworks) {
        this.query = query;
        this.page = page;
        this.artworks = artworks;
    }

    public String getQuery() {
        return query;
    }

    public ArrayList<Artwork> getArtworks() {
        return artworks;
    }

    public Integer getPage() {
        return page;
    }
}
