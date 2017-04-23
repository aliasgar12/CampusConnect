package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.SubjectAdapter;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.Subject;
import campusconnect.alias.com.campusconnect.web.services.SubjectService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity implements SubjectAdapter.ItemClickCallback{

    private static final String TAG = "DashboardActivity";
    private ArrayList<Subject> subList;
    private SubjectAdapter subjectAdapter;
    @BindView(R.id.show_request) Button myRequest;
    @BindView(R.id.add_courses) Button addCourse;
    @BindView(R.id.listSubject) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private LinearLayoutManager linearLayoutManager;
    private static int uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        subList = new ArrayList<>();

//        Log.i(TAG, SharedPrefManager.getInstance(this).getToken());


        // get intent values from previous activity
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        Set<Subject> subjectList = Parcels.unwrap(getIntent().getParcelableExtra("subjectList"));
        subList = new ArrayList<>(subjectList);
        Log.i(TAG, "Getting the subject list");
        Log.i(TAG, "Getting the subject list");
        Log.i(TAG, "Getting the subject list");

//         button listeners for myrequest and addcourse
        myRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DashboardActivity.this , RequestActivity.class);
                startActivity(intent);
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AddCourseActivity.class);
                intent.putExtra("uid", uid);
                startActivityForResult(intent,101);
            }
        });

        Log.i(TAG, "setting the adapter");
        subjectAdapter = new SubjectAdapter(this,subList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(subjectAdapter);
        subjectAdapter.setItemClickCallback(this);


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "This is onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "This is onActivityResult");
//        Log.i(TAG,String.valueOf(requestCode));
//        Log.i(TAG, String.valueOf(resultCode));

        if(requestCode == 101){
            if (resultCode == RESULT_OK){
                Log.i(TAG, "Inside the result code section");
                List<Subject> subjectAdded = Parcels.unwrap(data.getParcelableExtra("subjectAdded"));
                //        Log.i(TAG, "adding subjects");
                for(Subject sub: subjectAdded){
                    if(!subList.contains(sub)) {
                        Log.i(TAG, sub.getSubjectName()+ " not present");
                        subList.add(sub);
                    }
                }
                subjectAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void OnItemClick(int p) {
        Intent intent = new Intent(DashboardActivity.this, ModuleActivity.class);
        intent.putExtra("subjectId", subList.get(p).getSubjectCRN());
        intent.putExtra("userId",uid);
        startActivity(intent);
    }

    @Override
    public void OnDeleteItemClick(int p) {
        Subject subRemoved = subList.remove(p);
        updateServerDb(uid, subRemoved);
    }

    private void updateServerDb(int uid, final Subject subRemoved) {

        SubjectService.Factory.getInstance().deleteSubject(uid, subRemoved).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, "Subject Removed" + subRemoved.getSubjectName());
                Toast.makeText(getBaseContext(),"Subject Deleted" + subRemoved.getSubjectName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Could not connect to REST server");
            }
        });
    }
}
