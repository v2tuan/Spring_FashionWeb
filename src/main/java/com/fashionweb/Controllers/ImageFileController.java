package com.fashionweb.Controllers;

import com.fashionweb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/files")
public class ImageFileController {
    @Autowired
    private IStorageService storageService;

    @GetMapping("/")
    public ResponseEntity<Resource> serveDefaultFile() {
        return serveFile("default");
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        return serveFileInternal(filename);
    }

    private ResponseEntity<Resource> serveFileInternal(String filename) {
        try {
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
            }

            Resource file = storageService.loadAsResource(filename + ".jpg");
            String contentType = Files.probeContentType(Paths.get(file.getURI()));
            if (contentType == null) {
                contentType = "image/jpeg";
            }

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
