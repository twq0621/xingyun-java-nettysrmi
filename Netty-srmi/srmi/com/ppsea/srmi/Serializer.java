package com.ppsea.srmi;

import java.lang.reflect.Method;

public interface Serializer {
	/**
	 * 从buff里解析出一个返回对象或者异常
	 * @param context
	 * @param result
	 * @param responseHeader 
	 * @return 正常返回的对象
	 * @throws Throwable 远程返回的异常
	 */
	Object parseReturn(SharedContext context,Buffer result, Object[] responseHeader) throws Throwable;
	/**
	 * 把方法和参数序列化到buffer中
	 * @param context
	 * @param method
	 * @param args
	 * @return
	 */
	Buffer methodToBuffer(SharedContext context, Method method, Object header,
			Object[] args);
	/**
	 * 从buff中解析出一个方法以及参数
	 * @param context
	 * @param request
	 * @return
	 */
	NativeMethod parseMethod(SharedContext context,Buffer request);
	/**
	 * 转换成返回Buffer
	 * @param context
	 * @param result
	 * @param isException
	 * @return
	 */
	Buffer returnToBuffer(SharedContext context, Object header, Object result,
			boolean isException);
	

}
