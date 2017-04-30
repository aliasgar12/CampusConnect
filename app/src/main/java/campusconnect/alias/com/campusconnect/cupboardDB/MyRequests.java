package campusconnect.alias.com.campusconnect.cupboardDB;

/**
 * Created by alias on 4/30/2017.
 */

public class MyRequests {

    private int moduleId;
    private int fromUserId;
    private int toUserId;
    private String moduleName;

    public MyRequests(){}

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

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
