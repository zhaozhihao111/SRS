package com.srs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Section {

	private int sectionNo;
	private String dayOfWeek;
	private String timeOfDay;
	private String room;
	private int seatingCapacity;
	private Course representedCourse;
	private ScheduleOfClasses offeredIn;
	private Professor instructor;

	private HashMap<String, Student> enrolledStudents;

	private HashMap<Student, TranscriptEntry> assignedGrades;

	public Section(int sNo, String day, String time, Course course, String room, int capacity) {
		setSectionNo(sNo);
		setDayOfWeek(day);
		setTimeOfDay(time);
		setRepresentedCourse(course);
		setRoom(room);
		setSeatingCapacity(capacity);

		setInstructor(null);

		enrolledStudents = new HashMap<String, Student>();
		assignedGrades = new HashMap<Student, TranscriptEntry>();
	}

	// ѧ���Ǽǽ���ĳ�����,ѡ�κ��ķ���
	public EnrollmentStatus enroll(Student s) {
		// �Ƿ���ѧ��ѧϰ�ƻ���
		if (!inPlan(s)) {
			return EnrollmentStatus.notInPlan;
		}
		Transcript transcript = s.getTranscript();
		// �Ƿ����Ѿ��޹���ͬ�γ̣�������
		if (s.isCurrentlyEnrolledInSimilar(this) || transcript.verifyCompletion(this.getRepresentedCourse())) {
			return EnrollmentStatus.prevEnroll;
		}

		// ���޿γ��Ƿ������
		Course c = this.getRepresentedCourse();
		if (c.hasPrerequisites()) {
			// �������е����޿�
			for (Course pre : c.getPrerequisites()) {
				if (!transcript.verifyCompletion(pre)) {
					return EnrollmentStatus.prereq;
				}
			}
		}
		// �Ƿ��п�������
		if (!this.confirmSeatAvailability()) {
			return EnrollmentStatus.secFull;
		}
		enrolledStudents.put(s.getSsn(), s);
		s.addSection(this);

		return EnrollmentStatus.success;
	}

	// �ж�ѧ����ѡ����Ƿ��ڼƻ���
	private boolean inPlan(Student s) {
		for (Course course : s.getPlan().getCourses()) {
			if (this.getRepresentedCourse().getCourseNo().equals(course.getCourseNo())) {
				return true;
			}
		}
		return false;
	}

	// �ж���Ϊ�����Ƿ��㹻
	private boolean confirmSeatAvailability() {
		if (enrolledStudents.size() < getSeatingCapacity())
			return true;
		else
			return false;
	}

	// ȥ��ĳ��ѧ��
	public boolean drop(Student s) {

		if (!s.isEnrolledIn(this))
			return false;
		else {

			enrolledStudents.remove(s.getSsn());

			s.dropSection(this);
			return true;
		}
	}

	// ���������
	public int getTotalEnrollment() {
		return enrolledStudents.size();
	}

	/**
	 * ��ȡĳ��ѧ���ĳɼ�
	 * 
	 * @param s
	 * @return
	 */
	public String getGrade(Student s) {
		String grade = null;

		TranscriptEntry te = assignedGrades.get(s);
		if (te != null) {
			grade = te.getGrade();
		}

		return grade;
	}

	/**
	 * �ύĳ��ѧ���ĳɼ�
	 * 
	 * @param s
	 * @param grade
	 * @return
	 */
	public boolean postGrade(Student s, String grade) {
		// ������TanscriptEntry��֤�ɼ��Ƿ�ͨ��
		if (!TranscriptEntry.validateGrade(grade))
			return false;
		if (assignedGrades.get(s) != null)
			return false;
		TranscriptEntry te = new TranscriptEntry(s, grade, this);
		assignedGrades.put(s, te);

		return true;
	}

	public boolean isSectionOf(Course c) {
		if (c == representedCourse)
			return true;
		else
			return false;
	}

	/**
	 * ��ʾ��ǰ��section
	 * 
	 * @return
	 */
	public Map<String, Object> display() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();

		map.put("Semester", getOfferedIn().getSemester());
		map.put("CourseNo", getRepresentedCourse().getCourseNo());
		map.put("SectionNo", getSectionNo());
		map.put("Offered", getDayOfWeek() + " at " + getTimeOfDay());
		map.put("InRoom", getRoom());
		if (getInstructor() != null)
			map.put("Professor", getInstructor().getName());
		map.put("Total", getTotalEnrollment());
		for (Student s : enrolledStudents.values()) {
			list.add(s.getName());
		}
		map.put("NameList", list);
		return map;
	}

	@Override
	public String toString() {
		return getRepresentedCourse().getCourseNo() + " - " + getSectionNo() + " - " + getDayOfWeek() + " - "
				+ getTimeOfDay();
	}

	// ---- get set ���� ----
	public void setSectionNo(int no) {
		sectionNo = no;
	}

	public int getSectionNo() {
		return sectionNo;
	}

	public void setDayOfWeek(String day) {
		dayOfWeek = day;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setTimeOfDay(String time) {
		timeOfDay = time;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setInstructor(Professor prof) {
		instructor = prof;
	}

	public Professor getInstructor() {
		return instructor;
	}

	public void setRepresentedCourse(Course c) {
		representedCourse = c;
	}

	public Course getRepresentedCourse() {
		return representedCourse;
	}

	public void setRoom(String r) {
		room = r;
	}

	public String getRoom() {
		return room;
	}

	public void setSeatingCapacity(int c) {
		seatingCapacity = c;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setOfferedIn(ScheduleOfClasses soc) {
		offeredIn = soc;
	}

	public ScheduleOfClasses getOfferedIn() {
		return offeredIn;
	}
}
