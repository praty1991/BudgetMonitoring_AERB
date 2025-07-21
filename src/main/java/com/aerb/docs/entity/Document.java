package com.aerb.docs.entity;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Document {
    @Id @GeneratedValue
    private Long id;
    
	private String name;
    private String subject;
    private String enteredBy;
    private LocalDateTime modifiedDate;

    private String mainFileName;
    private String mainFileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] mainFileData;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportingFile> supportingFiles = new ArrayList<>();

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMainFileName() {
		return mainFileName;
	}

	public void setMainFileName(String mainFileName) {
		this.mainFileName = mainFileName;
	}

	public String getMainFileType() {
		return mainFileType;
	}

	public void setMainFileType(String mainFileType) {
		this.mainFileType = mainFileType;
	}

	public byte[] getMainFileData() {
		return mainFileData;
	}

	public void setMainFileData(byte[] mainFileData) {
		this.mainFileData = mainFileData;
	}

	public List<SupportingFile> getSupportingFiles() {
		return supportingFiles;
	}

	public void setSupportingFiles(List<SupportingFile> supportingFiles) {
		this.supportingFiles = supportingFiles;
	}


}
