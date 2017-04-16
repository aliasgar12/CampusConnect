//package campusconnect.alias.com.campusconnect.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import org.json.JSONArray;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import campusconnect.alias.com.campusconnect.R;
//import campusconnect.alias.com.campusconnect.model.Subject;
//import campusconnect.alias.com.campusconnect.ui.DashboardActivity;
//
///**
// * Created by alias on 4/10/2017.
// */
//
//public class SubjectAdapter extends BaseAdapter {
//
//    private DashboardActivity dashboardActivity;
//    private ArrayList<Subject> subjectList;
//
//    public SubjectAdapter(DashboardActivity dashboardActivity, ArrayList<Subject> subjectList) {
//        this.dashboardActivity = dashboardActivity;
//        this.subjectList= subjectList;
//    }
//
//    @Override
//    public int getCount() {
//        return subjectList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Subject subject = subjectList.get(position);
//        int crn = subject.getSubjectCRN();
//        String subjectName = subject.getSubjectName();
//
//        View view = LayoutInflater.from(dashboardActivity).inflate(R.layout.item_subject,null);
//        TextView crnTextView = (TextView)view.findViewById(R.id.item_subject_crn);
//        TextView subjectTextView = (TextView)view.findViewById(R.id.item_subject_name);
//        crnTextView.setText(String.valueOf(crn));
//        subjectTextView.setText(subjectName);
//
//        return view;
//    }
//}
