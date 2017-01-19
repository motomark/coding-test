package com.connectgroup;

import java.util.Comparator;

public class CountryComparator implements Comparator<RequestLog> {

	@Override
	public int compare(RequestLog log1, RequestLog log2) {

		return log1.getCountryCode().compareTo(log2.getCountryCode());

	}

}
