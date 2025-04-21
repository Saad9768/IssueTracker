package com.egroup.issuetracker.entity;

import com.egroup.issuetracker.dto.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "story")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Story extends Issue {
	@NotNull(message = "Estimated points are required")
	@Min(0)
	private Integer estimatedPoints;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.NEW;
	
	
}
