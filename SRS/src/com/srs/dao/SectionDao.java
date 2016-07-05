package com.srs.dao;

import java.util.List;
import java.util.Map;

import com.srs.model.Professor;
import com.srs.model.ScheduleOfClasses;
import com.srs.model.Section;
import com.srs.model.Student;

public interface SectionDao {
	/**
	 * �����ݿ��л�ȡ���е�section
	 * 
	 * @return
	 */
	public List<Section> getSections();

	/**
	 * ĳ��ѧ����ָ����section����
	 * 
	 * @param s
	 * @param sectionno
	 * @return
	 */
	public int inSection(Student s, int sectionno);

	/**
	 * ��ȡscheduleOfClass
	 * 
	 * @param sectionno
	 * @return
	 */
	public ScheduleOfClasses getSchedule(int sectionno);

	/**
	 * ��ȡָ����section
	 * 
	 * @param sectionno
	 * @return
	 */
	public Section getSectionByNo(int sectionno);

	/**
	 * ��ȡĳ��ѧ����ѡ��section
	 * 
	 * @param s
	 * @return
	 */
	public List<Integer> getHasSectionNo(Student s);

	/**
	 * ��ȡĳ�����ڿ����section
	 * 
	 * @param p
	 * @return
	 */
	public List<Integer> getHasSectionNo(Professor p);

	/**
	 * ��ȡ����ѧ�������section��ŵĶ�Ӧ��ϵ��һ�Զ�
	 * 
	 * @return
	 */
	public Map<String, String> getSectionOfStudent();

	/**
	 * ���ڲ�ѯ�Լ���������Щ��
	 * 
	 * @param ssn
	 * @return
	 */
	public List<String> querySectionByPro(String ssn);

	/**
	 * ����section
	 * 
	 * @param courseNo
	 * @param sectionNo
	 * @param week
	 * @param room
	 * @param seat
	 * @param time
	 * @param ssn
	 * @return
	 */
	public int updateSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn);

	/**
	 * ��ӿγ�
	 * 
	 * @param courseNo
	 * @param sectionNo
	 * @param week
	 * @param room
	 * @param seat
	 * @param time
	 * @param ssn
	 */
	public void addSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn);

	/**
	 * ɾ���γ�
	 * 
	 * @param sectionNo
	 */
	public void delSection(String sectionNo);

}
