package com.ppsea.serialization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ppsea.serialization.handlers.ArrayHandler;
import com.ppsea.serialization.handlers.Handler;
import com.ppsea.serialization.handlers.ListHandler;
import com.ppsea.srmi.Log;
/**
 * ֧��ϵ�л������InputStream
 * 
 * @author cyg
 *
 */
public class BeanInputStream extends DataInputStream {
	public static final byte[] EMPTY_OBJECT_DATA = new byte[]{0,0,0,0};
	/**
	 * ���ڻ����Ѿ���ȡ�Ķ���
	 */
	ArrayList<Object> list = new ArrayList<Object>();
	
	SharedClasses context;
	public BeanInputStream(SharedClasses context,InputStream is) {
		super(is);
		this.context = context;
	}
	/**
	 * ��ȡһ��������֮����Զ����?��
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object readObject() throws IllegalArgumentException, IOException, IllegalAccessException, InstantiationException{
		try{
			return readNextObject();
		}finally{
			clearBuffer();
		}
	}
	public void clearBuffer(){
		list.clear();
	}
	/**
	 * ��ȡһ������
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public Object readObjectArray() throws IllegalArgumentException, IllegalAccessException, IOException{
		int typeIndex = readShort();
		Class<?> contentType = context.getClass(typeIndex);
		return ArrayHandler.getObjectArrayHandler().fromStream(this,contentType);
	}
	/**
	 * ��ȡһ������
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public Object readBaseArray() throws IllegalArgumentException, IllegalAccessException, IOException{
		int typeIndex = readShort();
		Class<?> contentType = context.getSimple(typeIndex).getType();
		return ArrayHandler.getObjectArrayHandler().fromStream(this,contentType);
	}
	/**
	 * ��ȡһ��List
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public Object readList() throws IllegalArgumentException, IOException, IllegalAccessException {
		return ListHandler.objectListHandler.fromStream(this);
	}
	/**
	 * ��ȡһ����󣬲����?��
	 * ����֧��REFERENCE��ȡ
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object readNextObject() throws IOException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		int flag = read();
		Object bean;
		switch(flag){
		case Define.NULL:
			return null;
		case Define.REFERENCE:
			int ref = readShort();
			return list.get(ref);
		case Define.OBJECT_ARRAY:
			bean =  readObjectArray();
			list.add(bean);
			return bean;
		case Define.BASE_ARRAY:
			bean =  readBaseArray();
			list.add(bean);
			return bean;
		case Define.LIST:
			bean =  readList();
			list.add(bean);
			return bean;
		case Define.BASE:
			int serialIndex = readShort();
			SimpleSerial<?> serial = context.getSimple(serialIndex);
			bean =  serial.fromStream(this);
			list.add(bean);
			return bean;
		case Define.OBJECT:
			int index = readShort();
			ClassInfo cc = context.getClassInfo(index);
			bean = cc.newInstance();
			list.add(bean);
			Handler[] handers = cc.getHanders();
			for (int i = 0; i < handers.length; i++) {
				handers[i].fromStream(bean, this);
			}
			return bean;
		case Define.UNKNOW:
			Log.err("read unknow class");
			return null;
		}
		throw new RuntimeException("flag "+flag+" not implement!!");
	}

	
}
