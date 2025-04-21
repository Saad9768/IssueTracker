package com.egroup.issuetracker.interceptor;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private static final String START_TIME_ATTR = "startTime";
	private static final String REQUEST_ID = "requestId";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
		request.setAttribute(REQUEST_ID, UUID.randomUUID());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
		long duration = System.currentTimeMillis() - startTime;
		UUID requestId = (UUID) request.getAttribute(REQUEST_ID);

		log.info("[{} {}] completed in {} ms with requestId :: {} ", request.getMethod(), request.getRequestURI(), duration,requestId);
	}
}
