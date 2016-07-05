package com.srs.dao;

import java.util.List;

import com.srs.model.Course;

public interface PlanDao {
	/**
	 * ����ѧ����ȡѧϰ�ƻ�
	 * 
	 * @param ssn
	 * @return
	 */
	public List<Course> getCourses(String ssn);

	/**
	 * ͨ��course��ȡ�γ�
	 * 
	 * @param courseNo
	 * @return
	 */
	public Course getCourseByNo(String courseNo);

}
