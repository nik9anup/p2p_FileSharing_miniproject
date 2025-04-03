package com.fileshare.repository;

import com.fileshare.model.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FileRepository extends MongoRepository<FileMetadata, String> {
    List<FileMetadata> findByRoom(String room);
}
