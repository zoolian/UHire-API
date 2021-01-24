package com.uhire.rest.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.uhire.rest.TaskStatus;

@Component
public class StringToTaskStatusConverter implements Converter<String, TaskStatus>{

	@Override
	public TaskStatus convert(String source) {
		return TaskStatus.valueOf(source.toUpperCase());
	}

}
