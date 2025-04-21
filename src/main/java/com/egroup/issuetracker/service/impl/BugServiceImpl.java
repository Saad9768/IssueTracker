package com.egroup.issuetracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.egroup.issuetracker.dto.BugDTO;
import com.egroup.issuetracker.entity.Bug;
import com.egroup.issuetracker.entity.Developer;
import com.egroup.issuetracker.exception.ResourceNotFoundException;
import com.egroup.issuetracker.mapper.DtoConverter;
import com.egroup.issuetracker.repository.BugRepository;
import com.egroup.issuetracker.service.BugService;
import com.egroup.issuetracker.service.DeveloperService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BugServiceImpl implements BugService {

	private final BugRepository bugRepository;
	private final DtoConverter dtoConverter;
	private final DeveloperService developerService;

	@Override
	public BugDTO getBugById(long id) {
		log.debug("Fetching bug by id: {}", id);
		Bug bug = bugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Bug not found for bug id :: " + id));
		return dtoConverter.convert(bug, BugDTO.class);
	}

	@Override
	public List<BugDTO> getAllBugs() {
		log.debug("Fetching all bugs");
		List<Bug> bugs = bugRepository.findAll();
		return dtoConverter.convertToEntityList(bugs, BugDTO.class);
	}

	@Override
	public BugDTO addBug(BugDTO bugDto) {
		log.debug("Adding new bug: {}", bugDto);
		Bug bug = dtoConverter.convert(bugDto, Bug.class);
		if (bugDto.getDeveloperId() != 0) {
			Developer developer = developerService.fetchDeveloperById(bugDto.getDeveloperId());
			bug.setAssignee(developer);
		}
		Bug saved = bugRepository.save(bug);
		log.info("Bug created with id: {}", saved.getId());
		return dtoConverter.convert(saved, BugDTO.class);
	}

	@Override
	public BugDTO updateBug(long id, BugDTO updatedBug) {
		log.debug("Updating bug with id: {}", id);
		if (!checkBugExists(id)) {
			log.warn("Bug not found for update with id: {}", id);
			throw new ResourceNotFoundException("Bug not found for Bug id :: " + id);
		}
		Bug bug = dtoConverter.convert(updatedBug, Bug.class);
		bug.setId(id);
		Bug saved = bugRepository.save(bug);
		log.info("Bug updated with id: {}", saved.getId());
		return dtoConverter.convert(saved, BugDTO.class);
	}

	@Override
	public boolean deleteBug(long id) {
		log.debug("Deleting bug with id: {}", id);
		if (!checkBugExists(id)) {
			log.warn("Bug not found for deletion with id: {}", id);
			return false;
		}
		bugRepository.deleteById(id);
		log.info("Bug deleted with id: {}", id);
		return true;
	}

	private boolean checkBugExists(long id) {
		return bugRepository.existsById(id);
	}
}
