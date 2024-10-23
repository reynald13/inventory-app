package com.evotektask.inventory_app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class FileValidator {

    @Value("${allowed.mime.types}")
    private List<String> allowedMimeTypes;

    public String validateMimeType(MultipartFile file) {
        String mimeType = file.getContentType();
        if (!allowedMimeTypes.contains(mimeType)) {
            return "Invalid file type. Only the following types are allowed: " + String.join(", ", allowedMimeTypes);
        }
        return null;
    }

    public String validateFileSize(MultipartFile file, long maxSizeInBytes) {
        if (file.getSize() > maxSizeInBytes) {
            return "File size exceeds the limit of " + maxSizeInBytes / (1024 * 1024) + " MB.";
        }
        return null;  // No errors, file size is valid
    }
}