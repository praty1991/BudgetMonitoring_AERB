package com.aerb.docs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aerb.docs.entity.SupportingFile;

public interface SupportingFileRepository extends JpaRepository<SupportingFile, Long> {}
