
package com.gradescope.medialibrary;

//package MediaLibrary;

public abstract class Media {
    protected String title;
    public enum Genre { COMEDY, SCIFI };
    protected Genre genre;
    public enum MediaType { MOVIE, TVSHOW };

    public String getTitle() {
        return title;
    }

    public String toString() {
        MediaType thisType = getMediaType();

        switch (thisType) {
            case MOVIE:
                return "Movie title: " + getTitle();
            case TVSHOW:
                return "Show title: " + getTitle();
            default:
                return null;
        }
    }

    public abstract MediaType getMediaType();
    
    /*
     *     public Media(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }
     */
}