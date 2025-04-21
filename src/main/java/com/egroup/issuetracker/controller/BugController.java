package com.egroup.issuetracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egroup.issuetracker.dto.BugDTO;
import com.egroup.issuetracker.exception.ResourceNotFoundException;
import com.egroup.issuetracker.service.BugService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/bugs")
@AllArgsConstructor
public class BugController {

	private final BugService bugService;

	@GetMapping
	public ResponseEntity<?> getAllBugs() {
		try {
			List<BugDTO> bugs = bugService.getAllBugs();
			log.info("Fetched {} bugs", bugs.size());
			return ResponseEntity.ok(bugs);
		} catch (Exception e) {
			log.error("Failed to fetch bugs", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching bugs.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getBugById(@PathVariable Long id) {
		try {
			BugDTO bug = bugService.getBugById(id);
			log.info("Fetched bug with id: {}", id);
			return ResponseEntity.ok(bug);
		} catch (ResourceNotFoundException e) {
			log.warn("Bug not found with id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			log.error("Failed to fetch bug with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching the bug.");
		}
	}

	@PostMapping
	public ResponseEntity<?> addBug(@Valid @RequestBody BugDTO bug) {
		try {
			BugDTO savedBug = bugService.addBug(bug);
			log.info("Bug created with id: {}", savedBug.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(savedBug);
		} catch (Exception e) {
			log.error("Failed to create bug", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the bug.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBug(@PathVariable Long id, @Valid @RequestBody BugDTO updatedBug) {
		try {
			BugDTO updated = bugService.updateBug(id, updatedBug);
			log.info("Updated bug with id: {}", id);
			return ResponseEntity.ok(updated);
		} catch (ResourceNotFoundException e) {
			log.warn("Bug not found for update, id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			log.error("Failed to update bug with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while updating the bug.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBug(@PathVariable Long id) {
		try {
			if (bugService.deleteBug(id)) {
				log.info("Deleted bug with id: {}", id);
				return ResponseEntity.noContent().build();
			} else {
				log.warn("Attempted to delete non-existent bug with id: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bug not found.");
			}
		} catch (Exception e) {
			log.error("Failed to delete bug with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the bug.");
		}
	}
}
