package campusconnect.alias.com.campusconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import campusconnect.alias.com.campusconnect.model.College;

/**
 * Created by alias on 4/18/2017.
 */

public class CollegeDB {


    private String TAG = "CollegeDB";
    public static final String DB_NAME = "myDetails.db";
    public static final String TABLE_NAME = "college_details";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_NAME = "college_name";
    private static final String TABLE_CREATE_QUERY =
            "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key," + COLUMN_NAME + " text);";
    private static int DB_VERSION = 1;

    private CollegeDBUtility collegeDBUtility;
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext ;

    public CollegeDB(Context context){
        mContext = context;
        collegeDBUtility = new CollegeDBUtility(mContext);
    }

    public void open(){
        sqLiteDatabase = collegeDBUtility.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }


    public void addColleges(List<College> collegeList){
        ContentValues values = new ContentValues();

        for(College college : collegeList){
            values.put(COLUMN_ID, college.getCollegeID());
            values.put(COLUMN_NAME, college.getCollegeName());
            long id = sqLiteDatabase.insert(TABLE_NAME,null,values);
            if (-1 != id) {
                Toast.makeText(mContext, "Insert Success " + id, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "College Inserted = " college.getCollegeName());
            }
        }
    }





    public class CollegeDBUtility extends SQLiteOpenHelper{

        public CollegeDBUtility(Context context){
            super(context, DB_NAME,null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "No Tables yet . Creating them now.");
            db.execSQL(TABLE_CREATE_QUERY);
            Log.i(TAG, "Table College Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }



}
