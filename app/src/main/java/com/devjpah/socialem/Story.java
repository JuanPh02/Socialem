package com.devjpah.socialem;

import android.media.Image;

public class Story {

    private Image image;
    private String username;
    private boolean seen;

    public Story( String username, boolean seen) {
        this.username = username;
        this.seen = seen;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
