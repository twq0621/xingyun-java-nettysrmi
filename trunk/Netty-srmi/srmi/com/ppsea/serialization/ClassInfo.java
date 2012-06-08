package com.ppsea.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.ppsea.serialization.FieldAccessor;
import com.ppsea.serialization.handlers.Handler;

/**
 * 鍏变韩鐨勭被鍨嬩俊?? 鐢ㄤ簬甯姪搴忓垪鍖栧?? 鍒濆鍖栫殑锟�?浼氭牴鎹瓹lass鐨勫瓧娈典俊鎭壘鍒板搴旂殑绯诲垪鍖栨柟??
 * 
 * @author xingyun
 */
public class ClassInfo {
	public static int UNKNOW_CLASS = Integer.MAX_VALUE;
	Handler[] handers;
	int classIndex;
	Class<?> clazz;
	SharedClasses context;

	// ShareContext context;
	public ClassInfo(SharedClasses context, Class<?> clazz) {
		this.clazz = clazz;
		this.context = context;
	}

	public void loadClassInfo() {
		Class<?> clazz = this.clazz;
		Integer classIndex = context.getClassIndex(clazz);
		this.classIndex = classIndex;
		List<Handler> list = new ArrayList<Handler>(
				clazz.getDeclaredFields().length);
		
		while (classIndex !=null) {
			Field field[] = clazz.getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				Field f = field[i];
				int m = f.getModifiers();
				if ((m & Modifier.FINAL) != 0) {
					continue;
				}
				if(f.getAnnotation(Ignore.class)!=null){
					continue;
				}
				list.add(Handler.getHandler(f));
			}
			clazz = clazz.getSuperclass();
			classIndex = context.getClassIndex(clazz);
		}

		handers = new Handler[list.size()];
		handers = list.toArray(handers);
		Arrays.sort(handers, new Comparator<Handler>() {
			@Override
			public int compare(Handler f1, Handler f2) {
				return ((FieldAccessor)(f1.getAccessor())).field.getName()
						.compareTo(((FieldAccessor)(f2.getAccessor())).field.getName());
			}
		});
	}


	public Handler[] getHanders() {
		return handers;
	}

	public int getClassIndex() {
		return classIndex;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Object newInstance() throws InstantiationException, IllegalAccessException {
		try {
			return clazz.newInstance();
		} catch (IllegalAccessException e) {
			// try use private constructor
			try {
				Constructor<?> constructor = clazz.getDeclaredConstructor();
				constructor.setAccessible(true);
				return constructor.newInstance();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw e;
			}
		}
	}
}
