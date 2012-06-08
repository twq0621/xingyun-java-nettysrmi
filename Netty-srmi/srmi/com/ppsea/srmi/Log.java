package com.ppsea.srmi;


/**
 * log����
 * @author cyg
 *
 */
public class Log {
	static Throwable throwable = new Throwable();
	/**
	 * ��ӡ��Ϣ<br>
	 * ��ʽ��[����]([����])==>[��Ϣ]
	 * @param msg
	 */
	public static void info(Object msg){
		synchronized (throwable) {
			StackTraceElement te = throwable.fillInStackTrace().getStackTrace()[1];
			System.out.println(te.getFileName()+"("+te.getLineNumber()+"��)==>"+msg);
		}
	}
	/**
	 * ��ӡ������Ϣ<br>
	 * ��ʽ��[����]([����])==>[��Ϣ]
	 * @param msg
	 */
	public static void err(Object msg){
		synchronized (throwable) {
			StackTraceElement te = throwable.fillInStackTrace().getStackTrace()[1];
			System.err.println(te.getClassName()+"("+te.getLineNumber()+"��)==>"+msg);
		}
	}
	public static void main(String[] args) {
		System.out.println(String[][].class.getName());
	}
}
