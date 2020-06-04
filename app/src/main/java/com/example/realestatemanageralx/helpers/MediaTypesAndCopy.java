package com.example.realestatemanageralx.helpers;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MediaTypesAndCopy {

    private static final ArrayList<String> imageExtensions = new ArrayList<String>(
            Arrays.asList("jpg", "bmp", "gif", "png"));

    private static final ArrayList<String> videoExtensions = new ArrayList<String>(
            Arrays.asList("3gp", "mp4", "mkv"));

    /**
     * Checks whether or not a file is of an image type managed by android
     * @param filename
     * @return
     */
    public static boolean isImage(String filename) {

        if (imageExtensions.contains(filename.substring(filename.length() - 3))) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether or not a file is of a video type managed by android
     * @param filename
     * @return
     */
    public static boolean isVideo(String filename) {

        if (videoExtensions.contains(filename.substring(filename.length() - 3))) {
            return true;
        }
        return false;
    }

    /**
     * Copies a file from an input stream to an output stream
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        Log.i("alex", "helper copyFile");

        try {
            byte[] buffer = new byte[1024];
            int read;

            //int bits = 0;

            while ((read = in.read(buffer)) != -1) {
                //bits += 1;
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            Log.e("alex", e.toString());
        }
    }


}
