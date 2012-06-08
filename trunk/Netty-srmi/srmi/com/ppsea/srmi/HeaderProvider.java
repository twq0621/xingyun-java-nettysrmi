package com.ppsea.srmi;

import java.lang.reflect.Method;

public interface HeaderProvider {
	/**
	 * 提供一个返回的Header
	 * @param context
	 * @param method
	 * @param result 
	 * @param isException 
	 * @return
	 */
	Object provideResponseHeader(SharedContext context, Method method,Object requestHeader,Object []args, boolean isException, Object result);
	/**
	 * 提供一个请求的Header
	 * @param context
	 * @param method
	 * @param responseHeader
	 * @param args
	 * @return
	 */
	Object provideRequestHeader(SharedContext context, Method method,
			Object responseHeader, Object[] args);
	
	
	public static class Abstract implements HeaderProvider{

		@Override
		public Object provideResponseHeader(SharedContext context,
				Method method, Object requestHeader, Object[] args,
				boolean isException, Object result) {
			return requestHeader;
		}

		@Override
		public Object provideRequestHeader(SharedContext context,
				Method method, Object responseHeader, Object[] args) {
			return responseHeader;
		}
		
	}
	

}
