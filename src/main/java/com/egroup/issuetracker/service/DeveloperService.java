package com.egroup.issuetracker.service;

import java.util.List;

import com.egroup.issuetracker.dto.DeveloperDTO;
import com.egroup.issuetracker.entity.Developer;

public interface DeveloperService {

	Developer fetchDeveloperById(long id);

	DeveloperDTO getDeveloperById(long id);

	List<DeveloperDTO> getAllDevelopers();

	DeveloperDTO addDeveloper(DeveloperDTO developer);

	DeveloperDTO updateDeveloper(long id, DeveloperDTO updatedDeveloper);

	boolean deleteDeveloper(long id);

}
