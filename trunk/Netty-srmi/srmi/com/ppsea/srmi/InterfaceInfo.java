package com.ppsea.srmi;
/**
 * ����ӿڵ���Ϣ
 * ����
 * @author cyg
 *
 */
public class InterfaceInfo {
	Class<?> type;
	public InterfaceInfo(Class<?> type) {
		super();
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	@Override
	public String toString() {
		return type.toString();
	}
}
