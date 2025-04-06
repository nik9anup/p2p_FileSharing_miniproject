package com.fileshare.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileshare.model.FileMetadata;
import com.fileshare.repository.FileMetadataRepository;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {

    @Autowired
    private GridFsOperations gridFsOperations;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public String uploadFile(MultipartFile file, String room) throws IOException {
        ObjectId fileId = gridFsOperations.store(file.getInputStream(), file.getOriginalFilename());
        FileMetadata metadata = new FileMetadata(room, file.getOriginalFilename());
        fileMetadataRepository.save(metadata);
        return fileId.toString();
    }

    public List<FileMetadata> getFilesByRoom(String room) {
        return fileMetadataRepository.findByRoom(room);
    }

    public GridFsResource downloadFile(String id) {
        GridFSFile file = gridFsOperations.findOne(query(where("_id").is(new ObjectId(id))));
        return gridFsOperations.getResource(file);
    }
}
