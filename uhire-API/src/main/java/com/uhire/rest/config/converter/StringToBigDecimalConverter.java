package com.uhire.rest.config.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal>{

	@Override
	public BigDecimal convert(String source) {
		return new BigDecimal(source);
	}

}
