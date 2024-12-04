package com.fashionweb.service.Impl;

import com.fashionweb.Config.StorageProperties;
import com.fashionweb.exception.StorageException;
import com.fashionweb.service.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements IStorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Không thể khởi tạo thư mục lưu trữ", e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path filePath = rootLocation.resolve(Paths.get(fileName).normalize().toAbsolutePath());
            Files.delete(filePath);
        } catch (IOException e) {
            throw new StorageException("Không thể xóa file: " + fileName, e);
        }
    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try{
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()||resource.isReadable()){
                return resource;
            }
            throw new StorageException("Không thể đọc file: " + fileName);

        }
        catch (Exception e){
            throw new StorageException("Không thể đọc file: " + fileName, e);
        }
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Không thể lưu file rỗng");
            }

            // Tạo đường dẫn lưu trữ
            Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Không thể lưu trữ tập tin bên ngoài thư mục hiện tại");
            }

            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            }

        } catch (IOException e) {
            throw new StorageException("Lỗi lưu file: " , e);
        }
    }

    @Override
    public String getStorageFileName(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "img" + id + "." + ext;
    }

}
