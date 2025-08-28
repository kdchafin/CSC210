
package com.gradescope.medialibrary;

//package MediaLibrary;

import java.util.List;
import java.util.ArrayList;

public class MediaLibrary {
    protected List<Media> mediaList;

    public MediaLibrary() {
        mediaList = new ArrayList<>();
    }

    public void addMedia(Media media) {
        mediaList.add(media);
    }

    public String toString() {
        String string = "";
        for (Media media : mediaList) {
            string += media + "\n";
        }
        
        string = string.substring(0, string.length() - 1);
        
        return string;
    }
}