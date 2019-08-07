package com.shopping.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateConverter {

	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public LocalDate stringToLocalDate(String date) {
		try {
			return LocalDate.parse(date, df);
		} catch (Exception e) {
			return null;
		}
	}
}
