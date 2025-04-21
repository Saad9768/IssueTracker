package com.egroup.issuetracker.service;

import java.util.List;

import com.egroup.issuetracker.dto.BugDTO;

public interface BugService {

	BugDTO getBugById(long id);

	List<BugDTO> getAllBugs();

	BugDTO addBug(BugDTO bugDto);

	BugDTO updateBug(long id, BugDTO updatedBug);

	boolean deleteBug(long id);

}
