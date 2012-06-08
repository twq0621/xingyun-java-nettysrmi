package com.ppsea.serialization.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;
import com.ppsea.serialization.ObjectArrayAccessor;


public class ListHandler extends  Handler{
	Accessor accessor;
	public static final ListHandler objectListHandler = new ListHandler().init(new ObjectArrayAccessor());
	@Override
	public ListHandler init(Accessor accessor) {
		this.accessor =  accessor;
		return this;
	}
	@Override
	public Accessor getAccessor() {
		return accessor;
	}
	@Override
	public void toStream(Object obj, BeanOutputStream dos)
			throws IllegalArgumentException, IllegalAccessException, IOException {
		try{
			List<?> list = (List<?>) accessor.get(obj);
			dos.writeInt(list.size());
			for (Object o:list) {
				dos.writeNextObject(o);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public List<Object> fromStream(BeanInputStream dis) throws IOException, IllegalArgumentException, IllegalAccessException {
		return fromStream(null, dis);
	}
	@Override
	public void fromStream(Object obj,BeanInputStream dis)
		throws IllegalArgumentException, IllegalAccessException, IOException {
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) accessor.get(obj);
		List<Object> newList = fromStream(list, dis);
		if(newList!=list){
			accessor.set(obj, newList);
		}
	}
	
	public List<Object> fromStream(List<Object> list,BeanInputStream dis) throws IOException, IllegalArgumentException, IllegalAccessException {
		if(list==null) list = new ArrayList<Object>();
		int count = dis.readInt();
		for (int i = 0; i < count; i++) {
			try {
				list.add(dis.readNextObject());
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
}