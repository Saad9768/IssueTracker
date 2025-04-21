package com.egroup.issuetracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.egroup.issuetracker.dto.DeveloperDTO;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.exception.ResourceNotFoundException;
import com.egroup.issuetracker.mapper.DtoConverter;
import com.egroup.issuetracker.repository.DeveloperRepository;
import com.egroup.issuetracker.service.DeveloperService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

	private final DeveloperRepository developerRepository;
	private final DtoConverter dtoConverter;

	@Override
	public Developer fetchDeveloperById(long id) {
		log.debug("Fetching Developer entity with id: {}", id);
		return developerRepository.findById(id).orElseThrow(() -> {
			log.warn("Developer not found with id: {}", id);
			return new ResourceNotFoundException("Developer not found for developer id :: " + id);
		});
	}

	@Override
	public DeveloperDTO getDeveloperById(long id) {
		log.info("Retrieving developer by id: {}", id);
		Developer developer = fetchDeveloperById(id);
		return dtoConverter.convert(developer, DeveloperDTO.class);
	}

	@Override
	public List<DeveloperDTO> getAllDevelopers() {
		log.info("Fetching all developers");
		List<Developer> developers = developerRepository.findAll();
		log.debug("Total developers found: {}", developers.size());
		return dtoConverter.convertToEntityList(developers, DeveloperDTO.class);
	}

	@Override
	public DeveloperDTO addDeveloper(DeveloperDTO developer) {
		log.info("Adding new developer: {}", developer.getName());
		Developer dev = dtoConverter.convert(developer, Developer.class);
		Developer saved = developerRepository.save(dev);
		log.debug("Developer saved with id: {}", saved.getId());
		return dtoConverter.convert(saved, DeveloperDTO.class);
	}

	private boolean checkDeveloperExists(long id) {
		boolean exists = developerRepository.existsById(id);
		log.debug("Checking if developer exists with id {}: {}", id, exists);
		return exists;
	}

	@Override
	public DeveloperDTO updateDeveloper(long id, DeveloperDTO updatedDeveloper) {
		log.info("Updating developer with id: {}", id);
		if (!checkDeveloperExists(id)) {
			log.warn("Developer not found for update with id: {}", id);
			throw new ResourceNotFoundException("Developer not found for developer id :: " + id);
		}
		Developer dev = dtoConverter.convert(updatedDeveloper, Developer.class);
		dev.setId(id);
		Developer updated = developerRepository.save(dev);
		log.debug("Developer updated with id: {}", updated.getId());
		return dtoConverter.convert(updated, DeveloperDTO.class);
	}

	@Override
	public boolean deleteDeveloper(long id) {
		log.info("Deleting developer with id: {}", id);
		if (developerRepository.existsById(id)) {
			developerRepository.deleteById(id);
			log.info("Developer deleted with id: {}", id);
			return true;
		}
		log.warn("Developer not found for deletion with id: {}", id);
		return false;
	}
}
