package com.egroup.issuetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egroup.issuetracker.entity.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}