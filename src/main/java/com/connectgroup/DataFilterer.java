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
	 * Response Time field index in the CSV file.
	 */
	private static final int RESPONSE_TIME_FIELD_INDEX = 2;

	/**
	 * The Field limiter String character.
	 */
	private static final String FIELD_LIMITER = ",";

	/**
	 * Filter Request Log data by country code.
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


	/**
	 * Filter Request Log Data, first by country then and log with limit greater than supplied limit parameter.
	 * @param source Reader io stream.
	 * @param country String country code.
	 * @param limit long request limit.
	 * @return Collection<?> or empty collection.
	 */
	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		return filterByCountry(source, country).stream()
				.filter(line -> Long.valueOf(((String) line).split(FIELD_LIMITER)[RESPONSE_TIME_FIELD_INDEX]) > limit)
				.collect(Collectors.toList());
	}

	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		return Collections.emptyList();
	}
}