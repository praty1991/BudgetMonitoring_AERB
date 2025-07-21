package com.aerb.docs.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDTO {
    private String name;
    private String subject;
    private String enteredBy;
    private Long documentTypeId;
    private List<MultipartFile> files;
}
