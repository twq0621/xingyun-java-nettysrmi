package com.ppsea.serialization;

public interface Define {
	/**
	 * ����null
	 */
	int NULL = 0;//��
	int OBJECT = 1;//����
	int REFERENCE = 2;//����
	int BASE = 3;//�����Ͱ�װ
	int OBJECT_ARRAY = 4;//��������
	int BASE_ARRAY = 5;//�����Ͱ�װ������ ����(Integer,Boolean) �Լ�String
	int LIST =6;//List
	int UNKNOW = 7;
}
