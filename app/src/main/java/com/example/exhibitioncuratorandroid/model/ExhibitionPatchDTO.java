package com.example.exhibitioncuratorandroid.model;

public class ExhibitionPatchDTO {
    private String title;
    private String description;

    public ExhibitionPatchDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ExhibitionPatchDTO() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
