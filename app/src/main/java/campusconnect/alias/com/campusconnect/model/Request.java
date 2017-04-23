package campusconnect.alias.com.campusconnect.model;

import org.parceler.Parcel;

@Parcel
public class Request {

	private RequestId requestId;
	private Module module;
	private UserDetails userSent;
	private UserDetails userReceived;
	private int flag;
	private String toUserName;
	private String fromUserName;

	public Request(){}

	//Getters and Setters
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public Module getModule() {return module;}
	public void setModule(Module module) {this.module = module;}

	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public RequestId getRequestId() {
		return requestId;
	}
	public void setRequestId(RequestId requestId) {
		this.requestId = requestId;
	}

	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public UserDetails getUserSent() {
		return userSent;
	}
	public void setUserSent(UserDetails userSent) {
		this.userSent = userSent;
	}

	public UserDetails getUserReceived() {
		return userReceived;
	}
	public void setUserReceived(UserDetails userReceived) {
		this.userReceived = userReceived;
	}

}
