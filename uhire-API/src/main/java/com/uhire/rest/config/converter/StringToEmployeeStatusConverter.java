package com.uhire.rest.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.uhire.rest.EmployeeStatus;

@Component
public class StringToEmployeeStatusConverter implements Converter<String, EmployeeStatus>{

	@Override
	public EmployeeStatus convert(String source) {
		return EmployeeStatus.valueOf(source.toUpperCase());
	}

}
