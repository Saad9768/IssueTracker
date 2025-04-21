package com.egroup.issuetracker.dto;

import com.egroup.issuetracker.entity.Bug;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BugDTO extends IssueDTO {
	private Bug.Priority priority;
	private Status status;
}
