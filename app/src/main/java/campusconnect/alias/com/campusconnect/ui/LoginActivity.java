package campusconnect.alias.com.campusconnect.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.Subject;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.web.services.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String url = "http://localhost:8080/StudyConnect/webapi/user/login";

    @BindView(R.id.input_uid ) EditText _uid;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        if(SharedPrefManager.getInstance(this).getLoginStatus() == true){
            Log.i(TAG, "setting fields automatically and logging in");
            _uid.setVisibility(View.INVISIBLE);
            _passwordText.setVisibility(View.INVISIBLE);
            _loginButton.setVisibility(View.INVISIBLE);
            _signupLink.setVisibility(View.INVISIBLE);
            _uid.setText("0" + SharedPrefManager.getInstance(this).getUserId());
            _passwordText.setText(SharedPrefManager.getInstance(this).getPassword());
            Log.i(TAG, SharedPrefManager.getInstance(this).getPassword());
            _loginButton.performClick();
        }


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String uid = _uid.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        UserDetails user = new UserDetails();
        user.setUserId(Integer.parseInt(uid));
        user.setPassword(password);

        LoginService.Factory.getInstance().loginUser("login",user).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.code()==200) {
                    Set<Subject> subjectList = response.body().getSubjectList();
                    Log.i(TAG, "Getting Response");
                    UserDetails user = response.body();
                    for (Subject sub : subjectList)
                        Log.i(TAG, sub.getSubjectName());
                    progressDialog.dismiss();
                    onLoginSuccess(user, subjectList);
                }
                else{
                    progressDialog.dismiss();
                    onLoginFailed();
                }

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(UserDetails user , Set<Subject> subjectList) {
        _loginButton.setEnabled(true);
        if(SharedPrefManager.getInstance(this).getUserName() == null) {
            Log.i(TAG, "Setting shared preferences values for the first time");
            saveToSharedPref(user);
        }
        finish();
        Intent intent = new Intent(getBaseContext(),NavigationDrawer.class);
        intent.putExtra("subjectList", Parcels.wrap(subjectList));
        intent.putExtra("uid", user.getUserId());
        intent.putExtra("activity", TAG);
        startActivity(intent);
    }

    private void saveToSharedPref(UserDetails user) {
        SharedPrefManager.getInstance(this).storeUsername(user.getUserName());
        SharedPrefManager.getInstance(this).storeUserId(user.getUserId());
        SharedPrefManager.getInstance(this).storePassword(user.getPassword());
        SharedPrefManager.getInstance(this).storeEmail(user.getEmail());
        Log.i(TAG, "Saved shared preferences");


    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String uid = _uid.getText().toString();
        String password = _passwordText.getText().toString();

        if (uid.isEmpty() || uid.length() != 8) {
            _uid.setError("enter a valid email address");
            valid = false;
        } else {
            _uid.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
