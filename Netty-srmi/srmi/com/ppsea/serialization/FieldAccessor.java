package com.ppsea.serialization;

import java.lang.reflect.Field;
import java.util.List;

/**
 * �ֶεķ�����ʵ��
 * @author cyg
 *
 */
public  class FieldAccessor implements BaseAccessor{
	Field field;
	public FieldAccessor(Field field){
		this.field = field;
	}
	public Field getField(){
		return field;
	}
	
	@Override
	public int getInt(Object target) throws  IllegalAccessException {
		return field.getInt(target);
	}
	@Override
	public void setInt(Object target, int value) throws IllegalAccessException {
		field.setInt(target,value);
	}
	@Override
	public float getFloat(Object target) throws  IllegalAccessException {
		return field.getFloat(target);
	}
	@Override
	public void setFloat(Object target, float value) throws IllegalAccessException {
		field.setFloat(target,value);
	}
	
	@Override
	public Object get(Object target) throws IllegalAccessException {
		return field.get(target);
	}
	@Override
	public void set(Object target, Object value) throws  IllegalAccessException {
		field.set(target, value);
	}
	@Override
	public boolean getBoolean(Object target) throws  IllegalAccessException {
		return field.getBoolean(target);
	}
	@Override
	public void setBoolean(Object target, boolean value) throws  IllegalAccessException  {
		field.setBoolean(target, value);
	}
	@Override
	public void setLong(Object target, long value)  throws  IllegalAccessException {
		field.setLong(target, value);
	}
	@Override
	public long getLong(Object target) throws  IllegalAccessException  {
		return field.getLong(target);
	}
	@Override
	public String getString(Object target) throws IllegalAccessException {
		return (String)get(target);
	}
	@Override
	public void setString(Object target, String value) throws IllegalAccessException {
		set(target, value);
	}
	@Override
	public Class<?> getType() {
		return field.getType();
	}
}
