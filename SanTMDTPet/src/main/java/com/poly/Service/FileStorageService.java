package com.poly.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {

    public void save(MultipartFile file,UUID uuid);

    Resource load(String fileName);
}
