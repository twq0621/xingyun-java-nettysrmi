package com.ppsea.serialization;

import java.io.IOException;
/**
 * ��������͵�ϵ�л��ӿ�
 * @author user
 *
 * @param <T>
 */
public abstract class SimpleSerial<T> {
	/**
	 * ��ȡ����
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public abstract T fromStream(BeanInputStream in) throws IOException;
	/**
	 * ������д����
	 * @param obj
	 * @param out
	 * @throws IOException
	 */
	public abstract void toStream(T obj,BeanOutputStream out) throws IOException;
	/**
	 * ��ȡ��Ӧ������
	 * @return
	 */
	public abstract Class<T> getType();
	/**
	 * java  �˴�����ͷ�װ���Լ�String��ϵ�л�ʵ��
	 */
	public static final SimpleSerial<?> base[] = new SimpleSerial[]{
			new BooleanSerial(),new ByteSerial(),new ChatSerial(),new ShortSerial(),
			new IntegerSerial(),new FloatSerial(),new LongSerial(),new DoubleSerial(),
			new StringSerial()
	};
	
}
class BooleanSerial extends SimpleSerial<Boolean>{
	@Override
	public Boolean fromStream(BeanInputStream in) throws IOException {
		return in.readBoolean();
	}
	@Override
	public void toStream(Boolean obj, BeanOutputStream out) throws IOException {
		out.writeBoolean(obj);
	}
	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}
}

class ByteSerial extends SimpleSerial<Byte>{
	@Override
	public Byte fromStream(BeanInputStream in) throws IOException {
		return in.readByte();
	}
	@Override
	public void toStream(Byte obj, BeanOutputStream out) throws IOException {
		out.writeByte(obj);
	}
	@Override
	public Class<Byte> getType() {
		return Byte.class;
	}
}
class ChatSerial extends SimpleSerial<Character>{
	@Override
	public Character fromStream(BeanInputStream in) throws IOException {
		return in.readChar();
	}
	@Override
	public void toStream(Character obj, BeanOutputStream out) throws IOException {
		out.writeChar(obj);
	}
	@Override
	public Class<Character> getType() {
		return Character.class;
	}
}
class ShortSerial extends SimpleSerial<Short>{
	@Override
	public Short fromStream(BeanInputStream in) throws IOException {
		return in.readShort();
	}
	@Override
	public void toStream(Short obj, BeanOutputStream out) throws IOException {
		out.writeInt(obj);
	}
	@Override
	public Class<Short> getType() {
		return Short.class;
	}
}
class IntegerSerial extends SimpleSerial<Integer>{
	@Override
	public Integer fromStream(BeanInputStream in) throws IOException {
		return in.readInt();
	}
	@Override
	public void toStream(Integer obj, BeanOutputStream out) throws IOException {
		out.writeInt(obj);
	}
	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}

class FloatSerial extends SimpleSerial<Float>{
	@Override
	public Float fromStream(BeanInputStream in) throws IOException {
		return in.readFloat();
	}
	@Override
	public void toStream(Float obj, BeanOutputStream out) throws IOException {
		out.writeFloat(obj);
	}
	@Override
	public Class<Float> getType() {
		return Float.class;
	}
}
class LongSerial extends SimpleSerial<Long>{
	@Override
	public Long fromStream(BeanInputStream in) throws IOException {
		return in.readLong();
	}
	@Override
	public void toStream(Long obj, BeanOutputStream out) throws IOException {
		out.writeLong(obj);
	}
	@Override
	public Class<Long> getType() {
		return Long.class;
	}
}
class DoubleSerial extends SimpleSerial<Double>{
	@Override
	public Double fromStream(BeanInputStream in) throws IOException {
		return in.readDouble();
	}
	@Override
	public void toStream(Double obj, BeanOutputStream out) throws IOException {
		out.writeDouble(obj);
	}
	@Override
	public Class<Double> getType() {
		return Double.class;
	}
}
class StringSerial extends SimpleSerial<String>{
	@Override
	public String fromStream(BeanInputStream in) throws IOException {
		return in.readUTF();
	}
	@Override
	public void toStream(String obj, BeanOutputStream out) throws IOException {
		out.writeUTF(obj);
	}
	@Override
	public Class<String> getType() {
		return String.class;
	}
}

