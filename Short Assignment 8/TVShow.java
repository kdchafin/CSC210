package com.gradescope.medialibrary;

//package MediaLibrary;

public class TVShow extends Media {
    public TVShow(String title, Genre genre) {
    	this.title = title;
        this.genre = genre;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.TVSHOW;
    }
}