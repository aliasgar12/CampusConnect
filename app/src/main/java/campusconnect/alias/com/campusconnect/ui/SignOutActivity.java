package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.RequestSentAdapter;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.web.services.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignOutActivity extends Fragment {

    final String TAG = "SignOutActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Deleting Token");
        int uid = SharedPrefManager.getInstance(getContext()).getUserId();
        new ClearToken().execute(uid);
        Log.i(TAG, "Clearing Shared Preferences");
        SharedPrefManager.getInstance(getContext()).clear();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_out,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"Signing out");
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

    public class ClearToken extends AsyncTask<Integer,Void,Void>{
        @Override
        protected Void doInBackground(Integer... params) {
            UserDetails user = new UserDetails();
            user.setUserId(params[0]);
            TokenService.Factory.getInstance().deleteToken(user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Log.i(TAG, response.message());
                        Log.i(TAG, "Token Deleted");
                    }else{
                        Log.i(TAG, String.valueOf(response.code()));
                        Log.i(TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.i(TAG, "Failure deleting token");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
