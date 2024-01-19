package com.rhoopoe.site.service.imageprocessing;

import com.rhoopoe.site.utility.ImageProcessing;
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
    @Value("${app.constants.image-location.thumbnail}")
    private String imagePath;

    @Value("${app.constants.image-processing.thumbnail.height}")
    private int thumbnailHeight;

    @Value("${app.constants.image-processing.thumbnail.width}")
    private int thumbnailWidth;

    @Override
    public BufferedImage processImage(byte[] imageBytes, String imageExtension) throws IOException {
        return new ImageProcessing(imageBytes)
                .resize(thumbnailHeight, thumbnailWidth)
                .squareCropCenterWidth()
                .toImage();
    }
}
