package com.example.exhibitioncuratorandroid.model;

import java.util.ArrayList;

public class ArtworkResults {
    private String query;
    private Integer page;
    private ArrayList<Artwork> artworks;
    private Integer total_pages;

    public ArtworkResults(String query, Integer page, ArrayList<Artwork> artworks, Integer total_pages) {
        this.query = query;
        this.page = page;
        this.artworks = artworks;
        this.total_pages = total_pages;
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

    public Integer getTotal_pages() {
        return total_pages;
    }
}
