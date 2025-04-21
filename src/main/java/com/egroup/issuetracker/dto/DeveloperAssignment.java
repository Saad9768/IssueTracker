package com.egroup.issuetracker.dto;

import java.util.ArrayList;
import java.util.List;

import com.egroup.issuetracker.dto.StoryDTO;

public class DeveloperAssignment {
	private final long id;
	private final int maxPoints;
	private int usedPoints = 0;
	List<StoryDTO> assignedStories = new ArrayList<>();

	public DeveloperAssignment(Long id, int maxPoints) {
		this.id = id;
		this.maxPoints = maxPoints;
	}

	public int remainingPoints() {
		return maxPoints - usedPoints;
	}

	public long getId() {
		return this.id;
	}

	public List<StoryDTO> getAssignedStories() {
		return this.assignedStories;
	}

	public boolean canFit(StoryDTO s) {
		return remainingPoints() >= s.getEstimatedPoints();
	}

	public void assign(StoryDTO s) {
		usedPoints += s.getEstimatedPoints();
		assignedStories.add(s);
	}
}