package com.srs.model;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Person {

	private String title;
	private String department;
	private ArrayList<Section> teaches;

	public Professor(String name, String ssn, String title, String dept) {
		super(name, ssn);

		setTitle(title);
		setDepartment(dept);

		teaches = new ArrayList<Section>();
	}

	// ǰ����ʾ�ķ���
	public List<Section> displayTeachingAssignments() {
		if (teaches.size() == 0) {
			return null;
		} else {
			return teaches;
		}
	}

	public void agreeToTeach(Section s) {
		teaches.add(s);

		s.setInstructor(this);
	}

	@Override
	public String toString() {
		return getName() + " (" + getTitle() + ", " + getDepartment() + ")";
	}

	// ----- set get ���� -----

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDepartment(String dept) {
		department = dept;
	}

	public String getDepartment() {
		return department;
	}

}
