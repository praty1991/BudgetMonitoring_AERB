package com.aerb.docs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aerb.docs.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByNameContainingIgnoreCaseOrSubjectContainingIgnoreCase(String name, String subject);
}

