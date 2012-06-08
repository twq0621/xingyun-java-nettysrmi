package com.ppsea.serialization.handlers;

import java.io.IOException;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;


class BooleanHandler extends  Handler{

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
		dos.writeBoolean(accessor.getBoolean(obj));
	}

	@Override
	public void fromStream(Object obj,BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		accessor.setBoolean(obj, dis.readBoolean());
	}
	
}