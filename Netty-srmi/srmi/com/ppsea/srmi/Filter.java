package com.ppsea.srmi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface Filter{
	void readyInvok(int methodIndex, NativeMethod method);
	InvocationTargetException invokException(int methodIndex, NativeMethod method, InvocationTargetException e);
	void invokSuccess(int methodIndex, NativeMethod method);
	
	
	public static  class Abstract implements Filter{

		@Override
		public void readyInvok(int methodIndex, NativeMethod method) {
			
		}

		@Override
		public InvocationTargetException invokException(int methodIndex,
				NativeMethod method, InvocationTargetException e) {
			return e;
		}

		@Override
		public void invokSuccess(int methodIndex, NativeMethod method) {
			
		}
		
	}
	
	public static class ListFilter implements Filter{
		List<Filter> filters = new ArrayList<Filter>();
		public void invokSuccess(int methodIndex, NativeMethod method) {
			for(int i=0;i<filters.size();i++){
				filters.get(i).invokSuccess(methodIndex, method);
			}
		}

		public InvocationTargetException invokException(int methodIndex, NativeMethod method, InvocationTargetException e) {
			for(int i=0;i<filters.size();i++){
				e=filters.get(i).invokException(methodIndex, method, e);
			}
			return e;
		}

		public void readyInvok(int methodIndex, NativeMethod method) {
			for(int i=0;i<filters.size();i++){
				filters.get(i).readyInvok(methodIndex, method);
			}
		}

		public void add(Filter filter) {
			filters.add(filter);
		}
	}

	
	
	
}