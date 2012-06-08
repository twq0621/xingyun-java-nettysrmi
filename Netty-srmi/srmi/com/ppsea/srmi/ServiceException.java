package com.ppsea.srmi;
/**
 * Զ�̷������õĻ��쳣
 * ���Զ�̷����׳���쳣û�б����?���׳�ServiceException
 * @author user
 *
 */
public class ServiceException extends RuntimeException {
	String msg;
	public ServiceException() {
		this("ϵͳ�쳣�����Ժ���ʣ�~");
	}
	public ServiceException(String msg) {
		super(msg);
		this.msg = msg;
	}
	@Override
	public String getMessage() {
		return msg;
	}
}
