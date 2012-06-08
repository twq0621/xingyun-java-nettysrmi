package com.ppsea.srmi;

import java.io.IOException;
import java.io.OutputStream;

public class Buffer {
	byte []buff;
	int offset;
	int length;
	public Buffer(byte[] buff, int offset, int length) {
		super();
		this.buff = buff;
		this.offset = offset;
		this.length = length;
	}
	public Buffer(byte[] buff) {
		this(buff,0,buff.length);
	}
	public byte[] getData(){
		return buff;
	}
	
	public void reset(int start,int size){
		this.offset = start;
		this.length = size;
	}
	public void writeToStream(OutputStream out) throws IOException {
		out.write(buff, offset, length);
	}
	public ByteArrayInputStream asInputStream(){
		return new ByteArrayInputStream(buff, 0, length);
	}
	public int getOffset() {
		return offset;
	}
	public int getLength() {
		return length;
	}
	
}
