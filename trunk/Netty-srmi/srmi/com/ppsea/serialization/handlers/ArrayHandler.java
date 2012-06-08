package com.ppsea.serialization.handlers;

import java.io.IOException;
import java.lang.reflect.Array;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;
import com.ppsea.serialization.ObjectArrayAccessor;


public class ArrayHandler extends Handler implements BaseAccessor{
	
	Handler contentHandler;
	int index;
	Accessor accessor;
	public static ArrayHandler getObjectArrayHandler(){
		return new ArrayHandler().init(new ObjectArrayAccessor());
	}
	@Override
	public ArrayHandler init(Accessor accessor) {
		this.accessor = accessor;
		contentHandler = getHander(this);
		return this;
	}
	@Override
	public Accessor getAccessor() {
		return accessor;
	}
	@Override
	public void toStream(Object obj, BeanOutputStream dos)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		Object array = accessor.get(obj);
		if(array==null){
			dos.writeInt(-1);
			return;
		}
		
		int length = Array.getLength(array);
		dos.writeInt(length);
		for(int i=0;i<length;i++){
			synchronized (this) {
				index = i;
				contentHandler.toStream(array, dos);
			}
		}
	}
	public Object fromStream(BeanInputStream dis,Class<?>contentType)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		int length = dis.readInt();
		if(length==-1){
			return null;
		}
		Object array = Array.newInstance(contentType, length);
		for (int i = 0; i < length; i++) {
			synchronized (this) {
				index = i;
				contentHandler.fromStream(array, dis);
			}
		}
		return array;
	}
	@Override
	public void fromStream(Object obj,BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		accessor.set(obj, fromStream(dis,getType()));
	}
	@Override
	public int getInt(Object target) throws IllegalAccessException {
		return Array.getInt(target, index);
	}
	@Override
	public void setInt(Object target, int value) throws IllegalAccessException {
		Array.set(target, index, value);
	}
	@Override
	public String getString(Object target) throws IllegalAccessException {
		return (String)get(target);
	}
	@Override
	public void setString(Object target, String value)
			throws IllegalAccessException {
		set(target,value);
	}
	@Override
	public float getFloat(Object target) throws IllegalAccessException {
		return Array.getFloat(target, index);
	}
	@Override
	public void setFloat(Object target, float value)
			throws IllegalAccessException {
		Array.setFloat(target, index, value);
	}
	@Override
	public Object get(Object target) throws IllegalAccessException {
		return Array.get(target, index);
	}
	@Override
	public void set(Object target, Object value) throws IllegalAccessException {
		Array.set(target, index, value);
	}
	@Override
	public boolean getBoolean(Object target) throws IllegalAccessException {
		return Array.getBoolean(target, index);
	}
	@Override
	public void setBoolean(Object target, boolean value)
			throws IllegalAccessException {
		Array.setBoolean(target, index, value);
	}
	@Override
	public void setLong(Object target, long value)
			throws IllegalAccessException {
		Array.setLong(target, index, value);
	}
	@Override
	public long getLong(Object target) throws IllegalAccessException {
		return Array.getLong(target, index);
	}
	@Override
	public Class<?> getType() {
		return accessor.getType().getComponentType();
	}

}