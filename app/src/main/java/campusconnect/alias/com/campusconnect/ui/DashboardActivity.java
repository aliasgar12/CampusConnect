package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.SubjectAdapter;
import campusconnect.alias.com.campusconnect.model.Subject;

import static android.R.attr.button;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private ArrayList<Subject> subList;
    private SubjectAdapter subjectAdapter;
    @BindView(R.id.show_request) Button myRequest;
    @BindView(R.id.add_courses) Button addCourse;
    @BindView(R.id.listSubject) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        subList = new ArrayList<Subject>();

        // get intent values from previous activity
        Intent intent = getIntent();
        int uid = intent.getIntExtra("uid", 0);
        Set<Subject> subjectList = Parcels.unwrap(getIntent().getParcelableExtra("subjectList"));
        subList = new ArrayList<>(subjectList);
        Log.i(TAG, "Getting the subject list");
        Log.i(TAG, "Getting the subject list");
        Log.i(TAG, "Getting the subject list");

//         button listeners for myrequest and addcourse
        myRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        subjectAdapter = new SubjectAdapter(this,subList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(subjectAdapter);


    }




}
