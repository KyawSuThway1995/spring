package com.shopping.app.config;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Optional<HttpMessageConverter<?>> foundConverter = converters.stream()
				.filter(a -> a instanceof AbstractJackson2HttpMessageConverter).findFirst();

		if (foundConverter.isPresent()) {
			AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) foundConverter
					.get();
			converter.getObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			converter.getObjectMapper().addMixIn(Object.class, DisableHibernateProperties.class);
			converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		}
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "{}" })
	private class DisableHibernateProperties {
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowCredentials(true)
		.allowedOrigins("*")
		.allowedHeaders("*")
		.allowedMethods("*");
		
	}
}
