package com.connectgroup;

import java.io.Reader;
import java.util.Collection;

public interface DataAdapter {
	
	Collection<RequestLog> findLogData(Reader reader);

}
