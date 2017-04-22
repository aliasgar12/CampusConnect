package campusconnect.alias.com.campusconnect.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import campusconnect.alias.com.campusconnect.database.SharedPrefManager;

/**
 * Created by alias on 4/21/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private String TAG = "MyFireBaseService";
    private String TOKEN_BROADCAST = "token_broadcast";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        storeToken(refreshedToken);
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));

    }

    private void storeToken(String refreshedToken) {

        SharedPrefManager.getInstance(getApplicationContext()).storeToken(refreshedToken);
    }
}
