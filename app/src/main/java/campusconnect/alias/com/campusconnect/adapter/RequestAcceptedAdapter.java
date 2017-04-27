package campusconnect.alias.com.campusconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.model.Request;

/**
 * Created by alias on 4/27/2017.
 */

public class RequestAcceptedAdapter extends RecyclerView.Adapter<RequestAcceptedAdapter.ViewHolder> {

    private ArrayList<Request> acceptedRequests = new ArrayList<>();
    private String TAG = "RequestAcceptedAdapter";


    public RequestAcceptedAdapter( ArrayList<Request> requests){
        this.acceptedRequests = requests;
    }


    @Override
    public RequestAcceptedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//      View v = LayoutInflater.from(requestSentActivity).inflate(R.layout.item_request,parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RequestAcceptedAdapter.ViewHolder holder, int position) {
        holder.userName.setText(acceptedRequests.get(position).getFromUserName());
        holder.moduleName.setText(String.valueOf(acceptedRequests.get(position).getRequestId().getModuleId()));
    }

    @Override
    public int getItemCount() {
        return acceptedRequests.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView subjectName;
        private TextView moduleName;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_request_userName);
            subjectName = (TextView) itemView.findViewById(R.id.item_subject_name);
            moduleName = (TextView) itemView.findViewById(R.id.item_request_moduleName);
            container = itemView.findViewById(R.id.cont_root_request);

//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v.getId() == R.id.cont_root_request) {
//                        itemClickCallback.OnItemClick(getAdapterPosition());
//
//                    }
//                }
//
//            });
        }
    }
}

