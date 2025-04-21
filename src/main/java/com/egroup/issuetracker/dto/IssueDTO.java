package com.egroup.issuetracker.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
	private long id;
	private String issueId;
	private String title;
	private String description;
	private LocalDateTime creationDate;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private DeveloperDTO assignee;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private long developerId;
}
