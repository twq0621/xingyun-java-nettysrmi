package com.ppsea.srmi;

public interface InvokConnector {

	Buffer request(Buffer request);
	void requestNoReturn(Buffer request);
}
