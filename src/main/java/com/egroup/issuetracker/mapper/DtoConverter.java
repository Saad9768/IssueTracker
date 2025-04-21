package com.egroup.issuetracker.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DtoConverter {

	private final ModelMapper modelMapper;

	public <D, T> D convert(T input, Class<D> output) {
		return modelMapper.map(input, output);
	}

	public <D, T> List<T> convertToEntityList(final Collection<D> input, Class<T> output) {
		return input.stream().map(dto -> convert(dto, output)).collect(Collectors.toList());
	}
}
