package com.vaccineadminsystem.service;

import com.vaccineadminsystem.exception.ImageNotFoundException;
import com.vaccineadminsystem.exception.SaveImageException;
import com.vaccineadminsystem.util.ErrorMess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Logger log = LoggerFactory.getLogger(FileStorageService.class);
    private final Path fileStorageLocation;


    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void store(MultipartFile file, String fileURI) throws SaveImageException {
        try {
            Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(fileURI));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new SaveImageException(e.getMessage());
        }
    }

    public Resource loadFile(String filename) throws ImageNotFoundException {
        try {
            Path file = fileStorageLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ImageNotFoundException(ErrorMess.IMAGE_NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ImageNotFoundException(ErrorMess.IMAGE_NOT_FOUND);
        }
    }
}
