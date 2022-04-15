package com.helco.xf.cs34.ws;

import com.tobesoft.platform.data.Dataset;

public interface CS3403001A_Service {

	public Dataset query(Dataset ds) throws Exception ;
	
	public void save(Dataset ds) throws Exception;
}
