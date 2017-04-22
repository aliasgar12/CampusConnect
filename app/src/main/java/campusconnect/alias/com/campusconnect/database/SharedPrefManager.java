package campusconnect.alias.com.campusconnect.database;

import android.content.Context;
import android.content.SharedPreferences;

import campusconnect.alias.com.campusconnect.R;

/**
 * Created by alias on 4/21/2017.
 */

public class SharedPrefManager {

    private static Context mContext;
    private static SharedPrefManager mInstance;
    private static final String SHARED_PREF_NAME = "cc_shared_pref";
    private static final String KEY_ACCESS_TOKEN = "cc_key_token";

    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public boolean storeToken(String Token){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, Token);
        editor.apply();
        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }



}
