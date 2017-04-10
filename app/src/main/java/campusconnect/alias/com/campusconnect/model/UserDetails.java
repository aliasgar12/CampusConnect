package campusconnect.alias.com.campusconnect.model;

/**
 * Created by alias on 4/9/2017.
 */

import java.util.HashSet;
import java.util.Set;


public class UserDetails {

    private int userId;
    private String userName;
    private String email;
    private String password;
    private Set<Subject> subjectList = new HashSet<>();
    private Set<Module> moduleCompleted = new HashSet<>();
    private Set<Request> reqSent = new HashSet<>();
    private Set<Request> reqReceived = new HashSet<>();
    //@Embedded
    //private ChatDetails chatDetails = new ChatDetails();

    public UserDetails(){}

    //Getters and Setters

    //UserID
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    //UserName
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    //Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //	SubjectList
    public Set<Subject> getSubjectList() {
        return subjectList;
    }
    public void setSubjectList(Set<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Set<Request> getReqSent() {
        return reqSent;
    }

    public void setReqSent(Set<Request> reqSent) {
        this.reqSent = reqSent;
    }

    public Set<Request> getReqReceived() {
        return reqReceived;
    }

    public void setReqReceived(Set<Request> reqReceived) {
        this.reqReceived = reqReceived;
    }

    public Set<Module> getModuleCompleted() {
        return moduleCompleted;
    }

    public void setModuleCompleted(Set<Module> moduleCompleted) {
        this.moduleCompleted = moduleCompleted;
    }

    //ChatDetails
//	public ChatDetails getChatDetails() {
//		return chatDetails;
//	}
//
//	public void setChatDetails(ChatDetails chatDetails) {
//		this.chatDetails = chatDetails;
//	}


}

