package com.connectgroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class DataFiltererTest {

	/**
	 * Expected Index field index.
	 */
	private static final int EXPECTED_COUNTRY_CODE_FIELD_INDEX = 1;

	/**
	 * Expected Field limiter String character.
	 */
	private static final String EXPECTED_FIELD_LIMITER = ",";

	@Test
	public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
		final String countryCode = "GB";
		Assert.assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), countryCode).isEmpty());
	}

	@Test
	public void multiLineGB() throws FileNotFoundException {
		testCountryCodeFilter("GB", "src/test/resources/multi-lines", 1);
	}

	@Test
	public void multiLineUS() throws FileNotFoundException {
		testCountryCodeFilter("US", "src/test/resources/multi-lines", 3);
	}

	@Test
	public void multiLineDE() throws FileNotFoundException {
		testCountryCodeFilter("DE", "src/test/resources/multi-lines", 1);
	}

	@Test
	public void multiLineNONE() throws FileNotFoundException {
		// NO= Norway.
		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "NO");
		Assert.assertTrue(logs.isEmpty());
	}
	
	@Test
	public void emptyFileNONE() throws FileNotFoundException {
		// NO= Norway.
		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "NO");
		Assert.assertTrue(logs.isEmpty());
	}

	/**
	 * Common Test Method for testing basic country code filtering.
	 * 
	 * @param countryCode
	 *            String - Country code.
	 * @param filePath
	 *            String the relative path to the file.
	 * @param expectedRecordCount
	 *            int the expected Record count for this test.
	 * @throws FileNotFoundException
	 */
	private void testCountryCodeFilter(final String countryCode, final String filePath, int expectedRecordCount)
			throws FileNotFoundException {

		final Collection<?> logs = DataFilterer.filterByCountry(openFile(filePath), countryCode);
		Assert.assertFalse(logs.isEmpty());
		logs.forEach(item -> Assert.assertTrue(
				((String) item).split(EXPECTED_FIELD_LIMITER)[EXPECTED_COUNTRY_CODE_FIELD_INDEX].equals(countryCode)));
		Assert.assertTrue(logs.size() == expectedRecordCount);
	}

	private FileReader openFile(String filename) throws FileNotFoundException {
		return new FileReader(new File(filename));
	}
}
