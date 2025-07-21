package com.aerb.budget.service;

import com.aerb.budget.dto.ProcessDTO;
import com.aerb.budget.entity.Process;
import com.aerb.budget.exception.ResourceNotFoundException;
import com.aerb.budget.repository.ProcessRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing organizational processes in a hierarchical structure.
 */
@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    @Autowired
    private ProcessRepository processRepository;

    /**
     * Converts a Process entity to a ProcessDTO.
     *
     * @param process          the Process entity
     * @param includeChildren  whether to include child processes
     * @return a ProcessDTO representation of the entity
     */
    public ProcessDTO convertToDTO(Process process, boolean includeChildren) {
        if (process == null) return null;

        logger.debug("Converting entity to DTO for process ID: {}", process.getId());

        List<ProcessDTO> childrenDTOs = null;
        if (includeChildren && process.getChildren() != null) {
            childrenDTOs = process.getChildren().stream()
                    .filter(Process::getIsActive)
                    .map(child -> convertToDTO(child, false))
                    .collect(Collectors.toList());
        }

        return new ProcessDTO(
                process.getId(),
                process.getCode(),
                process.getName(),
                process.getLevel()
        );
    }

    /**
     * Converts a ProcessDTO to a Process entity with default timestamps and active state.
     *
     * @param dto the ProcessDTO
     * @return the Process entity
     */
    public Process convertToEntity(ProcessDTO dto) {
        logger.debug("Converting DTO to entity: {}", dto);
        Process process = new Process();
        process.setId(dto.getId());
        process.setCode(dto.getCode());
        process.setName(dto.getName());
        process.setLevel(dto.getLevel());
        process.setIsActive(true);
        process.setCreatedAt(LocalDateTime.now());
        process.setUpdatedAt(LocalDateTime.now());
        return process;
    }

    /**
     * Retrieves all top-level (parentless) processes that are active.
     *
     * @return a list of top-level ProcessDTOs
     */
    public List<ProcessDTO> getTopLevelProcesses() {
        logger.info("Fetching all top-level processes");
        List<ProcessDTO> list = processRepository.findByParentIsNull()
                .stream()
                .filter(Process::getIsActive)
                .map(process -> convertToDTO(process, false))
                .collect(Collectors.toList());
        logger.debug("Found {} top-level processes", list.size());
        return list;
    }

    /**
     * Retrieves active child processes of a given parent by name.
     *
     * @param parentName the name of the parent process
     * @return a list of ProcessDTOs that are children of the parent
     * @throws ResourceNotFoundException if the parent process is not found or inactive
     */
    public List<ProcessDTO> getChildrenByParentName(String parentName) {
        logger.info("Fetching children for parent name: {}", parentName);

        Process parent = processRepository.findByNameIgnoreCase(parentName)
                .filter(Process::getIsActive)
                .orElseThrow(() -> {
                    logger.error("Parent process '{}' not found or inactive", parentName);
                    return new ResourceNotFoundException("Parent process '" + parentName + "' not found or inactive");
                });

        List<ProcessDTO> children = parent.getChildren().stream()
                .filter(Process::getIsActive)
                .map(child -> convertToDTO(child, false))
                .collect(Collectors.toList());

        logger.debug("Found {} children under parent '{}'", children.size(), parentName);
        return children;
    }
   
    /**
     * Retrieves all hierarchical path strings of active processes starting from top-level (root) processes.
     * <p>
     * Each path is constructed in the format: "Director -> Manager -> Team Lead -> Employee", showing the
     * entire chain from the root to each leaf node.
     *
     * @return a list of formatted path strings for all active process hierarchies
     * @throws BadRequestException if no active root processes are found
     */
    public List<String> getAllPathStrings() {
    	logger.info("Fetching all active root-level processes to build path strings.");

        List<Process> roots = processRepository.findByParentIsNullAndIsActiveTrue();
        if (roots.isEmpty()) {
        	logger.warn("No active top-level processes found.");
            throw new ResourceNotFoundException("No active top-level processes available.");
        }

        List<String> allPaths = new ArrayList<>();
        for (Process root : roots) {
            buildPathStrings(root, "", allPaths);
        }

        logger.info("Successfully built {} hierarchical path strings.", allPaths.size());
        return allPaths;
    }

    /**
     * Recursively builds hierarchical path strings for a given process and its active children.
     * <p>
     * This method is internally used by {@link #getAllPathStrings()} to traverse the process tree.
     * If the current process has no active children, its path is added to the list.
     *
     * @param current    the current process node being processed
     * @param pathSoFar  the accumulated path string built so far
     * @param allPaths   the list collecting all completed path strings
     */
    private void buildPathStrings(Process current, String pathSoFar, List<String> allPaths) {
        if (!current.getIsActive()) {
        	logger.debug("Skipping inactive process: {}", current.getName());
            return;
        }

        String currentPath = pathSoFar.isEmpty()
            ? current.getName()
            : pathSoFar + " -> " + current.getName();

        List<Process> activeChildren = current.getChildren().stream()
            .filter(Process::getIsActive)
            .collect(Collectors.toList());

        if (activeChildren.isEmpty()) {
        	logger.debug("Leaf path found: {}", currentPath);
            allPaths.add(currentPath);
        } else {
            for (Process child : activeChildren) {
                buildPathStrings(child, currentPath, allPaths);
            }
        }
    }

    /**
     * Creates a new process with optional parent ID, and assigns hierarchy level.
     *
     * @param dto      the ProcessDTO containing the new process data
     * @param parentId the ID of the parent process (optional)
     * @return the created ProcessDTO
     * @throws ResourceNotFoundException if the parent process is not found or inactive
     */
    @Transactional
    public ProcessDTO createProcess(ProcessDTO dto, Long parentId) {
        logger.info("Creating new process: {}, parentId: {}", dto.getName(), parentId);

        Process process = convertToEntity(dto);

        if (parentId != null) {
            Process parent = processRepository.findById(parentId)
                    .filter(Process::getIsActive)
                    .orElseThrow(() -> {
                        logger.error("Parent process not found or inactive for ID: {}", parentId);
                        return new ResourceNotFoundException("Parent process with ID " + parentId + " not found or inactive");
                    });
            process.setParent(parent);
            process.setLevel(parent.getLevel() + 1);
        } else {
            process.setParent(null);
            process.setLevel(0);
        }

        Process saved = processRepository.save(process);
        logger.info("Process created with ID: {}", saved.getId());
        return convertToDTO(saved, false);
    }

    /**
     * Updates an existing process's code and name.
     *
     * @param id  the ID of the process to update
     * @param dto the updated data in ProcessDTO
     * @return the updated ProcessDTO
     * @throws ResourceNotFoundException if the process is not found or inactive
     */
    @Transactional
    public ProcessDTO updateProcess(Long id, ProcessDTO dto) {
        logger.info("Updating process ID: {}", id);

        Process existing = processRepository.findById(id)
                .filter(Process::getIsActive)
                .orElseThrow(() -> {
                    logger.error("Process not found or inactive for ID: {}", id);
                    return new ResourceNotFoundException("Process with ID " + id + " not found or inactive");
                });

        existing.setCode(dto.getCode());
        existing.setName(dto.getName());
        existing.setUpdatedAt(LocalDateTime.now());

        Process updated = processRepository.save(existing);
        logger.info("Process updated for ID: {}", updated.getId());
        return convertToDTO(updated, false);
    }

    /**
     * Performs a soft delete of a process by marking it as inactive.
     *
     * @param id the ID of the process to delete
     * @throws ResourceNotFoundException if the process is not found or already inactive
     */
    @Transactional
    public void deleteProcess(Long id) {
        logger.info("Soft deleting process ID: {}", id);

        Process existing = processRepository.findById(id)
                .filter(Process::getIsActive)
                .orElseThrow(() -> {
                    logger.error("Process not found or already inactive for ID: {}", id);
                    return new ResourceNotFoundException("Process with ID " + id + " not found or already inactive");
                });

        existing.setIsActive(false);
        existing.setUpdatedAt(LocalDateTime.now());
        processRepository.save(existing);

        logger.info("Process marked as inactive for ID: {}", id);
    }
}
