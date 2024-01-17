package com.rhoopoe.site.services.imageprocessing;

import com.rhoopoe.site.utils.ImageProcessing;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
@Service
public class ThumbnailProcessingService implements ImageProcessingService {

    @Getter
    private final String imagePath = "/images/thumbnails/";

    @Value("${app.constants.image-processing.thumbnail.height}")
    private int thumbnailHeight;

    @Value("${app.constants.image-processing.thumbnail.width}")
    private int thumbnailWidth;

    @Override
    public BufferedImage processImage(byte[] imageBytes) throws IOException {
        return new ImageProcessing(imageBytes)
                .resize(thumbnailHeight, thumbnailWidth)
                .squareCropCenterWidth()
                .toImage();
    }
}
