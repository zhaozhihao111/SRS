package com.srs.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.srs.dao.PlanDao;
import com.srs.model.Course;
import com.srs.util.DBUtil;

public class PlanDaoImp implements PlanDao {
	private Connection conn = DBUtil.open();

	@Override
	public List<Course> getCourses(String ssn) {
		// ��ȡcourse�б�
		List<Course> courses = new ArrayList<Course>();
		String sql = "select courseno from planofstudy where ssn=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ssn);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				// ֻ��һ���ƻ�
				String courseno = rs.getString("courseno");
				String[] arr = courseno.split("��");
				for (String tempNo : arr) {
					// ��ȡcourse���󲢼����б���
					courses.add(getCourseByNo(tempNo));
				}
				return courses;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Course getCourseByNo(String courseNo) {
		Course c = null;
		String sql = "select * from course where courseno=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, courseNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				c = new Course(courseNo, rs.getString(2), rs.getDouble(3));
				if (rs.getString("prerequisites") == null) {
					// û�����޿�
					return c;
				} else {
					String preNo = rs.getString("prerequisites");
					String[] arr = preNo.split("��");
					for (String tempNo : arr) {
						// ���õݹ�ķ�ʽ��ѭ�������Լ������޿�
						c.addPrerequisite(getCourseByNo(tempNo));
					}
					return c;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
