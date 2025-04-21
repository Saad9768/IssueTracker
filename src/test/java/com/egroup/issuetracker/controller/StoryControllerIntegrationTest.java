package com.egroup.issuetracker.controller;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.egroup.issuetracker.dto.Status;
import com.egroup.issuetracker.dto.StoryDTO;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.entity.Story;
import com.egroup.issuetracker.repository.DeveloperRepository;
import com.egroup.issuetracker.repository.StoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private DeveloperRepository developerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private Developer dev1;
	private Developer dev2;

	@BeforeEach
	public void setup() {
		storyRepository.deleteAll();
		developerRepository.deleteAll();

		dev1 = developerRepository.save(new Developer(0L, "Dev A"));
		dev2 = developerRepository.save(new Developer(0L, "Dev B"));

		Story s1 = new Story(8, Status.NEW);
		s1.setTitle("Story 1");
		s1.setAssignee(dev1);
		storyRepository.save(s1);

		Story s2 = new Story(8, Status.NEW);
		s2.setTitle("Story 2");
		s2.setAssignee(dev2);
		storyRepository.save(s2);

		Story s3 = new Story(5, Status.NEW);
		s3.setTitle("Story 3");
		storyRepository.save(s3);
	}

	@Test
	public void testGetAllStories() throws Exception {
		mockMvc.perform(get("/api/stories")).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	public void testGetStoryById() throws Exception {
		Story story = storyRepository.findAll().get(0);

		mockMvc.perform(get("/api/stories/" + story.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is((int) story.getId())))
				.andExpect(jsonPath("$.title", is(story.getTitle())));
	}

	@Test
	public void testAddStory() throws Exception {
		StoryDTO dto = new StoryDTO();
		dto.setTitle("New Story");
		dto.setDescription("New Description");
		dto.setEstimatedPoints(9);
		dto.setStatus(Status.NEW);
		dto.setIssueId("Test Isuse Id 1");

		mockMvc.perform(post("/api/stories").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.title", is("New Story")))
				.andExpect(jsonPath("$.description", is("New Description")));
	}

	@Test
	public void testUpdateStory() throws Exception {
		Story story = storyRepository.findAll().get(0);

		StoryDTO dto = new StoryDTO();
		dto.setTitle("Updated Story");
		dto.setDescription("Updated Description");
		dto.setEstimatedPoints(7);
		dto.setStatus(Status.NEW);

		mockMvc.perform(put("/api/stories/" + story.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Updated Story"))).andExpect(jsonPath("$.estimatedPoints", is(7)))
				.andExpect(jsonPath("$.status", is("NEW")));
	}

	@Test
	public void testDeleteStory() throws Exception {
		Story story = storyRepository.findAll().get(0);

		mockMvc.perform(delete("/api/stories/" + story.getId())).andExpect(status().isNoContent());

		mockMvc.perform(get("/api/stories/" + story.getId())).andExpect(status().isNotFound());
	}

	@Test
	public void testGetPlan() throws Exception {
		mockMvc.perform(get("/api/stories/plan")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", greaterThan(0)));
	}
}
