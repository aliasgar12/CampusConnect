package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.UserDetails;



public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String url = "http://10.0.2.2:8080/StudyConnect/webapi/user/myregistration";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.input_uid) TextView _uidText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        // if shared preference has logged in set to true, redirect to dashboard activity.
        boolean login_status = SharedPrefManager.getInstance(this).getLoginStatus();
        if(login_status == true){
            Log.i(TAG, "Shared preference login status" + login_status );
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }else
            Log.i(TAG, "Shared preference login status" + login_status );


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        //validating input
        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String uid = _uidText.getText().toString();
        // TODO: Implement your own signup logic here.

        new SignMeUp().execute(url,name, email, password,uid);
        progressDialog.dismiss();

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class );
        startActivity(intent);

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String uid = _uidText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (uid.isEmpty() || uid.length() != 8) {
            _uidText.setError("UID should be 8 characters long");
            valid = false;
        } else {
            _uidText.setError(null);
        }

        return valid;
    }


    private String toJsonString(UserDetails user) {

        String result = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            //Object to JSONString
            Log.i(TAG, "converting object to json string");
            result = objectMapper.writeValueAsString(user);
            Log.i(TAG, "sending json string");
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public class SignMeUp extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            StringBuilder response = new StringBuilder();
            String name = params[1];
            String email = params[2];
            String password = params[3];
            String id = params[4];
            int uid = Integer.parseInt(id);
            JSONObject user = new JSONObject();

            try {
                user.put("userId", uid);
                user.put("userName", name);
                user.put("email", email);
                user.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i(TAG, user.toString());

            try {
                URL url = new URL(params[0]);
                Log.i(TAG,url.toString());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type","application/json" );
                connection.setRequestMethod("POST");
                connection.connect();
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(user.toString());
                osw.flush();
                osw.close();
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        response.append(inputLine + "\n");
                    in.close();
                }catch(FileNotFoundException e){
                    Log.i(TAG,"returned message empty due to exception");
                }


                int status = connection.getResponseCode();
                Log.i(TAG, "status = " + status);

                if(status == 200){
                    Log.i(TAG, "status = ok");
                    return response.toString();
                }else{
                    Log.i(TAG,"status not ok" + status);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if(response != null) {
                Log.i(TAG, "Data returned " + response);
                parseJsonResponse(response);
                onSignupSuccess();
            }else {
                Log.i(TAG, "no data returned");
                onSignupFailed();
            }
        }

        private void parseJsonResponse(String response) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                //JSON from String to Object
                UserDetails user = objectMapper.readValue(response, UserDetails.class);
                int userId = user.getUserId();
                String password = user.getPassword();
                Toast.makeText(SignupActivity.this,"userid = " + userId + " password = " + password , Toast.LENGTH_LONG).show();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
