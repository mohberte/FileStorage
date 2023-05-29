package org.mohsoft.service;

import org.mohsoft.FileStorageProperties;
import org.mohsoft.model.UserContent;
import org.mohsoft.repository.UserContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements FileStorageService {

	@Autowired
	private UserContentRepository userContentRepository;
    private final Path fileStorageLocation;

    @Autowired
    public FileSystemStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
        	
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory for file storage.", e);
        }
    }

    @Override
    public Path storeFile(MultipartFile file, UserContent userContent) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
        	
            // Save the UserContent object to the database and get the assigned id
        	Long userContentId = userContent.getId();

            // Create the target file path using the userContentId
            Path targetLocation = this.fileStorageLocation.resolve(userContentId + "_"+fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            userContent.setFileName(userContentId + "_"+ file.getOriginalFilename());
            
            // Update the filePath in the UserContent object and save it again
            userContent.setFilePath(targetLocation.toString());
            userContent.setFileName(userContentId + "_"+ file.getOriginalFilename());
            userContentRepository.save(userContent);

            return targetLocation;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again.", e);
        }
    }


    // Loads the file if found
    @Override
    public Resource loadFileAsResource(String fileName, UserContent userContent) {
    	Long userContentId = userContent.getId();
        try {
        	
        	String newFileName = userContentId + "_" + fileName;
        	
            Path filePath = this.fileStorageLocation.resolve( fileName);
            
            
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + fileName, e);
        }
    }
    

    // Gets the file's location
    @Override
    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

	public void deleteFile(UserContent userContent) {
		  String filePath = "C:/Users/moh_b/Documents/Java/storage/" + userContent.getFileName();

		    try {
		    	 System.out.println(filePath);
		        Path path = Paths.get(filePath);
		        Files.deleteIfExists(path);
		        System.out.println("done");
		    } catch (IOException e) {
		        e.printStackTrace();
		        // Handle the exception appropriately
		    }
		
	}
}
