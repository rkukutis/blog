package com.rhoopoe.site.services.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageProcessingService {

    BufferedImage processImage(byte[] imageBytes) throws IOException;

    String getImagePath();


}
