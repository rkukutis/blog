package com.rhoopoe.site.utility;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;

import java.util.regex.PatternSyntaxException;

public class FileUtils {

    public static String getFileExtension(String filename) {
        String[] parts;
        try {
            parts = filename.split("\\.");
            if (parts.length != 2) throw new InvalidFileNameException(filename, "image file name is invalid");
        } catch (PatternSyntaxException exception) {
            throw new InvalidFileNameException(filename, "image file name is invalid");
        }
        return parts[1].strip().toLowerCase();
    }
}
