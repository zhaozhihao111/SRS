package com.srs.dao;

import com.srs.model.Person;
import com.srs.model.User;

public interface UserDao {
	/**
	 * ��¼
	 * @param username �˺�
	 * @param password ����
	 * @return
	 */
	public User login(String username,String password);
	/**
	 * ��ȡperson����
	 * @param ssn ѧ��
	 * @param type professor��student
	 * @return
	 */
	public Person getPerson(String ssn,int type);
	/**
	 * ��person���г��е���ѡ�ε������������ѡ�γ�
	 * 
	 * @param ssn  ѧ��
	 * @param sectionno   ��α��
	 * @return ������Ӱ������ݿ�����
	 */
	public int addCourseToPerson(String ssn,String sectionno);
}
