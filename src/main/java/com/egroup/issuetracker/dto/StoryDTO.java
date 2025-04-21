package com.egroup.issuetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoryDTO extends IssueDTO {
	private Integer estimatedPoints;
	private Status status;
}