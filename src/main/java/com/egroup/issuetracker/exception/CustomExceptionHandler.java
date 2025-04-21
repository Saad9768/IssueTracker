package com.egroup.issuetracker.exception;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleRecordSizeException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.NOT_FOUND, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.FORBIDDEN, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> exception(DataIntegrityViolationException ex) {
		return new ResponseEntity<>(getBody(HttpStatus.BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception ex) {
		return new ResponseEntity<>(getBody(HttpStatus.INTERNAL_SERVER_ERROR, ex, "Something Went Wrong"),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {

//		log.error(message, ex);

		System.out.println(message);

		System.out.println(ex);

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", message);
		body.put("timestamp", new Date());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
//		body.put("exception", ex.toString());

//		Throwable cause = ex.getCause();
//		if (cause != null) {
//			body.put("exceptionCause", ex.getCause().toString());
//		}
		return body;
	}

}
