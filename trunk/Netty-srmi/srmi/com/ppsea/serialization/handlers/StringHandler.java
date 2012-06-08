package com.ppsea.serialization.handlers;

import java.io.IOException;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;


class StringHandler extends  Handler{
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
	public void toStream(Object obj, BeanOutputStream dos)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		String value = (String)accessor.get(obj);
		if(value==null){
			dos.writeByte(0);
		}else{
			dos.writeByte(1);
			dos.writeUTF((String)accessor.get(obj));
		}
	}

	@Override
	public void fromStream(Object obj, BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		byte flag = dis.readByte();
		if(flag!=0){
			accessor.set(obj, dis.readUTF());
		}
		
	}
	
}