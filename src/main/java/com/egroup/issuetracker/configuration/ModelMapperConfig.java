package com.egroup.issuetracker.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.egroup.issuetracker.repository.DeveloperRepository;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper(DeveloperRepository developerRepository) {
		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration().setSkipNullEnabled(true).setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE);

		return mapper;
	}

}
