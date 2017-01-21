package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Filter Application Request Log Data.
 * 
 * 
 * @author Mark Hawkins
 *
 */
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
	 * Timestamp field column.
	 */
	private static final String REQUEST_TIMESTAMP_FIELD_COL = "REQUEST_TIMESTAMP";

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
		Collection<?> logDataCollection = Collections.emptyList();

		// try-with resources always close the Reader stream and clean-up.
		try (BufferedReader br = new BufferedReader(source)) {
			logDataCollection = br.lines()
					.filter(line -> line.split(FIELD_LIMITER)[COUNTRY_CODE_FIELD_INDEX].equals(country))
					.collect(Collectors.toList());
		} catch (IOException ioe) {

			throw new DataFiltererException("IO on Stream", ioe);
		}
		return logDataCollection;
	}

	/**
	 * Filter Request Log Data, first by country then and log with limit greater
	 * than supplied limit parameter.
	 * 
	 * @param source
	 *            Reader io stream.
	 * @param country
	 *            String country code.
	 * @param limit
	 *            long request limit.
	 * @return Collection<?> or empty collection.
	 */
	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		return filterByCountry(source, country).stream()
				.filter(line -> Long.valueOf(((String) line).split(FIELD_LIMITER)[RESPONSE_TIME_FIELD_INDEX]) > limit)
				.collect(Collectors.toList());
	}

	/**
	 * Filter request Log Data by records above the response time.
	 * 
	 * @param source
	 *            Reader io stream
	 * @return Collection<?> or empty collection.
	 */
	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {

		Collection<String> logDataCollection = Collections.emptyList();

		// try-with resources always close the Reader stream and clean-up.
		try (BufferedReader br = new BufferedReader(source)) {

			// Collect all valid records into a Collection for later processing.
			// Do this to avoid disk I/O later.
			logDataCollection = new BufferedReader(source).lines()
					.filter(line -> !line.startsWith(REQUEST_TIMESTAMP_FIELD_COL)).collect(Collectors.toList());
		} catch (IOException ioe) {
			throw new DataFiltererException("IO on Stream", ioe);
		}

		// Get the average by streaming over the collection.
		double average = logDataCollection.stream()
				.mapToLong(line -> Long.valueOf(((String) line).split(FIELD_LIMITER)[RESPONSE_TIME_FIELD_INDEX]))
				.average().getAsDouble();

		// Get the records where response time exceeds our average.
		return logDataCollection.stream()
				.filter(line -> Long.valueOf(((String) line).split(FIELD_LIMITER)[RESPONSE_TIME_FIELD_INDEX]) > average)
				.collect(Collectors.toList());

	}
}