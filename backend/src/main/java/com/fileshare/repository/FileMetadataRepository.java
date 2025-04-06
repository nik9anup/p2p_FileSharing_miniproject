package com.fileshare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fileshare.model.FileMetadata;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
    List<FileMetadata> findByRoom(String room);
}
