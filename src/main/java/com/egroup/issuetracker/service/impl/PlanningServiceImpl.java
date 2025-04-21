package com.egroup.issuetracker.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egroup.issuetracker.dto.StoryDTO;
import com.egroup.issuetracker.dto.WeekPlan;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.entity.Story;
import com.egroup.issuetracker.mapper.DtoConverter;
import com.egroup.issuetracker.repository.DeveloperRepository;
import com.egroup.issuetracker.repository.StoryRepository;
import com.egroup.issuetracker.service.PlanningService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanningServiceImpl implements PlanningService {

	private final StoryRepository storyRepository;
	private final DeveloperRepository developerRepository;
	private final DtoConverter dtoConverter;
	private final int pointsPerDeveloperPerWeek;

	public PlanningServiceImpl(StoryRepository storyRepository, DeveloperRepository developerRepository,
			DtoConverter dtoConverter, @Value("${points-per-developer-per-week}") int pointsPerDeveloperPerWeek) {
		this.storyRepository = storyRepository;
		this.developerRepository = developerRepository;
		this.dtoConverter = dtoConverter;
		this.pointsPerDeveloperPerWeek = pointsPerDeveloperPerWeek;
	}

	private List<Developer> fetchDevelopers() {
		List<Developer> developers = developerRepository.findAll();
		log.info("Fetched {} developers for planning", developers.size());
		return developers;
	}

	@Override
	@Transactional(readOnly = true)
	public List<WeekPlan> createPlan() {
		List<Developer> developers = fetchDevelopers();
		if (developers.isEmpty()) {
			log.warn("No developers available for planning.");
			return new ArrayList<>();
		}

		try (Stream<Story> stories = storyRepository.fetchPlanableStories(pointsPerDeveloperPerWeek)) {
			log.info("Fetched planable stories for weekly plan generation");
			List<WeekPlan> plan = generatePlan(stories, developers);
			log.info("Generated weekly plan with {} weeks", plan.size());
			return plan;
		} catch (ClassCastException e) {
			log.error("Failed to cast query result to Story. Possible query issue: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to cast query result to Story. Check your fetchPlanableStories() query.",
					e);
		} catch (Exception e) {
			log.error("Unexpected error occurred during plan generation", e);
			throw new RuntimeException("Unexpected error in plan generation", e);
		}
	}

	private List<WeekPlan> generatePlan(Stream<Story> stories, List<Developer> developers) {

		List<WeekPlan> weeks = new ArrayList<>();

		stories.forEach(s -> {

			StoryDTO story = dtoConverter.convert(s, StoryDTO.class);
			if (story.getEstimatedPoints() <= pointsPerDeveloperPerWeek) {
				boolean assigned = weeks.stream().anyMatch(week -> week.tryAssign(story));
				if (!assigned) {
					WeekPlan newWeek = new WeekPlan(developers, pointsPerDeveloperPerWeek);
					newWeek.tryAssign(story);
					weeks.add(newWeek);
					log.debug("Created new week and assigned story ID: {}", story.getId());
				} else {
					log.debug("Assigned story ID: {} to existing week", story.getId());
				}
			} else {
				log.warn("Story with id: {} skipped, estimated points :: {} ", story.getId(),
						story.getEstimatedPoints());
			}
		});

		weeks.sort(Comparator.comparing(WeekPlan::getTotalWeekPoints).reversed());
		return weeks;
	}
}
