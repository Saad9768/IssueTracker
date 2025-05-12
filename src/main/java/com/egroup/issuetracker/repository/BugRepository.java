package com.egroup.issuetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egroup.issuetracker.entity.Bug;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
}