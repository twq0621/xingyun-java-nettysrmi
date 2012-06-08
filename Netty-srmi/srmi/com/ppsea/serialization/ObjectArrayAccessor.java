package com.ppsea.serialization;
/**
 * ���������Accessor
 * @author cyg
 *
 */
public  class ObjectArrayAccessor implements Accessor {
	Object array = new Object[0];
	public ObjectArrayAccessor() {
	}

	@Override
	public Object get(Object target) throws IllegalAccessException {
		return target;
	}

	@Override
	public void set(Object target, Object value) throws IllegalAccessException {
	}

	@Override
	public Class<?> getType() {
		return array.getClass();
	}

}