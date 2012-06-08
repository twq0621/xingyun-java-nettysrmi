package com.ppsea.serialization.handlers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.ppsea.serialization.Accessor;
import com.ppsea.serialization.BaseAccessor;
import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;
import com.ppsea.serialization.FieldAccessor;
/**
 * ϵ�л���ʵ����
 * @author cyg
 *
 */
public abstract class Handler{
	
	public static Handler getHandler(Field field) {
		field.setAccessible(true);
		return getHander(new FieldAccessor(field));
	}
	public static Handler getHander(Accessor fieldFace) {
		Class<?> type = fieldFace.getType();
		if(type==int.class||type==Integer.class){
			return new IntHandler().init(fieldFace);
		}else if(type==String.class){
			return new StringHandler().init(fieldFace);
		}else if(type==boolean.class||type==Boolean.class){
			return new BooleanHandler().init(fieldFace);
		}else if(type==float.class||type==Float.class){
			return new FloatHandler().init(fieldFace);
		}else if(type==long.class||type==Long.class){
			return new LongHandler().init(fieldFace);
		}else if(List.class.isAssignableFrom(type)){
			return new ListHandler().init(fieldFace);
		}else if(type.isArray()){
			return new ArrayHandler().init(fieldFace);
		}else {
			return new ObjectHandler().init(fieldFace);
		}
	}
	
	public abstract Accessor getAccessor();
	
	public abstract Handler init(Accessor field);
	
	public abstract void toStream(Object target,BeanOutputStream outBuff) throws IllegalArgumentException, IllegalAccessException, IOException ;
	public abstract void fromStream(Object target,BeanInputStream inBuff) throws IllegalArgumentException, IllegalAccessException, IOException ;
}
