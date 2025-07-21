package com.aerb.docs.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aerb.docs.entity.Document;
import com.aerb.docs.entity.SupportingFile;
import com.aerb.docs.repository.DocumentRepository;
import com.aerb.docs.repository.SupportingFileRepository;

import jakarta.transaction.Transactional;

@Service
public class DocumentService {

    @Autowired private DocumentRepository docRepo;
    @Autowired private SupportingFileRepository fileRepo;

    @Transactional
    public void createDocument(String name, String subject, String enteredBy, MultipartFile mainFile, List<MultipartFile> supportingFiles) throws IOException {
        Document doc = new Document();
        doc.setName(name);
        doc.setSubject(subject);
        doc.setEnteredBy(enteredBy);
        doc.setModifiedDate(LocalDateTime.now());
        doc.setMainFileName(mainFile.getOriginalFilename());
        doc.setMainFileType(mainFile.getContentType());
        doc.setMainFileData(mainFile.getBytes());

        for (MultipartFile mf : supportingFiles) {
            SupportingFile sf = new SupportingFile();
            sf.setFileName(mf.getOriginalFilename());
            sf.setFileType(mf.getContentType());
            sf.setUploadDate(LocalDateTime.now());
            sf.setFileData(mf.getBytes());
            sf.setDocument(doc);
            doc.getSupportingFiles().add(sf);
        }

        docRepo.save(doc);
    }

    public List<Document> search(String query) {
        return docRepo.findByNameContainingIgnoreCaseOrSubjectContainingIgnoreCase(query, query);
    }

    public Optional<SupportingFile> getSupportingFile(Long id) {
        return fileRepo.findById(id);
    }

    public Optional<Document> getDocument(Long id) {
        return docRepo.findById(id);
    }

    public void deleteSupportingFile(Long fileId) {
        fileRepo.deleteById(fileId);
    }

    public void updateMetadata(Long id, String name, String subject, MultipartFile mainFile) throws IOException {
        Document doc = docRepo.findById(id).orElseThrow();
        doc.setName(name);
        doc.setSubject(subject);
        doc.setModifiedDate(LocalDateTime.now());
        if (mainFile != null && !mainFile.isEmpty()) {
            doc.setMainFileName(mainFile.getOriginalFilename());
            doc.setMainFileType(mainFile.getContentType());
            doc.setMainFileData(mainFile.getBytes());
        }
        docRepo.save(doc);
    }

    public void addSupportingFiles(Long id, List<MultipartFile> files) throws IOException {
        Document doc = docRepo.findById(id).orElseThrow();
        for (MultipartFile mf : files) {
            SupportingFile sf = new SupportingFile();
            sf.setFileName(mf.getOriginalFilename());
            sf.setFileType(mf.getContentType());
            sf.setUploadDate(LocalDateTime.now());
            sf.setFileData(mf.getBytes());
            sf.setDocument(doc);
            doc.getSupportingFiles().add(sf);
        }
        docRepo.save(doc);
    }
}
