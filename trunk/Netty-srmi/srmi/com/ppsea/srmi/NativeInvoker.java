package com.ppsea.srmi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.ppsea.srmi.Filter.ListFilter;

/***
 * 远程调用层 RemoteInvoker 序列化层 Serializer 传输层 InvokConnector
 * 
 * 传输层 Connector? 序列化层 Serializer 本地调用层 NativeInvoker 序列化层 Serializer 传输层
 * Connector?
 * 
 * 传输层 InvokConnector 序列化层 Serializer 远程调用层 RemoteInvoker
 * 
 * @author xingyun
 * 
 */
public class NativeInvoker {

	SharedContext context;

	Serializer serializer;


	Object[] implementers;

	
	ListFilter filterList = new ListFilter();
	
	InterfaceFilter interfaceFilter;
	
	ErrorHandler handler = ErrorHandler.DEFAULT;
	/**
	 * 
	 * @param context
	 * @param serializer
	 */
	public NativeInvoker(SharedContext context,Serializer serializer) {
		this.context = context;
		this.serializer = serializer;
		implementers = new Object[context.getInterfaceSize()];
		interfaceFilter = new InterfaceFilter(context);
		filterList.add(interfaceFilter);
	}
	
	public <T>void setImplements(Class<T> clazz,T implementer){
		int index = context.getInterfaceIndex(clazz);
		if(implementers[index]!=null){
			Log.err("接口被重复实现:"+clazz);
		}
		implementers[index] = implementer;
	}
	
	public void addFilter(Filter filter){
		filterList.add(filter);
	}
	
	public void addFilter(Class<?> clazz,Filter filter){
		interfaceFilter.addFilter(clazz, filter);
	}
	public Buffer invoke(Buffer request) {
		// 调用序列化层，把方法调用的细节序列化层字节数组
		NativeMethod method = serializer.parseMethod(context, request);
		Integer methodIndex = context.getInterfaceIndex(method.method
				.getDeclaringClass());
		
		Object result = null;
		boolean isException = false;
		try {
			if (methodIndex == null) {
				result = handler.interfaceNotFind(method);
			}else{
				Object obj = implementers[methodIndex.intValue()];
				if (obj == null) {
					result = handler.implementerNotFind(method);
				}else{
					//最终调用本地实现
					filterList.readyInvok(methodIndex,method);
					try{
						result = method.invok(obj);
					}catch (InvocationTargetException e) {
						throw filterList.invokException(methodIndex,method,e);
					}
					filterList.invokSuccess(methodIndex,method);
				}
			}
		} catch (InvocationTargetException e) {
			result = e.getTargetException();
			isException = true;
			e.printStackTrace();
		} catch (ServiceException e) {
			result = e;
			isException = true;
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object responseHeader = context.getHeaderProvider().provideResponseHeader(context, method.method,method.requestHeader,method.args
				,isException,result);
		// 解析返回结果，如果有异常则直接在解析层抛出
		return serializer.returnToBuffer(context, responseHeader,result, isException);
	}
	
	class InterfaceFilter implements Filter{
		ListFilter[] filters;
		SharedContext context;
		InterfaceFilter(SharedContext context){
			this.context = context;
			filters = new ListFilter[context.getInterfaceSize()];
		}
		void addFilter(Class<?> clazz,Filter filter){
			Integer index = context.getInterfaceIndex(clazz);
			if(index==null) 
				throw new RuntimeException("没有找到指定的接口"+clazz);
			ListFilter list =filters[index];
			if(list==null){
				list = new ListFilter();
				filters[index] = list;
			}
			list.add(filter);
		}
		
		@Override
		public void readyInvok(int methodIndex, NativeMethod method) {
			ListFilter list = filters[methodIndex];
			if(list!=null)list.readyInvok(methodIndex, method);
		}

		@Override
		public InvocationTargetException invokException(int methodIndex,
				NativeMethod method, InvocationTargetException e) {
			ListFilter list = filters[methodIndex];
			if(list!=null)
				return list.invokException(methodIndex, method,e);
			return e;
		}

		@Override
		public void invokSuccess(int methodIndex, NativeMethod method) {
			ListFilter list = filters[methodIndex];
			if(list!=null)list.invokSuccess(methodIndex, method);
		}
		
	}

}


