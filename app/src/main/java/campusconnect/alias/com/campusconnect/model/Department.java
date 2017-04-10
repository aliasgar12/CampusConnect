package campusconnect.alias.com.campusconnect.model;

import java.util.ArrayList;
import java.util.List;


public class Department {

	private int deptId;
	private String name;
	private College collegeId;
	private List<Subject> subjectList = new ArrayList<>();
	
	public Department(){}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public College getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(College collegeId) {
		this.collegeId = collegeId;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	
	
}
