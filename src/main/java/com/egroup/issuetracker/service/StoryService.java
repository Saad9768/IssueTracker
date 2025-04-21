package com.egroup.issuetracker.service;

import java.util.List;

import com.egroup.issuetracker.dto.StoryDTO;

public interface StoryService {

	boolean deleteStory(long id);

	StoryDTO updateStory(long id, StoryDTO updatedStory);

	StoryDTO addStory(StoryDTO storyDto);

	List<StoryDTO> getAllStories();

	StoryDTO getStoryById(long id);

}
