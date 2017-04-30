package campusconnect.alias.com.campusconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.LocalDatabaseHelper;
import campusconnect.alias.com.campusconnect.model.Request;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestSentAdapter extends RecyclerView.Adapter<RequestSentAdapter.ViewHolder> {

    private ArrayList<Request> sentRequests = new ArrayList<>();
    private String TAG = "RequestSentAdapter";
    private LocalDatabaseHelper dbHelper;

    public RequestSentAdapter( ArrayList<Request> requests, Context context) {
        this.sentRequests = requests;
        dbHelper = new LocalDatabaseHelper(context);
        dbHelper.open();
    }

    @Override
    public RequestSentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//      View v = LayoutInflater.from(requestSentActivity).inflate(R.layout.item_request,parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RequestSentAdapter.ViewHolder holder, int position) {

        int moduleId = sentRequests.get(position).getRequestId().getModuleId();
        holder.userName.setText(sentRequests.get(position).getToUserName());
        holder.subjectName.setText(dbHelper.getModuleNameById(moduleId));
        holder.moduleName.setText(dbHelper.getSubjectNameByModuleId(moduleId));
//        old way to access data
//        holder.userName.setText(sentRequests.get(position).getToUserName());
//        holder.subjectName.setText(String.valueOf(sentRequests.get(position).getRequestId().getModuleId()));
//        holder.moduleName.setText(String.valueOf(sentRequests.get(position).getRequestId().getModuleId()));
    }

    @Override
    public int getItemCount() {
        return sentRequests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView subjectName;
        private TextView moduleName;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_request_userName);
            subjectName = (TextView) itemView.findViewById(R.id.item_request_subjectName);
            moduleName = (TextView) itemView.findViewById(R.id.item_request_moduleName);
            container = itemView.findViewById(R.id.cont_root_request);

        }
    }
}

