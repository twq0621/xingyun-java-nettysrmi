package com.ppsea.serialization;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.List;

import com.ppsea.serialization.handlers.ArrayHandler;
import com.ppsea.serialization.handlers.Handler;
import com.ppsea.serialization.handlers.ListHandler;
import com.ppsea.srmi.Log;


public class BeanOutputStream extends DataOutputStream{
	Hashtable<Object, Integer> objtable= new Hashtable<Object, Integer>();
	int refIndex = 0;
	SharedClasses context;
	public BeanOutputStream(SharedClasses context,OutputStream os) {
		super(os);
		this.context = context;
	}
	public void writeObject(Object obj) throws IllegalArgumentException, IOException, IllegalAccessException{
		try{
			writeNextObject(obj);
		}finally{
			clearBuffer();
		}
	}
	public void clearBuffer(){
		objtable.clear();
		refIndex = 0;
	}
	
	public void writeNextObject(Object obj) throws IOException, IllegalArgumentException, IllegalAccessException{
		
		if(obj==null){
			write(Define.NULL);
			return;
		}
		
		//�ж��Ƿ��Ѿ�д����
		Integer index = objtable.get(obj);
		if(index!=null){
			write(Define.REFERENCE);
			writeShort(index);
			return;
		}
		
		
		Class<?> clazz = obj.getClass();
		if(clazz.isArray()){
			writeObjectArray(obj);
			objtable.put(obj, refIndex++);
			return;
		}
		/**
		 * �ж��ǲ���List
		 */
		if(List.class.isAssignableFrom(clazz)){
			write(Define.LIST);
			writeObjectList((List<?>)obj);
			objtable.put(obj, refIndex++);
			return;
		}
		
		//�ж��ǲ��ǻ��������
		Integer seriaIndex = context.getSimpleIndex(clazz);
		if(seriaIndex!=null){
			write(Define.BASE);
			writeShort(seriaIndex);
			SimpleSerial<Object> simple = context.getSimple(seriaIndex);
			simple.toStream(obj, this);
			objtable.put(obj, refIndex++);
			return;
		}
		Integer infoIndex = context.getClassIndex(clazz);
		if(infoIndex!=null){
			objtable.put(obj, refIndex++);
			write(Define.OBJECT);
			writeShort(infoIndex);
			ClassInfo classinfo = context.getClassInfo(infoIndex);
			writeNextObjectImpl(obj, classinfo);
			
			return;
		}
		throw new RuntimeException("unknow class"+obj.getClass());
//		System.err.println("unknow class"+obj.getClass());
//		write(Define.UNKNOW);
//		return;
	}

	public void writeObjectList(List<?> array) throws IllegalArgumentException, IllegalAccessException, IOException {
		ListHandler.objectListHandler.toStream(array, this);
	}
	public void writeObjectArray(Object array) throws IllegalArgumentException, IllegalAccessException, IOException{
		
		Class<?> componentType = array.getClass().getComponentType();
		if(componentType.isArray()){
			throw new RuntimeException("��֧��ֱ�Ӷ���Ķ�ά���� type:"+componentType);
		}else{
			Integer classIndex = context.getClassIndex(componentType);
			if(classIndex==null){
				classIndex = context.getSimpleIndex(componentType);
				if(classIndex==null){
					throw new RuntimeException("�޷����л�δ֪������ type:"+componentType);
				}
				write(Define.BASE_ARRAY);
			}else{
				write(Define.OBJECT_ARRAY);
			}
			writeShort(classIndex);
			ArrayHandler.getObjectArrayHandler().toStream(array, this);
		}
	}	
	private void writeNextObjectImpl(Object obj,ClassInfo context) throws IOException, IllegalArgumentException, IllegalAccessException{
		
		Handler[] handers = context.getHanders();
		for (int i = 0; i < handers.length; i++) {
			try{
				
				handers[i].toStream(obj,this);
			}catch (Exception e) {
				Log.err("exception at write field:"+handers[i]);
				e.printStackTrace();
			}
		}
	}
	public OutputStream getOut() {
		return out;
	}

	
}
