package com.aerb.budget.controller;

import com.aerb.budget.dto.ProcessDTO;
import com.aerb.budget.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processes")
public class ProcessController {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    private ProcessService processService;

    /**
     * GET /api/processes/roots
     * Fetches top-level processes (those with null parent)
     * 
     * Sample Response:
     * [
     *   { "id": 1, "name": "Director A", "parentId": null },
     *   { "id": 2, "name": "Director B", "parentId": null }
     * ]
     */
    @GetMapping("/roots")
    public List<ProcessDTO> getTopLevel() {
        logger.info("Fetching all top-level processes");
        List<ProcessDTO> processes = processService.getTopLevelProcesses();
        logger.debug("Top-level processes found: {}", processes.size());
        return processes;
    }

    /**
     * GET /api/processes/children/{parentName}
     * Fetches children processes under the given parent
     * 
     * Sample URL: /api/processes/children/abc
     * 
     * Sample Response:
     * [
     *   { "id": 3, "name": "Manager A1", "parentId": 1 },
     *   { "id": 4, "name": "Manager A2", "parentId": 1 }
     * ]
     */
    @GetMapping("/children-by-name/{parentName}")
    public ResponseEntity<List<ProcessDTO>> getChildrenByParentName(@PathVariable String parentName) {
        logger.info("Fetching children of process with name '{}'", parentName);
        List<ProcessDTO> children = processService.getChildrenByParentName(parentName);
        logger.debug("Found {} child processes for parent '{}'", children.size(), parentName);
        return ResponseEntity.ok(children);
    }
    
    
    /**
     * GET /api/processes/all-path
     * 
     * Fetches all hierarchical paths of active processes in the format:
     * "Director -> Manager -> Team Lead -> Employee"
     *
     * Each path represents a full branch from a top-level process to a leaf node.
     * 
     * Sample Response:
     * [
     *   "Director A -> Manager X -> Team Lead M -> Employee 1",
     *   "Director A -> Manager X -> Team Lead M -> Employee 2",
     *   "Director B -> Manager Y"
     * ]
     */
    @GetMapping("/all-path")
    public List<String> getAllPathStrings() {
        return processService.getAllPathStrings();
    }


    /**
     * POST /api/processes?parentId={optional}
     * Adds a new process (with or without parent)
     * 
     * Sample Request Body:
     * {
     *   "name": "Team Lead A1"
     * }
     * 
     * Sample Response:
     * {
     *   "id": 5,
     *   "name": "Team Lead A1",
     *   "parentId": 3
     * }
     */
    @PostMapping
    public ProcessDTO create(@RequestBody ProcessDTO dto,
                             @RequestParam(required = false) Long parentId) {
        logger.info("Creating new process: {}, parentId: {}", dto.getName(), parentId);
        ProcessDTO created = processService.createProcess(dto, parentId);
        logger.debug("Created process: {}", created);
        return created;
    }

    /**
     * PUT /api/processes/{id}
     * Updates an existing process by ID
     * 
     * Sample URL: /api/processes/5
     * 
     * Sample Request Body:
     * {
     *   "name": "Updated Team Lead A1"
     * }
     * 
     * Sample Response:
     * {
     *   "id": 5,
     *   "name": "Updated Team Lead A1",
     *   "parentId": 3
     * }
     */
    @PutMapping("/{id}")
    public ProcessDTO update(@PathVariable Long id,
                             @RequestBody ProcessDTO dto) {
        logger.info("Updating process with ID {}: new name = {}", id, dto.getName());
        ProcessDTO updated = processService.updateProcess(id, dto);
        logger.debug("Updated process: {}", updated);
        return updated;
    }

    /**
     * DELETE /api/processes/{id}
     * Soft-deletes a process by ID
     * 
     * Sample URL: /api/processes/5
     * 
     * No Response Body
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Deleting process with ID {}", id);
        processService.deleteProcess(id);
        logger.debug("Process with ID {} deleted", id);
    }
}
