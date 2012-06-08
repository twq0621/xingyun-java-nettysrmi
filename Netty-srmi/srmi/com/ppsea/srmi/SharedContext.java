package com.ppsea.srmi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.ppsea.serialization.IndexMap;
import com.ppsea.serialization.SharedClasses;

public class SharedContext {
	IndexMap<Method> methodMap = new IndexMap<Method>();
	IndexMap<Class<?>> interfaceMap = new IndexMap<Class<?>>();
	ArrayList<InterfaceInfo> interfaceList = new ArrayList<InterfaceInfo>();
	SharedClasses classContext = new SharedClasses();
	private HeaderProvider headerProvider;
	
	
	public SharedContext() {
		classContext.addClass(ServiceException.class);
	}
	
	
	private void initInterface() {

		ArrayList<Method> methods = new ArrayList<Method>();
		for (InterfaceInfo intf : interfaceList) {
			for (Method method : intf.getType().getMethods()) {
				methods.add(method);
			}
		}
		Method[] sharedMethod = new Method[methods.size()];
		sharedMethod = methods.toArray(sharedMethod);
		Arrays.sort(sharedMethod, new Comparator<Method>() {
			@Override
			public int compare(Method f1, Method f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		methodMap.put(sharedMethod);
	}
	
	public void setHeaderProvider(HeaderProvider headerProvider) {
		this.headerProvider = headerProvider;
	}


	public SharedContext init() {
		long start = System.currentTimeMillis();
		classContext.init();
		initInterface();
		Log.info("init shared context use millis:"
				+ (System.currentTimeMillis() - start));
		return this;
	}
	
	public Integer getInterfaceIndex(Class<?> clazz){
		return interfaceMap.indexOf(clazz);
	}
	public SharedContext addInterface(Class<?>...classes){
		for(Class<?> clazz:classes){
			addInterface(clazz);
		}
		return this;
	}
	public SharedContext addInterface(Class<?> clazz){
		interfaceList.add(new InterfaceInfo(clazz));
		interfaceMap.put(clazz);
		return this;
	}


	public SharedClasses getClassContext() {
		return classContext;
	}
	public void addClass(Class<?>...classes) {
		for(Class<?> clazz:classes){
			addClass(clazz);
		}
	}
	public void addClass(Class<?> clazz) {
		classContext.addClass(clazz);
	}

	public Method getMethod(int index) {
		return methodMap.get(index);
	}

	public Integer getMethodIndex(Method method) {
		return methodMap.indexOf(method);
	}

	public int getInterfaceSize() {
		return interfaceList.size();
	}


	public HeaderProvider getHeaderProvider() {
		return headerProvider;
	}

}
