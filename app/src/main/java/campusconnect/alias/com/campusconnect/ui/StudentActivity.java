package campusconnect.alias.com.campusconnect.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.StudentAdapter;
import campusconnect.alias.com.campusconnect.model.UserDetails;

public class StudentActivity extends AppCompatActivity  implements StudentAdapter.ItemClickCallback{


    private static int fromId;
    private ArrayList<UserDetails> studentList;
    @BindView(R.id.list_student) RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private static final String TAG = "StudentActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);

        fromId = getIntent().getIntExtra("uid", 0);
        studentList = Parcels.unwrap(getIntent().getParcelableExtra("studentList"));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        StudentAdapter studentAdapter = new StudentAdapter(this, studentList);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.setItemClickCallback(this);

    }

    @Override
    public void OnItemClick(int p) {
        Log.i(TAG, "Student Name clicked");
    }

    @Override
    public void OnRequestItemClick(int p) {
        Log.i(TAG, "I am sending a request to the desired student");
    }
}
