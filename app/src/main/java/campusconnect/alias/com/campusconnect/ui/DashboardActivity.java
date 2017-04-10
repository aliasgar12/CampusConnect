package campusconnect.alias.com.campusconnect.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

import campusconnect.alias.com.campusconnect.R;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listSubject;
    private Button button;
    private static final String URL =
            "http://10.0.2.2:8080/StudyConnect/webapi/user/4512783/subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listSubject = (ListView) findViewById(R.id.listSubject);
        button = (Button) findViewById(R.id.fetch_subject_button);

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
//                parseJsonResponse(response);
            System.out.println(response);
            }
        }
    }

//    private void parseJsonResponse(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray bookArray = jsonObject.getJSONArray("bookstore");
//
//            for (int i = 0; i < bookArray.length(); i++) {
//                JSONObject bookObject = bookArray.getJSONObject(i);
//
//                String name = bookObject.getString("name");
//                String author = bookObject.getString("author");
//                String price = bookObject.getString("price");
//
//                books.add(new Book(name, author, price));
//            }
//
//            booksAdapter = new BookAdapter(MainActivity.this, books);
//            mViews.bookList.setAdapter(booksAdapter);
//            mViews.bookList.setVisibility(View.VISIBLE);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
