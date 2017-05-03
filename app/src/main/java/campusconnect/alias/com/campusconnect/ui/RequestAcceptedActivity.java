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
import campusconnect.alias.com.campusconnect.adapter.RequestAcceptedAdapter;
import campusconnect.alias.com.campusconnect.adapter.RequestReceivedAdapter;
import campusconnect.alias.com.campusconnect.model.Request;
import campusconnect.alias.com.campusconnect.web.services.RequestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alias on 4/27/2017.
 */

public class RequestAcceptedActivity extends Fragment {
    private String TAG = "RequestAcceptedActivity";
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RequestAcceptedAdapter requestAcceptedAdapter;
    private ArrayList<Request> requests = new ArrayList<>();
    private static int uid;
    private Handler handler;
    private static final int FETCH_COMPLETE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnCreateView Loaded");
        View rootView = inflater.inflate(R.layout.tab_request_accepted, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list_request_accepted);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        requestAcceptedAdapter = new RequestAcceptedAdapter(getContext() , requests);
        recyclerView.setAdapter(requestAcceptedAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Log.i(TAG,"OnCreate ended");
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "OnActivityCreated Loaded");
        super.onActivityCreated(savedInstanceState);

        uid = getArguments().getInt("uid");
        loadAcceptedRequest();


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
                requestAcceptedAdapter.notifyDataSetChanged();
            }
        };

    }

    @Override
    public void onPause() {
        super.onPause();
        requests.clear();
    }




    private void loadAcceptedRequest() {

        RequestService.Factory.getInstance().getAcceptedRequest(uid).enqueue(new Callback<ArrayList<Request>>() {
            @Override
            public void onResponse(Call<ArrayList<Request>> call, Response<ArrayList<Request>> response) {
                if(response.code()==200) {
                    Log.i(TAG, "Got list of accepted request from server");
                    requests.addAll(response.body());
                    Message msg = Message.obtain();
                    msg.what = FETCH_COMPLETE;
                    handler.sendMessage(msg);
                }
                else
                    Toast.makeText(getActivity(),"Couldn't fetch request. Please try again later.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Request>> call, Throwable t) {
                Log.i(TAG, "Failed Getting requestList");
            }
        });

    }


    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction == ItemTouchHelper.RIGHT){
                    completeRequest(viewHolder.getAdapterPosition());
                }
            }
        };
        return simpleItemTouchCallback;
    }



    private void completeRequest(final int adapterPosition) {
        final Request request = requests.get(adapterPosition);
        RequestService.Factory.getInstance().deleteRequest(uid, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    Log.i(TAG, "Request completed" );
                    Toast.makeText(getActivity(), "Request to " + request.getToUserName()+ " completed",Toast.LENGTH_SHORT ).show();
                    requests.remove(adapterPosition);
                    requestAcceptedAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Failed to complete request",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Failed to remove request to " + request.getToUserName());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Failed to complete request");
                Toast.makeText(getActivity(), "Failed to send request to server", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
