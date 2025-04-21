package com.egroup.issuetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egroup.issuetracker.entity.Bug;

public interface BugRepository extends JpaRepository<Bug, Long> {
}