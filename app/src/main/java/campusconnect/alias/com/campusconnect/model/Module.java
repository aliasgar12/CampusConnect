package campusconnect.alias.com.campusconnect.model;

import java.util.HashSet;
import java.util.Set;


public class Module {

	private int moduleId;
	private String moduleName;
	private Subject subjectId; // subjectID
	Set<UserDetails> user = new HashSet<>();

	//Getters and Setters
	public Subject getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Subject subjectId) {
		this.subjectId = subjectId;
	}

	public Set<UserDetails> getUser() {
		return user;
	}
	public void setUser(Set<UserDetails> user) {
		this.user = user;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
}
