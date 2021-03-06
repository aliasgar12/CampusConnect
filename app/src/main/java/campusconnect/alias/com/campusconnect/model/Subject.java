package campusconnect.alias.com.campusconnect.model;

import android.util.Log;

import org.parceler.Parcel;

import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;

@Parcel
public class Subject{

	int subjectCRN;
	String subjectName;
//	private Set<Module> modules = new HashSet<>();
	private Set<UserDetails> studentList = new HashSet<>();
//	private Department dept;
//	private College college;

	public Subject() {}

    public Subject(int subjectCRN, String subjectName){
        this.subjectCRN = subjectCRN;
        this.subjectName = subjectName;
    }

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
	
//	//ModuleList
//	public Set<Module> getModules() {
//		return modules;
//	}
//	public void setModules(Set<Module> modules) {
//		this.modules = modules;
//	}
//
	//UserList
	public Set<UserDetails> getStudentList() {
		return studentList;
	}
	public void setStudentList(Set<UserDetails> studentList) {
		this.studentList = studentList;
	}
//

//
//	//Department
//	public Department getDept() {
//		return dept;
//	}
//	public void setDept(Department dept) {
//		this.dept = dept;
//	}
//
//	//College
//	public College getCollege() {
//		return college;
//	}
//	public void setCollege(College college) {
//		this.college = college;
//	}
//
//	public String toString(){
//		return (this.subjectCRN + " " + this.subjectName + " " + this.dept + " " + this.college);
//	}

	@Override
	public boolean equals(Object other){
		if (!(other instanceof Subject))
			return false;
		Subject otherSubject = (Subject) other;
		return this.getSubjectName().trim().equals(otherSubject.getSubjectName().trim());
	}
}
