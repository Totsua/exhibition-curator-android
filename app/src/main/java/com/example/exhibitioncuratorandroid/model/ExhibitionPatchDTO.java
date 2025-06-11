package com.example.exhibitioncuratorandroid.model;

public class ExhibitionPatchDTO {
    private String Title;
    private String Description;

    public ExhibitionPatchDTO(String title, String description) {
        Title = title;
        Description = description;
    }

    public ExhibitionPatchDTO() {
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
