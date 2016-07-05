package com.srs.model;

public enum EnrollmentStatus {
	success("����ɹ� ��  :o)"), 
	secFull("����ʧ�� ; �ð��������  :op"), 
	prereq("����ʧ�� ; ���޿γ̲����㡣   :op"), 
	prevEnroll("����ʧ�� ; ��֮ǰ�μӹ��ÿγ̣�   :op"),
	notInPlan("����ʧ��;����ѧϰ�ƻ���û�иÿγ̣�  :op");
	
	private final String value;
		EnrollmentStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
