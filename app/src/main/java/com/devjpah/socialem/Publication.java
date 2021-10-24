package com.devjpah.socialem;

import java.util.List;

public class Publication {

    private String author;
    private String dateCreated;
    private String media;
    private String description;
    private int likes;
    private List<Comment> comments;

    public Publication(String author, String dateCreated, String media, String description, int likes, List<Comment> comments) {
        this.author = author;
        this.dateCreated = dateCreated;
        this.media = media;
        this.description = description;
        this.likes = likes;
        this.comments = comments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
