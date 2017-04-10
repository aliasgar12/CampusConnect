package campusconnect.alias.com.campusconnect.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class Subject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int subjectCRN;
	private String subjectName;
	private Set<Module> modules = new HashSet<>();
	private Set<UserDetails> studentList = new HashSet<>();
	private Set<Request> requestList = new HashSet<>();
	private Department dept;
	private College college;

	public Subject() {}


	//Getters and Setters
	
	//Subject CRN
	public int getSubjectCRN() {
		return subjectCRN;
	}
	public void setSubjectCRN(int subjectCRN) {
		this.subjectCRN = subjectCRN;
	}
	
	//SubjectName
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	//ModuleList
	public Set<Module> getModules() {
		return modules;
	}
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}
	
	//UserList
	public Set<UserDetails> getStudentList() {
		return studentList;
	}
	public void setStudentList(Set<UserDetails> studentList) {
		this.studentList = studentList;
	}
	
	//RequestList
	public Set<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(Set<Request> requestList) {
		this.requestList = requestList;
	}
	
	//Department
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}

	//College
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	
	public String toString(){
		return (this.subjectCRN + " " + this.subjectName + " " + this.dept + " " + this.college);
	}
}
