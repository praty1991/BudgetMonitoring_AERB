package com.aerb.budget.controller;

import com.aerb.budget.entity.Division;
import com.aerb.budget.service.DivisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/budget")
public class DivisionController {

    private static final Logger logger = LoggerFactory.getLogger(DivisionController.class);

    @Autowired
    private DivisionService divisionService;

    @GetMapping("/divisions")
    public List<String> getDivisions() {
        logger.info("GET /api/divisions called");

        List<String> divisions = divisionService.getDivisions();

        logger.info("Returned {} divisions", divisions.size());
        return divisions;
    }
    
    //for setting
    @GetMapping("/divAll")
    public List<Division> getAllDivisions() {
        logger.info("GET /api/divisions called");

        List<Division> divisions = divisionService.getAllDivisions();

        logger.info("Returned {} divisions", divisions.size());
        return divisions;
    }

    @PostMapping
    public Division createDivision(@RequestBody Division division) {
        logger.info("POST /api/divisions - Creating Division: {}", division.getName());

        return divisionService.createDivision(division);
    }

    @PutMapping("/{id}")
    public Division updateDivision(@PathVariable Long id, @RequestBody Division division) {
        logger.info("PUT /api/divisions/{} - Updating Division", id);

        return divisionService.updateDivision(id, division);
    }

    @DeleteMapping("/{id}")
    public void deleteDivision(@PathVariable Long id) {
        logger.info("DELETE /api/divisions/{} - Deleting Division", id);

        divisionService.deleteDivision(id);
    }
}
