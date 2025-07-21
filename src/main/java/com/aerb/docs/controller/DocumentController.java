package com.aerb.docs.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aerb.docs.entity.Document;
import com.aerb.docs.entity.SupportingFile;
import com.aerb.docs.service.DocumentService;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired private DocumentService documentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @RequestParam String name,
        @RequestParam String subject,
        @RequestParam String enteredBy,
        @RequestParam("mainFile") MultipartFile mainFile,
        @RequestParam("supportingFiles") List<MultipartFile> supportingFiles
    ) throws IOException {
        documentService.createDocument(name, subject, enteredBy, mainFile, supportingFiles);
        return ResponseEntity.ok("Document uploaded");
    }

    @PostMapping("/{id}/supporting-files")
    public ResponseEntity<String> addSupportingFiles(
        @PathVariable Long id,
        @RequestParam("supportingFiles") List<MultipartFile> files
    ) throws IOException {
        documentService.addSupportingFiles(id, files);
        return ResponseEntity.ok("Files added");
    }

    @DeleteMapping("/supporting-files/{fileId}")
    public ResponseEntity<String> deleteSupportingFile(@PathVariable Long fileId) {
        documentService.deleteSupportingFile(fileId);
        return ResponseEntity.ok("File deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMetadata(
        @PathVariable Long id,
        @RequestParam String name,
        @RequestParam String subject,
        @RequestParam(value = "mainFile", required = false) MultipartFile mainFile
    ) throws IOException {
        documentService.updateMetadata(id, name, subject, mainFile);
        return ResponseEntity.ok("Document updated");
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String q) {
        return documentService.search(q);
    }

    @GetMapping("/{id}/main-file/download")
    public ResponseEntity<byte[]> downloadMainFile(@PathVariable Long id) {
        Document doc = documentService.getDocument(id).orElseThrow();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getMainFileName() + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(doc.getMainFileData());
    }

    @GetMapping("/supporting-files/{fileId}/download")
    public ResponseEntity<byte[]> downloadSupportingFile(@PathVariable Long fileId) {
        SupportingFile file = documentService.getSupportingFile(fileId).orElseThrow();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(file.getFileData());
    }
}
