package campusconnect.alias.com.campusconnect.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.RequestSentAdapter;
import campusconnect.alias.com.campusconnect.model.Request;
import campusconnect.alias.com.campusconnect.web.services.RequestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestSentActivity extends Fragment {

//implements RequestSentAdapter.ItemClickCallback
    private String TAG = "RequestSentActivity";
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RequestSentAdapter requestSentAdapter;
    private ArrayList<Request> requests = new ArrayList<>();
    private static int uid;
    private Handler handler;
    private static final int FETCH_COMPLETE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnCreateView Loaded");
        View rootView = inflater.inflate(R.layout.tab_request_sent, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list_request_sent);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        requestSentAdapter = new RequestSentAdapter(requests);
        recyclerView.setAdapter(requestSentAdapter);
        Log.i(TAG,"OnCreate ended");

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnActivityCreated Loaded");
        super.onActivityCreated(savedInstanceState);

        uid = getArguments().getInt("uid");
        loadSentRequest();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case FETCH_COMPLETE:
                        updateAdapter();

                }
            }

            private void updateAdapter() {
                Log.i(TAG, "Updating Adapter");
                requestSentAdapter.notifyDataSetChanged();
            }
        };

    }

    @Override
    public void onPause() {
        super.onPause();
        requests.clear();
    }

    private void loadSentRequest() {

        RequestService.Factory.getInstance().getSentRequest(uid).enqueue(new Callback<ArrayList<Request>>() {
            @Override
            public void onResponse(Call<ArrayList<Request>> call, Response<ArrayList<Request>> response) {
                if(response.code()==200) {
                    Log.i(TAG, "Got list of request from server");
                    requests.addAll(response.body());
                    Message msg = Message.obtain();
                    msg.what = FETCH_COMPLETE;
                    handler.sendMessage(msg);
                }else
                    Toast.makeText(getActivity(),"Couldn't fetch request. Please try again later.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Request>> call, Throwable t) {
                Log.i(TAG, "Failed Getting requestList");
            }
        });

    }


    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                delete(viewHolder.getAdapterPosition());
            }
        };
        return simpleItemTouchCallback;
    }

    private void delete(final int adapterPosition) {
        final Request request = requests.get(adapterPosition);
        RequestService.Factory.getInstance().deleteRequest(uid, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    Log.i(TAG, "Request removed" );
                    Toast.makeText(getActivity(), "Request to " + request.getToUserName()+ " removed",Toast.LENGTH_SHORT ).show();
                    requests.remove(adapterPosition);
                    requestSentAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Failed to remove request",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Failed to remove request to " + request.getToUserName());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Failed to complete request");
                Toast.makeText(getActivity(), "Failed to complete request to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
