package com.example.realestatemanageralx.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MediaTypesAndCopy {

    private final ArrayList<String> imageExtensions = new ArrayList<String>(
            Arrays.asList("jpg", "bmp", "gif", "png"));

    private final ArrayList<String> videoExtensions = new ArrayList<String>(
            Arrays.asList("3gp", "mp4", "mkv"));

    public boolean isImage(String filename) {

        if (imageExtensions.contains(filename.substring(filename.length() - 3))) {
            return true;
        }
        return false;
    }

    public boolean isVideo(String filename) {

        if (videoExtensions.contains(filename.substring(filename.length() - 3))) {
            return true;
        }
        return false;
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        int bits = 0;

        while((read = in.read(buffer)) != -1){
            bits +=1;
            out.write(buffer, 0, read);
        }
    }


}
