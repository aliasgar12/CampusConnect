package campusconnect.alias.com.campusconnect.model;

import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class RequestId{

	private int moduleId;
	private int fromUserId;
	private int toUserId;
	
	
	//Getter and Setters

	public int getModuleId() {return moduleId;}
	public void setModuleId(int moduleId) {this.moduleId = moduleId;}

	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}	
}