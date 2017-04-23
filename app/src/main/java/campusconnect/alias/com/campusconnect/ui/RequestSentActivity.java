package campusconnect.alias.com.campusconnect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.RequestSentAdapter;
import campusconnect.alias.com.campusconnect.model.Request;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestSentActivity extends Fragment {


    private String TAG = "RequestSentActivity";
    @BindView(R.id.list_request_sent) RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestSentAdapter requestSentAdapter;
    private ArrayList<Request> requests = new ArrayList<>();
    private static int userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnCreateView Loaded");
        return inflater.inflate(R.layout.tab_request_sent, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnActivityCreated Loaded");
        super.onActivityCreated(savedInstanceState);

    }
}
