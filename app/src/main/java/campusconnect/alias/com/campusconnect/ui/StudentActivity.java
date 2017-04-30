package campusconnect.alias.com.campusconnect.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.StudentAdapter;
import campusconnect.alias.com.campusconnect.database.LocalDatabaseHelper;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.Request;
import campusconnect.alias.com.campusconnect.model.RequestId;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.web.services.RequestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity implements StudentAdapter.ItemClickCallback {


    private static int fromId;
    private static int moduleId;
    private ArrayList<UserDetails> studentList;
    @BindView(R.id.list_student)
    RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private static final String TAG = "StudentActivity";
    private LocalDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);

        fromId = getIntent().getIntExtra("uid", 0);
        moduleId = getIntent().getIntExtra("moduleId", 0);
        studentList = Parcels.unwrap(getIntent().getParcelableExtra("studentList"));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        studentAdapter = new StudentAdapter(this, studentList , moduleId);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.setItemClickCallback(this);

        //initialize local db
        dbHelper = new LocalDatabaseHelper(this);

    }

    @Override
    public void OnItemClick(int p) {
        Log.i(TAG, "Student Name clicked");
        UserDetails userTo = studentList.get(p);
        Toast.makeText(this, userTo.getUserName() + " clicked", Toast.LENGTH_LONG).show();
        RequestId requestId = new RequestId();
        requestId.setFromUserId(fromId);
        requestId.setToUserId(studentList.get(p).getUserId());
        requestId.setModuleId(moduleId);
        Request request = new Request();
        request.setToUserName(userTo.getUserName());
        request.setFromUserName(SharedPrefManager.getInstance(this).getUserName());
        request.setFlag(0);
        request.setRequestId(requestId);
        sendRequest(request);
    }

    private void sendRequest(final Request request) {
        RequestService.Factory.getInstance().addRequest(fromId, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, response.message());
                if (response.code() == 200) {
                    Toast.makeText(getBaseContext(), "Request successfully created", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Request successfully created");
                    addRequestToLocalDb(request.getRequestId().getModuleId(), request.getRequestId().getToUserId());
                } else if (response.code() == 409) {
                    Toast.makeText(getBaseContext(), "Request already exist", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getBaseContext(), "Request Failed", Toast.LENGTH_SHORT).show();

            }

            private void addRequestToLocalDb(int moduleId, int toUserId) {
                dbHelper.open();
                if (!dbHelper.doesRequestExist(moduleId, toUserId)) {
                    dbHelper.addRequest(moduleId, toUserId);
                    Log.i(TAG, "Request Added to local db");
                } else
                    Log.i(TAG, "Request Already exist in local db");
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Request creation failed");
            }
        });
    }

}
