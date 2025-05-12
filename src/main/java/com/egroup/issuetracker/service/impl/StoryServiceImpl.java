package com.egroup.issuetracker.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egroup.issuetracker.dto.StoryDTO;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.entity.Story;
import com.egroup.issuetracker.exception.ResourceNotFoundException;
import com.egroup.issuetracker.mapper.DtoConverter;
import com.egroup.issuetracker.repository.StoryRepository;
import com.egroup.issuetracker.service.DeveloperService;
import com.egroup.issuetracker.service.StoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StoryServiceImpl implements StoryService {

	private static final Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

	private final StoryRepository storyRepository;
	private final DtoConverter dtoConverter;
	private final DeveloperService developerService;

	private Story fetchStoryByStoryId(long id) {
		return storyRepository.findById(id).orElseThrow(() -> {
			logger.warn("Story not found for ID: {}", id);
			return new ResourceNotFoundException("Story not found for story id :: " + id);
		});
	}

	@Override
	public StoryDTO getStoryById(long id) {
		logger.info("Fetching story with ID: {}", id);
		Story story = fetchStoryByStoryId(id);
		logger.debug("Fetched story: {}", story);
		return dtoConverter.convert(story, StoryDTO.class);
	}

	@Override
	public List<StoryDTO> getAllStories() {
		logger.info("Fetching all stories");
		List<Story> stories = storyRepository.findAll();
		logger.debug("Fetched {} stories", stories.size());
		return dtoConverter.convertToEntityList(stories, StoryDTO.class);
	}

	@Override
	public StoryDTO addStory(StoryDTO storyDto) {
		logger.info("Adding new story with title: {}", storyDto.getTitle());
		Story story = dtoConverter.convert(storyDto, Story.class);
		updateDeveloper(storyDto, story);
		Story saved = storyRepository.save(story);
		logger.debug("Story added with ID: {}", saved.getId());
		return dtoConverter.convert(saved, StoryDTO.class);
	}

	private void updateDeveloper(StoryDTO storyDto, Story story) {
		if (storyDto.getDeveloperId() != 0) {
			Developer developer = developerService.fetchDeveloperById(storyDto.getDeveloperId());
			story.setAssignee(developer);
		}
	}

	@Override
	public StoryDTO updateStory(long id, StoryDTO updatedStory) {
		logger.info("Updating story with ID: {}", id);

		Story story = fetchStoryByStoryId(id);

		updateDeveloper(updatedStory, story);

		if (updatedStory.getTitle() != null) {
			story.setTitle(updatedStory.getTitle());
		}

		if (updatedStory.getStatus() != null) {
			story.setStatus(updatedStory.getStatus());
		}

		if (updatedStory.getEstimatedPoints() != null) {
			story.setEstimatedPoints(updatedStory.getEstimatedPoints());
		}

		if (updatedStory.getDescription() != null) {
			story.setDescription(updatedStory.getDescription());
		}
		Story updated = storyRepository.save(story);
		logger.debug("Story updated: {}", updated);
		return dtoConverter.convert(updated, StoryDTO.class);
	}

	private boolean checkStoryExists(long id) {
		boolean exists = storyRepository.existsById(id);
		logger.debug("Check if story exists for ID {}: {}", id, exists);
		return exists;
	}

	@Override
	public boolean deleteStory(long id) {
		logger.info("Deleting story with ID: {}", id);
		if (!checkStoryExists(id)) {
			logger.warn("Story with ID {} not found. Cannot delete.", id);
			return false;
		}
		storyRepository.deleteById(id);
		logger.info("Story with ID {} deleted successfully", id);
		return true;
	}
}
