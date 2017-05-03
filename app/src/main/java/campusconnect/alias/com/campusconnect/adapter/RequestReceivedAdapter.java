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

public class RequestReceivedAdapter extends RecyclerView.Adapter<RequestReceivedAdapter.ViewHolder> {

    private ArrayList<Request> receivedRequests = new ArrayList<>();
    private LocalDatabaseHelper dbHelper;
    private String TAG = "RequestReceivedAdapter";


    public RequestReceivedAdapter( ArrayList<Request> requests, Context context){
        this.receivedRequests = requests;
        dbHelper = new LocalDatabaseHelper(context);
        dbHelper.open();

    }

    @Override
    public RequestReceivedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RequestReceivedAdapter.ViewHolder holder, int position) {
        int moduleId = receivedRequests.get(position).getRequestId().getModuleId();
        holder.userName.setText(receivedRequests.get(position).getFromUserName());
        holder.subjectName.setText(dbHelper.getModuleNameById(moduleId));
        holder.moduleName.setText(dbHelper.getSubjectNameByModuleId(moduleId));
//        holder.userName.setText(receivedRequests.get(position).getFromUserName());
//        holder.moduleName.setText(String.valueOf(receivedRequests.get(position).getRequestId().getModuleId()));
    }

    @Override
    public int getItemCount() {
        return receivedRequests.size();
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

