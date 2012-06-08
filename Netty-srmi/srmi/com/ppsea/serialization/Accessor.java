package com.ppsea.serialization;

import java.lang.reflect.Method;
/**
 * ���Եķ�����
 * @author cyg
 *
 */
public interface Accessor{
	Object get(Object target)throws IllegalAccessException ;
	void set(Object target,Object value)throws IllegalAccessException ;
	Class<?> getType();
}
