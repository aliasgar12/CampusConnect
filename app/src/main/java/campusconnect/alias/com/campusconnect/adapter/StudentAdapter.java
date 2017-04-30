package campusconnect.alias.com.campusconnect.adapter;

/**
 * Created by alias on 4/16/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.LocalDatabaseHelper;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.ui.StudentActivity;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

    private StudentActivity studentActivity;
    private ArrayList<UserDetails> studentList;
    private ItemClickCallback itemClickCallback;
    private int moduleId;
    private LocalDatabaseHelper dbHelper;

    public interface ItemClickCallback{
        void OnItemClick(int p);
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public StudentAdapter(StudentActivity studentActivity, ArrayList<UserDetails> studentList , int moduleId){
        this.studentActivity = studentActivity;
        this.studentList = studentList;
        this.moduleId = moduleId;
        dbHelper = new LocalDatabaseHelper(studentActivity);
        dbHelper.open();
    }

    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(studentActivity).inflate(R.layout.item_student,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        UserDetails student = studentList.get(position);
        holder.studentName.setText(student.getUserName());
        if(dbHelper.doesRequestExist(moduleId, student.getUserId()))
            holder.requestIcon.setVisibility(View.VISIBLE);
        }

    @Override
    public int getItemCount() {
        return studentList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView studentName;
        private ImageView requestIcon;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            studentName = (TextView) itemView.findViewById(R.id.item_student_name);
            requestIcon = (ImageView) itemView.findViewById(R.id.item_icon_request);
            container = itemView.findViewById(R.id.cont_root_student);

            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.cont_root_student){
                itemClickCallback.OnItemClick(getAdapterPosition());
                requestIcon.setVisibility(View.VISIBLE);
            }
        }
    }
}
