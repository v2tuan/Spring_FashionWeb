package com.fashionweb.exception;

public class StorageException extends RuntimeException{
    // Constructor chỉ với thông báo lỗi
    public StorageException(String message) {
        super(message);
    }

    // Constructor với thông báo lỗi và nguyên nhân
    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
