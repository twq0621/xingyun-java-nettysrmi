package com.ppsea.srmi;


public interface ErrorHandler {
	/**
	 * 方法对应的实现接口没有找到的时候被调用
	 * @param method
	 * @return 返回到客户端的对象，该对象类型必须是method对应的return type
	 * @throws Exception 如果想提示到客户端请抛出 ServiceException
	 */
	Object interfaceNotFind(NativeMethod method) throws Exception;
	/**
	 * 方法对应的实现实例没有找到的时候被调用
	 * @param method
	 * @return 返回到客户端的对象，该对象类型必须是method对应的return type
	 * @throws Exception 如果想提示到客户端请抛出 ServiceException
	 */
	Object implementerNotFind(NativeMethod method) throws Exception;

	ErrorHandler DEFAULT = new Default();

	public static class Default implements ErrorHandler {
		@Override
		public Object interfaceNotFind(NativeMethod method)
				throws Exception {
			throw new ServiceException("方法[" + method.method.getName()
					+ "]对应的接口没有找到");
		}

		@Override
		public Object implementerNotFind(NativeMethod method)
				throws Exception {
			throw new ServiceException("方法[" + method.method.getName()
					+ "]对应的实现没有找到");
		}
	}

}