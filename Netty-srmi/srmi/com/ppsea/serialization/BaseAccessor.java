package com.ppsea.serialization;

/**
 * ��(��������͵ķ�����
 * @author cyg
 *
 */
public interface BaseAccessor extends Accessor{
	
	int getInt(Object target)throws IllegalAccessException ;
	void setInt(Object target,int value)throws IllegalAccessException ;
	
	String getString(Object target)throws IllegalAccessException ;
	void setString(Object target,String value)throws IllegalAccessException ;
	
	float getFloat(Object target)throws IllegalAccessException ;
	void setFloat(Object target,float value)throws IllegalAccessException ;
	
	boolean getBoolean(Object target)throws IllegalAccessException ;
	void setBoolean(Object target,boolean value)throws IllegalAccessException ;
	
	void setLong(Object target, long value)throws IllegalAccessException;
	long getLong(Object target)throws IllegalAccessException ;
	
}
