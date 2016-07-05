package com.srs.model;

import java.util.ArrayList;
import java.util.List;

import com.srs.service.PlanService;

public class PlanOfStudy {

	private Student studentOwner;
	private List<Course> courses;

	// չʾ������ѡ
	public List<Course> diplay() {
		PlanService service = new PlanService();
		List<Course> list = new ArrayList<Course>();
		// ���ݿ��ȡcourse�б�
		list = service.getCourses(studentOwner.getSsn());
		// ����ǰ̨չʾ
		return list;
	}

	public PlanOfStudy() {
		courses = new ArrayList<Course>();
	}

	public Student getStudentOwner() {
		return studentOwner;
	}

	public void setStudentOwner(Student studentOwner) {
		this.studentOwner = studentOwner;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
