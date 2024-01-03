package com.rhoopoe.site.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageFileStorage {
    private static final String root = "src/main/resources";

    public static String storeThumbnail(byte[] bytes, String fileName) throws IOException {
        String path = root + "/images/thumbnails/" + fileName;
        File file = new File(path);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(bytes);
        }
        return "http://localhost:8080/uploads/images/thumbnails/" + fileName;
    }
    public static byte[] retrieveThumbnail(String fileName){
        byte[] bytes = null;
        try {
            Path path = Path.of(root + "/images/thumbnails/" + fileName);
            bytes = Files.readAllBytes(path);
        } catch (IOException exception){
            // TODO: Add default thumbnail
            bytes = new byte[0];
        }
        return bytes;
    }
}
