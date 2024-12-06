package com.fashionweb.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class ImageUtil {
    public String encodeImageToBase64(MultipartFile image) throws IOException {
        byte[] fileContent = image.getBytes();
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
