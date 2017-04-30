package campusconnect.alias.com.campusconnect.cupboardDB;

/**
 * Created by alias on 4/30/2017.
 */

public class MyModules {

    private int moduleId;
    private int subjectCRN;
    private String moduleName;
    boolean isComplete;

    public MyModules(){}

    public int getModuleId() {return moduleId;}
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getSubjectCRN() {return subjectCRN;}
    public void setSubjectCRN(int subjectCRN) {this.subjectCRN = subjectCRN;}

    public boolean isComplete() {return isComplete;}
    public void setComplete(boolean complete) {isComplete = complete;}
}
