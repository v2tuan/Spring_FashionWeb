package com.fashionweb.service.Impl;

import com.fashionweb.Config.StorageProperties;
import com.fashionweb.service.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements IStorageService {

    private final Path rootLocation;

    public String FileSystemStorageService(StorageProperties properties) {

    }

    @Override
    public void init() {

    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public Path load(String fileName) {
        return null;
    }

    @Override
    public Resource loadAsResource(String fileName) {
        return null;
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Lỗi lưu file rỗng");
            }

            Path targetLocation = this.rootLocation.resolve(Paths.get());
        }
    }

    @Override
    public String getStorageFileName(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "img" + id + "." + ext;
    }

}
