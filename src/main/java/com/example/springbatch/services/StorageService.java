package com.example.springbatch.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;

public interface StorageService {
    public void storeFile(MultipartFile file);

    public void storeFile(InputStream file);

    public void storeFile(byte[] file);

    public InputStream fetchFile(String name);

    public InputStream fetchLastFile();

    public void clearOldFiles();

    public void evictFilesByTime(Instant instant);

    public void evictFilesByTime(long time);

    public List<InputStream> fetchAll();
}
