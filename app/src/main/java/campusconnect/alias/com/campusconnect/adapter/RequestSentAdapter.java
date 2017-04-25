package campusconnect.alias.com.campusconnect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.model.Request;
import campusconnect.alias.com.campusconnect.ui.RequestSentActivity;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestSentAdapter extends RecyclerView.Adapter<RequestSentAdapter.ViewHolder> {

    private ArrayList<Request> sentRequests = new ArrayList<>();
    private ItemClickCallback itemClickCallback;
    private String TAG = "RequestSentAdapter";


    public RequestSentAdapter( ArrayList<Request> requests){
        this.sentRequests = requests;
    }

    public interface ItemClickCallback{
        void OnItemClick(int p);
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }


    @Override
    public RequestSentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//      View v = LayoutInflater.from(requestSentActivity).inflate(R.layout.item_request,parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RequestSentAdapter.ViewHolder holder, int position) {
        holder.userName.setText(sentRequests.get(position).getToUserName());
        holder.moduleName.setText(String.valueOf(sentRequests.get(position).getRequestId().getModuleId()));
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
            subjectName = (TextView) itemView.findViewById(R.id.item_subject_name);
            moduleName = (TextView) itemView.findViewById(R.id.item_request_moduleName);
            container = itemView.findViewById(R.id.cont_root_request);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.cont_root_request) {
                        itemClickCallback.OnItemClick(getAdapterPosition());

                    }
                }

            });
        }
    }
}

