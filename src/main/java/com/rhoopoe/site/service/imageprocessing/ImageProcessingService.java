package com.rhoopoe.site.service.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageProcessingService {

    BufferedImage processImage(byte[] imageBytes) throws IOException;

    String getImagePath();


}
