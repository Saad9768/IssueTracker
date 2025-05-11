package com.egroup.issuetracker.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egroup.issuetracker.dto.Status;
import com.egroup.issuetracker.entity.Story;

//@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
	List<Story> findByStatusNot(Status status);

	@Query("""
			SELECT s  FROM Story s WHERE s.status <> 'COMPLETED' and s.estimatedPoints is not NULL
			 and s.estimatedPoints <= :maxEstimatedPoints  and s.assignee is null
			order by s.estimatedPoints DESC
					""")
	Stream<Story> fetchPlanableStories(@Param("maxEstimatedPoints") int maxEstimatedPoints);
}