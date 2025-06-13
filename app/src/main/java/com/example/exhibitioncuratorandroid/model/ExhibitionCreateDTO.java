

package com.example.exhibitioncuratorandroid.model;

public class ExhibitionCreateDTO {
    private String title;
    private String description;

    public ExhibitionCreateDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ExhibitionCreateDTO() {
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