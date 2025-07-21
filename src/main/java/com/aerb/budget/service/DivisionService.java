package com.aerb.budget.service;

import com.aerb.budget.entity.Division;
import com.aerb.budget.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DivisionService {

    private static final Logger logger = LoggerFactory.getLogger(DivisionService.class);

    @Autowired
    private DivisionRepository divisionRepository;

    // ✅ Get all divisions
    public List<String> getDivisions() {
        logger.info("Fetching all divisions from the database");

        List<Division> divisions = divisionRepository.findAll();

        List<String> divisionNames = divisions.stream()
                .map(Division::getName)
                .collect(Collectors.toList());

       logger.info("Returning list of division names: {}", divisionNames);

       return divisionNames;
    }
    
    // ✅ Get all divisions (full object)
    public List<Division> getAllDivisions() {
        logger.info("Fetching all divisions from the database");

        List<Division> divisions = divisionRepository.findAll();

        logger.debug("Fetched {} divisions from DB", divisions.size());
        return divisions;
    }

    // ✅ Create a new division
    public Division createDivision(Division division) {
        logger.info("Creating division with name: {}", division.getName());

        if (divisionRepository.existsByName(division.getName())) {
            logger.warn("Division '{}' already exists. Aborting.", division.getName());
            throw new RuntimeException("Division already exists");
        }

        Division saved = divisionRepository.save(division);
        logger.info("Division created with ID: {}", saved.getId());
        return saved;
    }

    // ✅ Update division
    public Division updateDivision(Long id, Division updatedDivision) {
        logger.info("Updating division ID: {}", id);

        Division existing = divisionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Division with ID {} not found", id);
                    return new RuntimeException("Division not found");
                });

        existing.setName(updatedDivision.getName());
        Division saved = divisionRepository.save(existing);

        logger.info("Division updated: ID={} Name={}", saved.getId(), saved.getName());
        return saved;
    }

    // ✅ Delete division
    public void deleteDivision(Long id) {
        logger.info("Deleting division with ID: {}", id);

        if (!divisionRepository.existsById(id)) {
            logger.warn("Cannot delete. Division ID {} does not exist", id);
            throw new RuntimeException("Division not found");
        }

        divisionRepository.deleteById(id);
        logger.info("Division deleted successfully.");
    }

    // ✅ Still available if needed by older consumers
    public List<String> getAllDivisionNames() {
        logger.info("Fetching all division names from the database");

        List<Division> divisions = divisionRepository.findAll();
        List<String> divisionNames = divisions.stream()
                                              .map(Division::getName)
                                              .collect(Collectors.toList());

        logger.info("Returning list of division names: {}", divisionNames);
        return divisionNames;
    }
}
