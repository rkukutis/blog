package com.rhoopoe.site.utility;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;

import java.util.regex.PatternSyntaxException;

public class FileUtils {

    private FileUtils() {
    }

    public static String getFileExtension(String filename) {
        String[] parts;
        try {
            parts = filename.split("\\.");
            if (parts.length != 2 ||
                    parts[0].isEmpty() ||
                    parts[1].isEmpty() ||
                    parts[1].length() < 3 ||
                    parts[1].length() > 4
            )
                throw new InvalidFileNameException(filename, "image file name is invalid");
        } catch (PatternSyntaxException exception) {
            throw new InvalidFileNameException(filename, "image file name is invalid");
        }
        return parts[1].strip().toLowerCase();
    }
}
