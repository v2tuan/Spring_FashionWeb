package com.fashionweb.Controllers.web;

import com.fashionweb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
            }

            Resource file = storageService.loadAsResource(filename + ".jpg");
            String contentType = Files.probeContentType(Paths.get(file.getURI()));

            return ResponseEntity.ok()
                    .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null); // Trả về 404 nếu không tìm thấy file
        }
    }
}
