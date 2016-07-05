package com.srs.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.srs.dao.SectionDao;
import com.srs.dao.TranscriptDao;
import com.srs.daoImp.SectionDaoImp;
import com.srs.daoImp.TranscriptDaoImp;
import com.srs.model.Course;
import com.srs.model.PlanOfStudy;
import com.srs.model.ScheduleOfClasses;
import com.srs.model.Section;
import com.srs.model.Student;
import com.srs.model.Transcript;
import com.srs.model.TranscriptEntry;
import com.srs.service.PlanService;
import com.srs.service.SectionService;
import com.srs.service.UserService;

import net.sf.json.JSONArray;

public class SectionAction extends ActionSupport {
	private static final long serialVersionUID = 5283293241313468644L;
	
	/**
	 * ǰ�˽���ʹ�õ�����
	 */
	private Student s;
	private JSONArray root;
	
	/**
	 * �ڲ��������õ�����
	 */
	SectionService service = new SectionService();
	HttpServletRequest request = ServletActionContext.getRequest();
	UserService ser = new UserService();
	
	/**
	 * Section���display()�ŷ���ǰ����ʾ
	 */
	@Override
	public String execute() throws Exception {
		
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		List<Section> list = new ArrayList<Section>();
		Map<String,Object>  map = new HashMap<String,Object>();
		
		//1����ʼ������section
		list = service.getSections();
		for (Section section : list) {
			SectionDao dao = new SectionDaoImp();
			//2����ʼ����Ӧ��ScheduleOfClasses
			ScheduleOfClasses soc = dao.getSchedule(section.getSectionNo());
			soc.addSection(section);
			section.setOfferedIn(soc);
			//3������section����ʾ����
			map = section.display();
			//���ӹ��ܣ�������Ѿ��ɹ�ѡ�εĿγ̿���ѡ����ѡ�������ݿ�person���������ɸ�飬
			//���������ѡ���ֶ������뵱ǰsection��ͬ����ô��map�����һ��1�����û�����һ��0
			//��ʼ��Student����ȡ��ǰ����
			String ssn = request.getParameter("ssn");
			Student stu = (Student) ser.getPerson(ssn, 2);
			List<Integer> sList = getFromPerson(stu);
			for (Integer tempNo : sList) {
				if(section.getSectionNo()==tempNo){
					//��ѡ�����Ϊ1 
					map.put("mark",1);
					break;
				}else{
					//δѡ�����Ϊ0
					map.put("mark",0);
				}
			}
			mapList.add(map);
		}
		root = JSONArray.fromObject(mapList);
		return SUCCESS;
	}
	
	/**
	 * �鿴�Ƿ���ѡ�Ĺ���
	 * @return ����Section����
	 */
	private List<Integer> getFromPerson(Student s){
		SectionDao dao = new SectionDaoImp();
		return dao.getHasSectionNo(s);
	}
	
	/**
	 * Section��enroll������ǰ����ʾ
	 * �� ѡ�εĺ��ķ���
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	public String inSection() throws IOException {
		
		//1����ʼ��Student����ȡ��ǰ����
		String ssn = request.getParameter("ssn");
		String sectionno = request.getParameter("sectionno");
		s = (Student) ser.getPerson(ssn, 2);
		
		//2����ʼ��PlanOfStudy,��ȡ��ǰ�����ѧϰ�ƻ�
		PlanOfStudy plan = new PlanOfStudy();
		PlanService pService = new PlanService();
		List<Course> list = pService.getCourses(s.getSsn());
		plan.setStudentOwner(s);
		plan.setCourses(list);
		//ѧ������ƻ��б�
		s.setPlan(plan);
		
		//3����ʼ��Transcript,��ȡ��ǰ����ĳɼ���
		Transcript t = new Transcript(s);
		TranscriptDao dao = new TranscriptDaoImp();
		List<TranscriptEntry> tList = dao.getTranscript();
		for (TranscriptEntry transcriptEntry : tList) {
			if(transcriptEntry.getStudent().getSsn().equals(s.getSsn())) 	
				t.addTranscriptEntry(transcriptEntry);
		}
		Section section = service.getSectionByNo(Integer.parseInt(sectionno));
		//���ķ���
		String msg = section.enroll(s).value();
		//�������ɹ�-->�����ݿ�������ֶΣ������� 
		if(msg.equals("����ɹ� ��  :o)")){
			try {
				Class clazz = Class.forName("com.srs.daoImp.UserDaoImp");
				Method m = clazz.getDeclaredMethod("addCourseToPerson",new Class[]{String.class,String.class});
				m.invoke(clazz.newInstance(),new String[]{ssn,sectionno});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		

		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(msg);

		return null;
	}
	
	/*	
 	public void addToPerson(String ssn,String sectionno){
		Connection conn = DBUtil.open();
		String sql = "update person set hassectionno = ? where ssn=?";
		String temp = "select hassectionno from person where ssn = ?";
		String var = "";
		try {
			PreparedStatement p = conn.prepareStatement(temp);
			p.setString(1, ssn);
			ResultSet rs = p.executeQuery();
			if(rs.next()){var = rs.getString("hassectionno");}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, var+"��"+sectionno);
			pstmt.setString(2, ssn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	 */
	
	public Student getS() {
		return s;
	}

	public void setS(Student s) {
		this.s = s;
	}

	public JSONArray getRoot() {
		return root;
	}

	public void setRoot(JSONArray root) {
		this.root = root;
	}
	
}
