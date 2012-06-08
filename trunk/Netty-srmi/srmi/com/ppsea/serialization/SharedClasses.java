package com.ppsea.serialization;

import java.util.ArrayList;

/**
 * ���������������
 * @author xingyun
 * 
 */
public class SharedClasses {
	ArrayList<ClassInfo> classInfo = new ArrayList<ClassInfo>();
	IndexMap<Class<?>> classMap = new IndexMap<Class<?>>();
	
	
	
	ArrayList<SimpleSerial<?>> simples = new ArrayList<SimpleSerial<?>>();
	IndexMap<Class<?>> simpleMap = new IndexMap<Class<?>>();
	
	
	
	
	public SharedClasses() {
		addSimple(SimpleSerial.base);
		addClass(Object.class,long.class,double.class,int.class,float.class,
				boolean.class,char.class,short.class,byte.class);
	}
	public SharedClasses addClass(Class<?>...classes){
		for(Class<?> clazz:classes){
			addClass(clazz);
		}
		return this;
	}
	public SharedClasses addClass(Class<?> clazz){
		assertEmptyConstructor(clazz);
		ClassInfo info = new ClassInfo(this, clazz);
		classMap.put(clazz);
		classInfo.add(info);
		return this;
	}
	
	private void assertEmptyConstructor(Class<?> clazz) {
		try {
			if(!clazz.isPrimitive()){
				clazz.getDeclaredConstructor();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("���ͣ�"+clazz.getName()+"�������޲���������ܱ����?");
		}
	}
	public SharedClasses addSimple(SimpleSerial<?>...simples){
		for(SimpleSerial<?> simple:simples){
			addSimple(simple);
		}
		return this;
	}
	public SharedClasses addSimple(SimpleSerial<?> simple){
		simpleMap.put(simple.getType());
		simples.add(simple);
		return this;
	}
	public void init() {
		for (ClassInfo info:classInfo) {
			info.loadClassInfo();
		}
	}

	public Class<?> getClass(int index) {
		return classMap.get(index);
	}

	public ClassInfo getClassInfo(int index) {
		return classInfo.get(index);
	}

	public Integer getClassIndex(ClassInfo clazz) {
		return getClassIndex(clazz.getClazz());
	}

	public boolean hasClass(ClassInfo info) {
		return classMap.indexOf(info.clazz)!=null;
	}

	public Integer getClassIndex(Class<?> clazz) {
		return classMap.indexOf(clazz);
	}

	public boolean hasClass(Class<?> clazz) {
		return classMap.indexOf(clazz)!=null;
	}

	
	
	
	public ClassInfo getClassInfo(Class<?> clazz) {
		try {
			return getClassInfo(getClassIndex(clazz));
		} catch (Exception e) {
			return null;
		}
	}
	public Integer getSimpleIndex(SimpleSerial<?> simple) {
		return simpleMap.indexOf(simple.getType());
	}
	public Integer getSimpleIndex(Class<?> simple) {
		return simpleMap.indexOf(simple);
	}
	public SimpleSerial<?> getSimple(Class<?> clazz) {
		Integer index = getClassIndex(clazz);
		if(index==null) return null;
		return getSimple(index);
	}
	@SuppressWarnings("unchecked")
	public SimpleSerial<Object> getSimple(int index) {
		return (SimpleSerial<Object>) simples.get(index);
	}

	

}
