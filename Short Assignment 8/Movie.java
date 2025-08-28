
package com.gradescope.medialibrary;

//package MediaLibrary;

public class Movie extends Media {
    public Movie(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.MOVIE;
    }
}