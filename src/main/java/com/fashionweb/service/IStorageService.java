package com.fashionweb.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IStorageService {

    void init();
    void deleteFile(String fileName);
    Path load(String fileName);
    Resource loadAsResource(String fileName);
    void store(MultipartFile file, String fileName);
    String getStorageFileName(MultipartFile file, String id);

}
