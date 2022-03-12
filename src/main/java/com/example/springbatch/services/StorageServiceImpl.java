package com.example.springbatch.services;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {
    private static Map<String, Map<InputStream, Instant>> files = new ConcurrentHashMap<>();

    public StorageServiceImpl() {

    }

    private void manageFile(Object file){
        HashMap<InputStream, Instant> value = new HashMap<>();
        InputStream inputStream = null;
        String fileName = "";

        if(file instanceof MultipartFile){
            try {
                inputStream = ((MultipartFile) file).getInputStream();
                fileName = ((MultipartFile) file).getName();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if (file instanceof InputStream){
            try {
                fileName = "tmp_file_".concat(Base64.getEncoder().encodeToString(((InputStream) file).readAllBytes()));
            }catch (IOException e){

            }
            inputStream = (InputStream) file;
        } else if (file instanceof Byte || file instanceof Byte[] || file instanceof byte[]){

                fileName = "tmp_file_".concat(Base64.getEncoder().encodeToString(((byte[]) file)));

            inputStream = new ByteArrayInputStream((byte[]) file);
        }

        if(inputStream != null){
            value.put(inputStream, Instant.now());
            if(fileName == null || fileName.isEmpty()) {

            }

            files.put(fileName, value);
        }
    }

    @Override
    public void storeFile(MultipartFile file) {
        manageFile(file);
    }

    @Override
    public void storeFile(InputStream file) {
        manageFile(file);
    }

    @Override
    public void storeFile(byte[] file) {
        manageFile(file);
    }

    @Override
    public InputStream fetchFile(String name) {
        if(files.containsKey(name)){
            return files.get(name).keySet().stream().findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public InputStream fetchLastFile() {
        return null;
    }

    @Override
    public void clearOldFiles() {

    }

    @Override
    public void evictFilesByTime(Instant instant) {

    }

    @Override
    public void evictFilesByTime(long time) {

    }

    @Override
    public List<InputStream> fetchAll() {
        List<InputStream> valuesFound = new ArrayList<>();

        for (Map.Entry<String, Map<InputStream, Instant>> values : files.entrySet()){
            valuesFound.add(values.getValue().keySet().stream().findFirst().get());
        }

        Function<Set<InputStream>, InputStream> function = inputStream -> inputStream.stream().findFirst().orElse(null);

        return files.values()
                .stream()
                .map(Map::keySet)
                .map(function)
                .collect(Collectors.toList());

        //return valuesFound;
    }

}
