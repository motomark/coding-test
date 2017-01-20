package com.connectgroup;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class DataFilterer {

	/**
	 * Country code field index in the CSV file.
	 */
	private static final int COUNTRY_CODE_FIELD_INDEX = 1;

	/**
	 * The Field limiter String character.
	 */
	private static final String FIELD_LIMITER = ",";

	/**
	 * Filter RequestLog data by country code.
	 * 
	 * @param source
	 *            Reader io stream
	 * @param country
	 *            String country code.
	 * @return Collection<?> or empty collection.
	 */
	public static Collection<?> filterByCountry(Reader source, String country) {
		return new BufferedReader(source).lines()
				.filter(line -> line.split(FIELD_LIMITER)[COUNTRY_CODE_FIELD_INDEX].equals(country))
				.collect(Collectors.toList());
	}

	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		return Collections.emptyList();
	}

	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		return Collections.emptyList();
	}
}