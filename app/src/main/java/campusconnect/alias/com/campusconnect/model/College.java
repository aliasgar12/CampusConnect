package campusconnect.alias.com.campusconnect.model;

import java.util.ArrayList;
import java.util.List;


public class College {

	private int collegeID;
	private String collegeName;
	private List<Department> deptList = new ArrayList<>();
	private List<Subject> subjectList = new ArrayList<>();
	
	public College(){}
	
	public int getCollegeID() {
		return collegeID;
	}
	public void setCollegeID(int collegeID) {
		this.collegeID = collegeID;
	}

	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	
	
	
}
