package com.helco.xf.cs13.ws;

import com.tobesoft.platform.data.Dataset;

public interface CS1301001B_Service {

	public Dataset search(String empno) throws Exception ;

	public Dataset searchComboCst(Dataset ds) throws Exception ;
	
	public void save(Dataset ds) throws Exception;
	
	public void update(Dataset ds) throws Exception;
	
	public void delete(String empno) throws Exception;
}
