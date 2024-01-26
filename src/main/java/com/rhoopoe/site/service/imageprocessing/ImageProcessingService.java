package com.rhoopoe.site.service.imageprocessing;

import com.rhoopoe.site.exception.ImageProcessingException;

import java.awt.image.BufferedImage;

public interface ImageProcessingService {

    BufferedImage processImage(byte[] imageBytes, String imageExtension) throws ImageProcessingException;

    String getImagePath();


}
