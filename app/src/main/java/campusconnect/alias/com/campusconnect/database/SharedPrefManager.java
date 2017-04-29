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
    private static final String KEY_USERID = "cc_userId";
    private static final String KEY_PASSWORD = "cc_password";
    private static final String KEY_EMAIL = "cc_email";
    private static final String KEY_USERNAME = "cc_userName";
    private static final String IS_LOGGED_IN = "login_status";


    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public boolean storeToken(String Token) {
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

    public boolean storeUsername(String userName){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, userName);
        editor.apply();
        return true;
    }

    public String getUserName(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public boolean storeUserId(int userId){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USERID, userId);
        editor.apply();
        return true;
    }

    public Integer getUserId(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USERID, 0);
    }

    public boolean storePassword(String password){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
        return true;
    }

    public String getPassword(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public boolean storeEmail(String email){
        SharedPreferences sharedPreferences =mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
        return true;
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public boolean saveLoginStatus(boolean status) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, status);
        editor.apply();
        return true;
    }

    public boolean getLoginStatus(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERID);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_USERNAME);
        editor.remove(IS_LOGGED_IN);
        editor.commit();

    }
}
