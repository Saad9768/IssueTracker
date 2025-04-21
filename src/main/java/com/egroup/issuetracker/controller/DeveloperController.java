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

import com.egroup.issuetracker.dto.DeveloperDTO;
import com.egroup.issuetracker.service.DeveloperService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/developers")
@AllArgsConstructor
public class DeveloperController {

	private final DeveloperService developerService;

	@GetMapping
	public ResponseEntity<List<DeveloperDTO>> getAllDevelopers() {
		log.info("GET request received: Fetch all developers");
		try {
			List<DeveloperDTO> developers = developerService.getAllDevelopers();
			log.debug("Fetched {} developers", developers.size());
			return new ResponseEntity<>(developers, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to fetch all developers", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<DeveloperDTO> getDeveloperById(@PathVariable Long id) {
		log.info("GET request received: Fetch developer by ID {}", id);
		try {
			DeveloperDTO developer = developerService.getDeveloperById(id);
			log.debug("Fetched developer: {}", developer);
			return new ResponseEntity<>(developer, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to fetch developer with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<DeveloperDTO> addDeveloper(@Valid @RequestBody DeveloperDTO developer) {
		log.info("POST request received: Add new developer with name {}", developer.getName());
		try {
			DeveloperDTO savedDeveloper = developerService.addDeveloper(developer);
			log.debug("Developer added with ID: {}", savedDeveloper.getId());
			return new ResponseEntity<>(savedDeveloper, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Failed to add developer: {}", developer, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<DeveloperDTO> updateDeveloper(@PathVariable Long id,
			@Valid @RequestBody DeveloperDTO updatedDeveloper) {
		log.info("PUT request received: Update developer with ID {}", id);
		try {
			DeveloperDTO updated = developerService.updateDeveloper(id, updatedDeveloper);
			log.debug("Developer updated: {}", updated);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to update developer with ID {}: {}", id, updatedDeveloper, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
		log.info("DELETE request received: Delete developer with ID {}", id);
		try {
			if (developerService.deleteDeveloper(id)) {
				log.info("Developer with ID {} deleted successfully", id);
				return ResponseEntity.noContent().build();
			} else {
				log.warn("Developer with ID {} not found for deletion", id);
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			log.error("Failed to delete developer with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
