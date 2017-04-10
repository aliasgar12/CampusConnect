package campusconnect.alias.com.campusconnect.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.SubjectAdapter;
import campusconnect.alias.com.campusconnect.model.Subject;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listSubject;
    private Button button;
    private static final String URL =
            "http://10.0.2.2:8080/StudyConnect/webapi/user/4590583/subject";
    private ArrayList<Subject> subList;
    private SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listSubject = (ListView) findViewById(R.id.listSubject);
        button = (Button) findViewById(R.id.fetch_subject_button);
        subList = new ArrayList<Subject>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new mySubjectListTask().execute(URL);
            }
        });

    }

    private class mySubjectListTask extends AsyncTask<String,Void,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                java.net.URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder stringBuilder = new StringBuilder();
                String tempString;

                while ((tempString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tempString);
                    stringBuilder.append("\n");
                }

                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String response) {

            if(response!=null){
                parseJsonResponse(response);
            System.out.println(response);
            }
        }
    }

    private void parseJsonResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            TypeReference<List<Subject>> mapType = new TypeReference<List<Subject>>() {};
            List<Subject> subjectList = objectMapper.readValue(response, mapType);
            System.out.println("Length of the list : " + subjectList.size());
            for (int i = 0; i < subjectList.size(); i++) {
                Subject subjectObject = subjectList.get(i);

                int crn = subjectObject.getSubjectCRN();
                String subjectName = subjectObject.getSubjectName();
                subList.add(new Subject(crn, subjectName));
            }
            subjectAdapter = new SubjectAdapter(DashboardActivity.this,subList);
            listSubject.setAdapter(subjectAdapter);
            listSubject.setVisibility(View.VISIBLE);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
