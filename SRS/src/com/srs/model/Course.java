package com.srs.model;

import java.util.ArrayList;
import java.util.Collection;


public class Course {

	private String courseNo;
	private String courseName;
	private double credits;
	private ArrayList<Section> offeredAsSection;
	private ArrayList<Course> prerequisites;

	public Course(String cNo, String cName, double credits) {
		setCourseNo(cNo);
		setCourseName(cName);
		setCredits(credits);

		offeredAsSection = new ArrayList<Section>();
		prerequisites = new ArrayList<Course>();
	}

	/**
	 * ������޿�
	 * 
	 * @param c
	 *            Ҫ��Ϊ���޿εĿγ�
	 */
	public void addPrerequisite(Course c) {
		prerequisites.add(c);
	}

	/**
	 * �Ƿ������޿�
	 * 
	 * @return ��������
	 */
	public boolean hasPrerequisites() {
		if (prerequisites.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * ��ȡ�������޿�
	 * 
	 * @return ���������ӿڣ���Ϊ������������
	 */
	public Collection<Course> getPrerequisites() {
		return prerequisites;
	}

	/**
	 * ���ƿγ̱��������еİ����Ϣ
	 * 
	 * @param day
	 * @param time
	 * @param room
	 * @param capacity
	 * @return
	 */
	public Section scheduleSection(String day, String time, String room, int capacity) {

		Section s = new Section(offeredAsSection.size() + 1, day, time, this, room, capacity);

		addSection(s);

		return s;
	}

	/**
	 * ĳ���γ�����һ�����
	 * 
	 * @param s
	 */
	public void addSection(Section s) {
		offeredAsSection.add(s);
	}

	public void display() {
		System.out.println("Course Information:");
		System.out.println("\tCourse No.:  " + getCourseNo());
		System.out.println("\tCourse Name:  " + getCourseName());
		System.out.println("\tCredits:  " + getCredits());
		System.out.println("\tPrerequisite Courses:");

		for (Course c : prerequisites) {
			System.out.println("\t\t" + c.toString());
		}

		System.out.print("\tOffered As Section(s):  ");
		for (Section s : offeredAsSection) {
			System.out.print(s.getSectionNo() + " ");
		}

		System.out.println();
	}

	@Override
	public String toString() {
		return getCourseNo() + ":  " + getCourseName();
	}

	// ---- get set ���� ----
	public void setCourseNo(String cNo) {
		courseNo = cNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseName(String cName) {
		courseName = cName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCredits(double c) {
		credits = c;
	}

	public double getCredits() {
		return credits;
	}

}
