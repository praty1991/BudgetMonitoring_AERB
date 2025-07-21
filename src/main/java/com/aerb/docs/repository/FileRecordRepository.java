package com.aerb.docs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aerb.docs.entity.FileRecord;

public interface FileRecordRepository extends JpaRepository<FileRecord, Long> {}

