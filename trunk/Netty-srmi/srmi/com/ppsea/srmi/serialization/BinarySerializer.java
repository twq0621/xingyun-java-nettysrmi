package com.ppsea.srmi.serialization;

import java.lang.reflect.Method;

import com.ppsea.serialization.BeanInputStream;
import com.ppsea.serialization.BeanOutputStream;
import com.ppsea.srmi.Buffer;
import com.ppsea.srmi.ByteArrayOutputStream;
import com.ppsea.srmi.NativeMethod;
import com.ppsea.srmi.Serializer;
import com.ppsea.srmi.SharedContext;

public class BinarySerializer implements Serializer {

	
	@Override
	public Buffer methodToBuffer(SharedContext context, Method method,Object header, Object[] args) {
		//从共享上下文里获取方法的ID
		Integer index = context.getMethodIndex(method);
		if(index==null) throw new RuntimeException("方法["+method.getName()+"]没有被共享");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BeanOutputStream bos = new BeanOutputStream(context.getClassContext(), baos);
		try {
			bos.writeShort(index);
			bos.writeObject(header);
			bos.writeObject(args);
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
		
		return baos.toBuffer();
	}

	@Override
	public NativeMethod parseMethod(SharedContext context, Buffer request) {
		BeanInputStream is = new BeanInputStream(context.getClassContext(), request.asInputStream());
		try {
			int index = is.readShort();
			Method method = context.getMethod(index);
			Object header = is.readObject();
			Object args[] = (Object[]) is.readObject();
			return new NativeMethod(method, args,header);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	@Override
	public Buffer returnToBuffer(SharedContext context,Object header, Object result,
			boolean isException) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BeanOutputStream bos = new BeanOutputStream(context.getClassContext(), baos);
		try {
			bos.writeObject(header);
			bos.writeBoolean(isException);
			bos.writeObject(result);
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
		return baos.toBuffer();
	}
	@Override
	public Object parseReturn(SharedContext context, Buffer result,
			Object[] responseHeader) throws Throwable {
		BeanInputStream is = new BeanInputStream(context.getClassContext(), result.asInputStream());
		try {
			responseHeader[0] = is.readObject();
			boolean isException = is.readBoolean();
			Object obj = is.readObject();
			if(isException){
				throw (Throwable)obj;
			}else{
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}


	

}
