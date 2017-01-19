package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RequestLogDataAdapter implements DataAdapter{

	
	@Override
	public Collection<RequestLog> findLogData(Reader source) {

		final List<RequestLog> filterList = new ArrayList<RequestLog>();

		BufferedReader br = new BufferedReader(source);
		try {

			// Ignore first line (header info).
			String line = br.readLine();

			// Read the remaining lines.
			while ((line = br.readLine()) != null) {

				final String[] fields = line.split(",");

				// Validate the field length.
				if (fields.length == 3) {
					RequestLog rl = new RequestLog();
					rl.setDateTime(new Date(Long.valueOf(fields[0])));
					rl.setCountryCode(fields[1]);
					rl.setMs(Integer.valueOf(fields[2]));
					filterList.add(rl);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filterList;

	}

}
