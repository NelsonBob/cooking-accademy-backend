package com.esgi.pa.api.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.esgi.pa.domain.services.util.UtilService;

import lombok.RequiredArgsConstructor;

/**
 * Contient les routes lié au traitement de fichiers
 */
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileResource {

    private static final String UPLOAD_DIR = "src/main/resources/files/";

    /**
     * Permet le stockage d'un fichier
     *
     * @param audio contient les données du fichier
     * @return le path vers le fichier
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(MultipartFile audio) {
        try {
            String fileName =
                UUID.randomUUID().toString() + "_" + audio.getOriginalFilename();
            return UtilService.saveFile(audio, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file");
        }
    }

    /**
     * Permet la récupération d'un fichier
     *
     * @param fileName le nom du fichier à récupérer
     * @return le fichier
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                byte[] fileBytes = Files.readAllBytes(filePath);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(
                    MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                );
                headers.setContentDisposition(
                    ContentDisposition
                        .attachment()
                        .filename(resource.getFilename())
                        .build()
                );
                headers.setContentLength(fileBytes.length);

                return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    /**
     * Permet de mettre à jour stockage d'un fichier
     *
     * @param audio contient les données du fichier
     * @param lastFileName contient l'url du dernier fichier
     * @return le path vers le fichier
     */
    @PostMapping(value = "/upload/{lastFileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateFile(MultipartFile audio, @PathVariable String lastFileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + lastFileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // Remove the file after it's been downloaded
                Files.delete(filePath);
            }

            String fileName =
                    UUID.randomUUID().toString() + "_" + audio.getOriginalFilename();
            return UtilService.saveFile(audio, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file");
        }
    }

}
