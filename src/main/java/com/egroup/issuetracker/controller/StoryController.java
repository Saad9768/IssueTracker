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

import com.egroup.issuetracker.dto.StoryDTO;
import com.egroup.issuetracker.dto.WeekPlan;
import com.egroup.issuetracker.exception.ResourceNotFoundException;
import com.egroup.issuetracker.service.PlanningService;
import com.egroup.issuetracker.service.StoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/stories")
@AllArgsConstructor
public class StoryController {

	private final StoryService storyService;
	private final PlanningService planningService;

	@GetMapping
	public ResponseEntity<?> getAllStories() {
		try {
			List<StoryDTO> stories = storyService.getAllStories();
			log.info("Fetched {} stories", stories.size());
			return ResponseEntity.ok(stories);
		} catch (Exception e) {
			log.error("Failed to fetch stories", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching stories.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStoryById(@PathVariable long id) {
		try {
			StoryDTO story = storyService.getStoryById(id);
			log.info("Fetched story with id: {}", id);
			return ResponseEntity.ok(story);
		} catch (ResourceNotFoundException e) {
			log.warn("Story not found for id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			log.error("Failed to fetch story with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching the story.");
		}
	}

	@PostMapping
	public ResponseEntity<?> addStory(@Valid @RequestBody StoryDTO storyDto) {
		try {
			StoryDTO savedStory = storyService.addStory(storyDto);
			log.info("Story created with id: {}", savedStory.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(savedStory);
		} catch (Exception e) {
			log.error("Failed to create story", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the story.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStory(@PathVariable Long id, @Valid @RequestBody StoryDTO updatedStory) {
		try {
			StoryDTO updated = storyService.updateStory(id, updatedStory);
			log.info("Updated story with id: {}", id);
			return ResponseEntity.ok(updated);
		} catch (ResourceNotFoundException e) {
			log.warn("Story not found for update, id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			log.error("Failed to update story with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while updating the story.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStory(@PathVariable long id) {
		try {
			if (storyService.deleteStory(id)) {
				log.info("Deleted story with id: {}", id);
				return ResponseEntity.noContent().build();
			} else {
				log.warn("Attempted to delete non-existent story with id: {}", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found.");
			}
		} catch (Exception e) {
			log.error("Failed to delete story with id: {}", id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the story.");
		}
	}

	@GetMapping("/plan")
	public ResponseEntity<?> getPlan() {
		try {
			List<WeekPlan> plan = planningService.createPlan();
			log.info("Generated plan with {} weeks", plan.size());
			return ResponseEntity.ok(plan);
		} catch (Exception e) {
			log.error("Failed to generate plan", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while generating the plan.");
		}
	}
}
