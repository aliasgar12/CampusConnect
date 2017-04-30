package campusconnect.alias.com.campusconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.LocalDatabaseHelper;
import campusconnect.alias.com.campusconnect.model.Subject;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.ui.AddCourseActivity;

/**
 * Created by alias on 4/19/2017.
 */

public class AddSubjectAdapter extends RecyclerView.Adapter<AddSubjectAdapter.ViewHolder> {
    private final String TAG = "AddSubjectAdapter";
    private AddCourseActivity addCourseActivity;
    private List<Subject> subjectList;
    private AddSubjectAdapter.ItemClickCallback itemClickCallback;
    private LocalDatabaseHelper dbHelper;
    private int userId;

    public interface ItemClickCallback {
        void OnSubjectAddIconClick(int p);
    }

    public void setItemClickCallback(AddSubjectAdapter.ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public AddSubjectAdapter(AddCourseActivity addCourseActivity, List<Subject> subjectList, int userId) {
        this.addCourseActivity = addCourseActivity;
        this.subjectList = subjectList;
        this.userId = userId;
        dbHelper = new LocalDatabaseHelper(addCourseActivity);
        dbHelper.open();
    }

    @Override
    public AddSubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(addCourseActivity).inflate(R.layout.item_add_subject, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddSubjectAdapter.ViewHolder holder, int position) {

        Subject subject = subjectList.get(position);
        String subName = subject.getSubjectName();
        int subId = subject.getSubjectCRN();
//        Set<UserDetails> userDetailsSet = subject.getStudentList();
        holder.subjectName.setText(subName);
        holder.subjectCRN.setText(String.valueOf(subId));
        if(dbHelper.doesSubjectExist(subId))
            holder.addIcon.setImageResource(R.mipmap.icon_complete);
        else
            holder.addIcon.setImageResource(R.drawable.ic_add_circle_white_36dp);


//        old method to decide the logic for subject icon
//          int count = 0;
//        for(UserDetails user: userDetailsSet) {
//            Log.i(TAG, String.valueOf(user.getUserId()));
//            if (user.getUserId() == userId)
//                count++;
//        }
//        if(count==0){
//            Log.i("Subject Adapter ", "using add_subject icon");
//            holder.addIcon.setImageResource(R.drawable.ic_add_circle_white_36dp);
//        }else {
//            Log.i("Subject Adapter ", "using subject_added_check icon");
//            holder.addIcon.setImageResource(R.mipmap.icon_complete);
//        }
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView subjectName;
        private TextView subjectCRN;
        private ImageView addIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectName = (TextView) itemView.findViewById(R.id.item_add_subject_name);
            subjectCRN = (TextView) itemView.findViewById(R.id.item_add_subject_crn);
            addIcon = (ImageView) itemView.findViewById(R.id.item_icon_add);

            addIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.item_icon_add) {
                itemClickCallback.OnSubjectAddIconClick(getAdapterPosition());
                notifyDataSetChanged();
            }
        }
    }
}
