package com.egroup.issuetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egroup.issuetracker.entity.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}