package com.ppsea.srmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NativeMethod {
	Object requestHeader;
	Method method;
	Object [] args;
	
	
	public NativeMethod(Method method, Object[] args,Object requestHeader) {
		super();
		this.method = method;
		this.args = args;
		this.requestHeader = requestHeader;
	}
	
	
	
	public Object invok(Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		return method.invoke(obj, args);
	}



	public Object getRequestHeader() {
		return requestHeader;
	}



	public Method getMethod() {
		return method;
	}



	public Object[] getArgs() {
		return args;
	}
	
	
}
