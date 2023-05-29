package org.mohsoft.service;

import org.mohsoft.model.UserContent;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService {

	// Manages the files by storing or retrieving them
    Path storeFile(MultipartFile file, UserContent userContent);

    Resource loadFileAsResource(String fileName, UserContent userContent);

	Path getFileStorageLocation();
}
