package com.egroup.issuetracker.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}