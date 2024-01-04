package com.rhoopoe.site.utils.image_file_storage;

import java.io.IOException;

public interface ImageFileStorageService {
    public static final String root = "src/main/resources";
    public static final String host = "http://localhost:8080";

    String store(byte[] imageBytes, String imageName) throws IOException;
    byte[] retrieve(String fileName) throws IOException;
}
