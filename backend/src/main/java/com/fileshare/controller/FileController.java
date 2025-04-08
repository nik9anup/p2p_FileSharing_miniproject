package com.fileshare.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileshare.model.FileMetadata;
import com.fileshare.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("room") String room) throws IOException {
        String id = fileService.uploadFile(file, room);
        return ResponseEntity.ok("Uploaded with ID: " + id);
    }

    @GetMapping("/{room}")
    public List<Map<String, Object>> listFiles(@PathVariable String room) {
        List<FileMetadata> files = fileService.getFilesByRoom(room);
        return files.stream().map(file -> {
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("id", file.getId());
            fileMap.put("originalFileName", file.getOriginalFileName());
            fileMap.put("room", file.getRoom());
            return fileMap;
        }).collect(Collectors.toList());
    }


    @GetMapping("/download/{id}")
public ResponseEntity<InputStreamResource> download(@PathVariable String id) throws IOException {
    System.out.println("Download request received for file ID: " + id);
    try {
        GridFsResource resource = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(resource.getInputStream()));
    } catch (Exception e) {
        System.err.println("Download failed for ID: " + id);
        e.printStackTrace(); // log full stack trace
        throw e;
    }
}

}
