package com.rhoopoe.site.service.imageprocessing;

import com.rhoopoe.site.utility.ImageProcessing;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@Slf4j
@Getter
public class PostPictureProcessingService implements ImageProcessingService {

    private final String imagePath = "/images/post-pictures/";

    @Value("${app.constants.image-processing.post-picture.height}")
    private int pictureHeight;

    @Value("${app.constants.image-processing.post-picture.width}")
    private int pictureWidth;

    @Override
    public BufferedImage processImage(byte[] imageBytes, String imageExtension) throws IOException {
        return new ImageProcessing(imageBytes)
                .resize(pictureHeight)
                .toImage();
    }

}
