package org.mohsoft.controller;

import org.mohsoft.model.User;
import org.mohsoft.model.UserContent;
import org.mohsoft.repository.UserContentRepository;
import org.mohsoft.repository.UserRepository;
import org.mohsoft.service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class UserContentController {

	@Autowired
	private UserContentRepository userContentRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private FileSystemStorageService fileStorageService;
    
   

    @GetMapping("/userContent")
    public String userContent(Model model) {
        // Get the current logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the files associated with the user
        List<UserContent> userContents = userContentRepository.findAllByUserId(user.getId());
        model.addAttribute("files", userContents);
        
        return "user/userContent";
    }

    @GetMapping("/delete/{filename:.+}")
    public String deleteFile(@PathVariable String filename, Authentication authentication) {
    
        // Get the current logged-in user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Load the UserContent object from the database using the filename and the current user's ID
        UserContent userContent = userContentRepository.findByFileNameAndUserId(filename, user.getId())
                .orElseThrow(() -> new RuntimeException("File not found " + filename));
     
        // Delete the file from the storage
        fileStorageService.deleteFile(userContent);
       
        // Delete the UserContent from the database
        userContentRepository.delete(userContent);
        
        // Redirect back to the user content page
        return "redirect:/userContent";
    }

    
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") List<MultipartFile> files, Authentication authentication) {
        // Get the current logged-in user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        for (MultipartFile file : files) {
            

       
        // Create the UserContent object
        UserContent userContent = new UserContent(); 
        userContent.setFileType(file.getContentType());
        userContent.setFileSize(file.getSize());
        userContent.setUploadTime(LocalDateTime.now());
        userContent.setUser(user);
       userContent.setFileName("temp");
        userContent.setFilePath("temp");
       userContentRepository.save(userContent);
       
        // Store the uploaded file
        Path filePath = fileStorageService.storeFile(file, userContent);

        // Update the UserContent object with the file path
        userContent.setFilePath(filePath.toString());
        userContentRepository.save(userContent);
        }

        return "redirect:/userContent";
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request, Authentication authentication) {
        // Get the current logged-in user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Load the UserContent object from the database using the filename and the current user's ID
        UserContent userContent = userContentRepository.findByFileNameAndUserId(filename, user.getId())
                .orElseThrow(() -> new RuntimeException("File not found " + filename));

        
        // Load the file as a resource
        Resource resource = fileStorageService.loadFileAsResource(filename, userContent);

        // Determine the content type of the file
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            // Do nothing, fallback to the default content type
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

