//package com.uhire.rest.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.uhire.rest.config.converter.StringToWorkFrequencyConverter;
//import com.uhire.rest.config.converter.StringToBigDecimalConverter;
//import com.uhire.rest.config.converter.StringToEmployeeStatusConverter;
//import com.uhire.rest.config.converter.StringToPayTypeConverter;
//import com.uhire.rest.config.converter.StringToTaskStatusConverter;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//	@Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new StringToEmployeeStatusConverter());
//        registry.addConverter(new StringToPayTypeConverter());
//        registry.addConverter(new StringToTaskStatusConverter());
//        registry.addConverter(new StringToWorkFrequencyConverter());
//        registry.addConverter(new StringToBigDecimalConverter());
//    }
//	
//}
