package com.ppsea.serialization.handlers;

import java.io.IOException;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;


class FloatHandler extends  Handler{
	BaseAccessor accessor;
	@Override
	public Handler init(Accessor accessor) {
		this.accessor = (BaseAccessor) accessor;
		return this;
	}
	@Override
	public Accessor getAccessor() {
		return accessor;
	}

	@Override
	public void toStream(Object obj,BeanOutputStream dos)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		dos.writeFloat(accessor.getFloat(obj));
	}

	@Override
	public void fromStream(Object obj,BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		accessor.setFloat(obj, dis.readFloat());
	}
	
}