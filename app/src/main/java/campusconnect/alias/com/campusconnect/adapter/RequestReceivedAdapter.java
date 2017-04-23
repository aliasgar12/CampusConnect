package campusconnect.alias.com.campusconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestReceivedAdapter extends RecyclerView.Adapter<RequestReceivedAdapter.ViewHolder> {


    @Override
    public RequestReceivedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RequestReceivedAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
