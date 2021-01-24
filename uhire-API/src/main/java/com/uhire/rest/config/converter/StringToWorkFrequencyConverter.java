package com.uhire.rest.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.uhire.rest.WorkFrequency;

@Component
public class StringToWorkFrequencyConverter implements Converter<String, WorkFrequency>{

	@Override
	public WorkFrequency convert(String source) {
		return WorkFrequency.valueOf(source.toUpperCase());
	}

}
