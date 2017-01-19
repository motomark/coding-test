package com.connectgroup;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.Request;

public class DataFilterer {

	private static DataAdapter dataAdapter;

	public static void setDataAdapter(DataAdapter pDataAdapter) {

		dataAdapter = pDataAdapter;
	}

	public static Collection<?> filterByCountry(Reader source, String country) {

		List<RequestLog> newCollection = new ArrayList<RequestLog>();
		
		dataAdapter.findLogData(source).forEach(item->{
			RequestLog rl = (RequestLog)  item;
			if(rl.getCountryCode().equals("GB"))
			{
				//System.out.println(rl.getCountryCode());
				newCollection.add(rl);
			}
		});
		
		return newCollection; 
	}

	public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
		return Collections.emptyList();
	}

	public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
		return Collections.emptyList();
	}
}