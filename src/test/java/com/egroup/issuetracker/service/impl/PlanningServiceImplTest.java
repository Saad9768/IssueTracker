package com.egroup.issuetracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.egroup.issuetracker.dto.StoryDTO;
import com.egroup.issuetracker.dto.WeekPlan;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.entity.Story;
import com.egroup.issuetracker.mapper.DtoConverter;
import com.egroup.issuetracker.repository.DeveloperRepository;
import com.egroup.issuetracker.repository.StoryRepository;

class PlanningServiceImplTest {

	private StoryRepository storyRepository;
	private DeveloperRepository developerRepository;
	private DtoConverter dtoConverter;

	private PlanningServiceImpl planningService;

	private final int POINTS_PER_DEV = 10;

	@BeforeEach
	void setUp() {
		storyRepository = mock(StoryRepository.class);
		developerRepository = mock(DeveloperRepository.class);
		dtoConverter = mock(DtoConverter.class);
		planningService = new PlanningServiceImpl(storyRepository, developerRepository, dtoConverter, POINTS_PER_DEV);
	}

	private Story mockStory(long id, int points) {
		Story s = new Story();
		s.setId(id);
		StoryDTO dto = new StoryDTO();
		dto.setId(id);
		dto.setEstimatedPoints(points);
		when(dtoConverter.convert(eq(s), eq(StoryDTO.class))).thenReturn(dto);
		return s;
	}

	@Test
	void testPlanningWithTwoDevelopersAndThreeStories() {
		// Setup: 2 developers, stories with points 8, 8, 5
		Developer dev1 = new Developer();
		dev1.setId(1L);
		Developer dev2 = new Developer();
		dev2.setId(2L);

		List<Developer> developers = Arrays.asList(dev1, dev2);
		when(developerRepository.findAll()).thenReturn(developers);

		Story s1 = mockStory(101, 8);
		Story s2 = mockStory(102, 8);
		Story s3 = mockStory(103, 5);

		when(storyRepository.fetchPlanableStories(POINTS_PER_DEV)).thenReturn(Stream.of(s1, s2, s3));

		List<WeekPlan> plan = planningService.createPlan();

		assertEquals(2, plan.size(), "Should create 2 week plans");
	}

	@Test
	void testPlanningWithOneDeveloperAndThreeStories() {
		// Setup: 1 developer, stories with points 9, 9, 2
		Developer dev1 = new Developer();
		dev1.setId(1L);
		List<Developer> developers = Arrays.asList(dev1);
		when(developerRepository.findAll()).thenReturn(developers);

		Story s1 = mockStory(201, 9);
		Story s2 = mockStory(202, 9);
		Story s3 = mockStory(203, 2);

		when(storyRepository.fetchPlanableStories(POINTS_PER_DEV)).thenReturn(Stream.of(s1, s2, s3));

		List<WeekPlan> plan = planningService.createPlan();

		assertEquals(3, plan.size(), "Should create 3 week plans");
	}

	@Test
	void testPlanningWithTwoDeveloperAndThreeStories() {
		// Setup: 2 developer, stories with points 9, 9, 2
		Developer dev1 = new Developer();
		dev1.setId(1L);
		Developer dev2 = new Developer();
		dev2.setId(2L);
		List<Developer> developers = Arrays.asList(dev1, dev2);
		when(developerRepository.findAll()).thenReturn(developers);

		Story s1 = mockStory(201, 9);
		Story s2 = mockStory(202, 9);
		Story s3 = mockStory(203, 2);

		when(storyRepository.fetchPlanableStories(POINTS_PER_DEV)).thenReturn(Stream.of(s1, s2, s3));

		List<WeekPlan> plan = planningService.createPlan();

		assertEquals(2, plan.size(), "Should create 2 week plans");
	}

	@Test
	void testPlanningWithTwoDeveloperAndFourStories() {
		// Setup: 2 developer, stories with points 8, 8, 2, 2
		Developer dev1 = new Developer();
		dev1.setId(1L);
		Developer dev2 = new Developer();
		dev2.setId(2L);
		List<Developer> developers = Arrays.asList(dev1, dev2);
		when(developerRepository.findAll()).thenReturn(developers);

		Story s1 = mockStory(201, 8);
		Story s2 = mockStory(202, 8);
		Story s3 = mockStory(203, 2);
		Story s4 = mockStory(204, 2);

		when(storyRepository.fetchPlanableStories(POINTS_PER_DEV)).thenReturn(Stream.of(s1, s2, s3, s4));

		List<WeekPlan> plan = planningService.createPlan();

		assertEquals(1, plan.size(), "Should create 1 week plans");
	}

	@Test
	void testPlanningWithThreeDeveloperAndSixStories() {
		// Setup: 3 developer, stories with points 9,8,8,2,2,3
		Developer dev1 = new Developer();
		dev1.setId(1L);
		Developer dev2 = new Developer();
		dev2.setId(2L);
		Developer dev3 = new Developer();
		dev3.setId(2L);
		List<Developer> developers = Arrays.asList(dev1, dev2, dev3);
		when(developerRepository.findAll()).thenReturn(developers);

		Story s1 = mockStory(201, 9);
		Story s2 = mockStory(202, 8);
		Story s3 = mockStory(203, 8);
		Story s4 = mockStory(204, 2);
		Story s5 = mockStory(205, 2);
		Story s6 = mockStory(206, 3);

		when(storyRepository.fetchPlanableStories(POINTS_PER_DEV)).thenReturn(Stream.of(s1, s2, s3, s4, s5, s6));

		List<WeekPlan> plan = planningService.createPlan();

		assertEquals(2, plan.size(), "Should create 2 week plans");
	}
}
