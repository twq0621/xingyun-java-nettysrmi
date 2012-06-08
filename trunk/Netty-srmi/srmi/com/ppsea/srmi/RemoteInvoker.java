package com.ppsea.srmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/***
 * 远程调用层  RemoteInvoker
 * 序列化层  Serializer
 * 传输层 InvokConnector
 * 
 * 传输层 Connector?
 * 序列化层  Serializer
 * 本地调用层 NativeInvoker
 * 序列化层  Serializer
 * 传输层 Connector?
 * 
 * 传输层 InvokConnector
 * 序列化层  Serializer
 * 远程调用层  RemoteInvoker
 * 
 * @author xingyun
 *
 */
public class RemoteInvoker implements InvocationHandler {
	
	SharedContext context;
	
	Serializer serializer;
	
	InvokConnector connector;
	
	Object responseHeader;
	
	public RemoteInvoker(SharedContext context, Serializer serializer,
			InvokConnector connector) {
		super();
		this.context = context;
		this.serializer = serializer;
		this.connector = connector;
	}


	public static Object newProxy(SharedContext context, Serializer serializer,
			InvokConnector connector,Class<?> clazz){
		return Proxy.newProxyInstance(RemoteInvoker.class.getClassLoader(), new Class[]{clazz}, 
				new RemoteInvoker(context, serializer, connector));
	}
	@Override
	public Object invoke(Object arg0, Method method, Object[] args)
			throws Throwable {
		//调用序列化层，把方法调用的细节序列化层字节数组
		HeaderProvider provider = context.getHeaderProvider();
		Object header = provider.provideRequestHeader(context,method,responseHeader,args);
		Buffer request = serializer.methodToBuffer(context,method,header,args);
		//请求服务器
		Buffer resultBuffer = connector.request(request);
		Object[] responseHeader = new Object[1];
		Object result;
		try{
			result = serializer.parseReturn(context,resultBuffer,responseHeader);
		}catch (Throwable e) {
			throw e;
		}finally{
			this.responseHeader = responseHeader[0];
		}
		//解析返回结果，如果有异常则直接在解析层抛出
		return result;
	}
	
}
