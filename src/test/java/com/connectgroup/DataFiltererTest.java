package com.connectgroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataFiltererTest {
	
	@BeforeClass
	public static void initDataAdapter()
	{
		DataFilterer.setDataAdapter(new RequestLogDataAdapter());
	}
	
	@Test
	public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
		Assert.assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
	}

	@Test
	public void multiLineGB() throws FileNotFoundException {

		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "GB");
		Assert.assertFalse(logs.isEmpty());
		logs.forEach(item->
			Assert.assertTrue(((RequestLog)item).getCountryCode().equals("GB")
			
		));
		
		// Should only yield 1 record.
		Assert.assertTrue(logs.size() == 1);
		
	}
	
	@Test
	public void multiLineUS() throws FileNotFoundException {

		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US");
		Assert.assertFalse(logs.isEmpty());
		logs.forEach(item->
			Assert.assertTrue(((RequestLog)item).getCountryCode().equals("US")
			
		));
		
		// Should only yield 3 records.
		Assert.assertTrue(logs.size() == 3);
		
	}
	
	
	@Test
	public void multiLineDE() throws FileNotFoundException {

		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "DE");
		Assert.assertFalse(logs.isEmpty());
		logs.forEach(item->
			Assert.assertTrue(((RequestLog)item).getCountryCode().equals("DE")
			
		));
		
		// Should only yield 3 records.
		Assert.assertTrue(logs.size() == 1);
		
	}
	
	@Test
	public void multiLineNO() throws FileNotFoundException {

		// Norwegian should be empty.
		final Collection<?> logs = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "NO");
		Assert.assertTrue(logs.isEmpty());
		
	}
	
	
	
	

	private FileReader openFile(String filename) throws FileNotFoundException {
		return new FileReader(new File(filename));
	}
}
