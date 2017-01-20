package com.connectgroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class DataFiltererTest {

	/**
	 * Path Constant for empty file.
	 */
	private static final String EMPTY_FILE_PATH = "src/test/resources/empty";

	/**
	 * Path Constant for multi-line file.
	 */
	private static final String MULTI_FILE_PATH = "src/test/resources/multi-lines";

	/**
	 * Path Constant for single line file.
	 */
	private static final String SINGLE_FILE_PATH = "src/test/resources/single-line";

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
		Assert.assertTrue(DataFilterer.filterByCountry(openFile(EMPTY_FILE_PATH), countryCode).isEmpty());
	}

	@Test
	public void multiLineGB() throws FileNotFoundException {
		testCountryCodeFilter("GB", MULTI_FILE_PATH, 1);
	}

	@Test
	public void multiLineUS() throws FileNotFoundException {
		testCountryCodeFilter("US", MULTI_FILE_PATH, 3);
	}

	@Test
	public void multiLineDE() throws FileNotFoundException {
		testCountryCodeFilter("DE", MULTI_FILE_PATH, 1);
	}

	@Test
	public void multiLineNONE() throws FileNotFoundException {
		final Collection<?> logs = DataFilterer.filterByCountry(openFile(MULTI_FILE_PATH), "NO");
		Assert.assertTrue(logs.isEmpty());
	}

	@Test
	public void emptyFileNONE() throws FileNotFoundException {
		final Collection<?> logs = DataFilterer.filterByCountry(openFile(EMPTY_FILE_PATH), "NO");
		Assert.assertTrue(logs.isEmpty());
	}

	@Test
	public void filterByResponseTimeGreater() throws FileNotFoundException {
		Collection<?> logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(MULTI_FILE_PATH), "US",
				538L);
		Assert.assertTrue(logs.size() == 3);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(MULTI_FILE_PATH), "US", 539L);
		Assert.assertTrue(logs.size() == 2);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(MULTI_FILE_PATH), "DE", 416L);
		Assert.assertTrue(logs.size() == 0);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(MULTI_FILE_PATH), "DE", 414L);
		Assert.assertTrue(logs.size() == 1);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(EMPTY_FILE_PATH), "US", 0L);
		Assert.assertTrue(logs.size() == 0);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(SINGLE_FILE_PATH), "GB", 0L);
		Assert.assertTrue(logs.size() == 1);

		logs = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile(SINGLE_FILE_PATH), "GB", 200L);
		Assert.assertTrue(logs.size() == 0);

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
