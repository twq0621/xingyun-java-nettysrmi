package com.ppsea.serialization.handlers;

import java.io.IOException;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;


class ObjectHandler extends  Handler{
	Accessor accessor;
	@Override
	public Handler init(Accessor accessor) {
		this.accessor = accessor;
		return this;
	}
	@Override
	public Accessor getAccessor() {
		return accessor;
	}
	@Override
	public void toStream(Object obj, BeanOutputStream dos)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		dos.writeNextObject(accessor.get(obj));
	}

	@Override
	public void fromStream(Object obj,BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		try {
			accessor.set(obj, dis.readNextObject());
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
}