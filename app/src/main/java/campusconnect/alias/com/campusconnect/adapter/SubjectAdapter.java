package campusconnect.alias.com.campusconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.model.Subject;
import campusconnect.alias.com.campusconnect.ui.DashboardActivity;

/**
 * Created by alias on 4/10/2017.
 */

    public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder>{

    private DashboardActivity dashboardActivity;
    private ArrayList<Subject> subjectList;

    public SubjectAdapter(DashboardActivity dashboardActivity, ArrayList<Subject> subjectList){
        this.dashboardActivity = dashboardActivity;
        this.subjectList = subjectList;
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(dashboardActivity).inflate(R.layout.list_subject,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.subjectName.setText(subjectList.get(position).getSubjectName());
        holder.subjectCrn.setText(String.valueOf(subjectList.get(position).getSubjectCRN()));

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView subjectName;
        private TextView subjectCrn;
        private ImageView deleteIcon;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectName = (TextView) itemView.findViewById(R.id.item_subject_name);
            subjectCrn = (TextView)itemView.findViewById(R.id.item_subject_crn) ;
            deleteIcon = (ImageView) itemView.findViewById(R.id.item_icon_delete);
            container = itemView.findViewById(R.id.cont_root);

            subjectName.setOnClickListener(this);
            deleteIcon.setOnClickListener(this);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.cont_root){

            }else{
                
            }
        }
    }
}
