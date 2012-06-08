package com.ppsea.serialization;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * ˫����ұ�
 * @author cyg
 * @param <T>
 */
public class IndexMap<T> {
	HashMap<T, Integer> map = new HashMap<T, Integer>();
	ArrayList<T> list = new ArrayList<T>();
	
	public IndexMap() {
	
	}
	public IndexMap(T...values) {
		this();
		put(values);
	}
	public int put(T value){
		map.put(value, list.size());
		list.add(value);
		return list.size();
	}
	public int put(T...values){
		for (int i = 0; i < values.length; i++) {
			put(values[i]);
		}
		return list.size();
	}
	public T get(int index){
		return list.get(index);
	}
	public Integer indexOf(T obj){
		return map.get(obj);
	}
	
}
