package com.example.exhibitioncuratorandroid.model;

public class Artist {
    private Long apiID;
    private String name;


    public Artist(Long apiID, String name) {
        this.apiID = apiID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getApiID() {
        return apiID;
    }
}
