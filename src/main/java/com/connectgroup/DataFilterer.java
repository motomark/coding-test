package com.connectgroup;

import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class DataFilterer {

	private static DataAdapter dataAdapter;

	public static void setDataAdapter(DataAdapter pDataAdapter) {

		dataAdapter = pDataAdapter;
	}

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
		return dataAdapter.findLogData(source).stream()
				.filter(requestLog -> requestLog.getCountryCode().startsWith(country)).collect(Collectors.toList());
	}

	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		return Collections.emptyList();
	}

	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		return Collections.emptyList();
	}
}