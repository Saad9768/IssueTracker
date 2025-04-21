package com.egroup.issuetracker.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.egroup.issuetracker.entity.Developer;

public class WeekPlan {
	private final UUID weekIdentifier;
	private final List<DeveloperAssignment> developers;
	private int totalWeekPoints;
	private final int maxWeekPoints;

	public WeekPlan(List<Developer> devs, int pointsPerWeek) {
		this.weekIdentifier = UUID.randomUUID();
		this.totalWeekPoints = 0;
		this.developers = new ArrayList<>();
		for (Developer dev : devs) {
			this.developers.add(new DeveloperAssignment(dev.getId(), pointsPerWeek));
		}
		this.maxWeekPoints = this.developers.size() * pointsPerWeek;
	}

	public List<DeveloperAssignment> getDevelopers() {
		return this.developers;
	}

	public UUID getWeekIdentifier() {
		return this.weekIdentifier;
	}

	public int getTotalWeekPoints() {
		return this.totalWeekPoints;
	}

	private boolean canAccept(StoryDTO s) {
		return this.getTotalWeekPoints() + s.getEstimatedPoints() <= this.maxWeekPoints;
	}

	public boolean tryAssign(StoryDTO s) {
		if (!canAccept(s))
			return false;
		for (DeveloperAssignment dev : developers) {
			if (dev.canFit(s)) {
				dev.assign(s);
				this.totalWeekPoints += s.getEstimatedPoints();
				return true;
			}
		}
		return false;
	}
}