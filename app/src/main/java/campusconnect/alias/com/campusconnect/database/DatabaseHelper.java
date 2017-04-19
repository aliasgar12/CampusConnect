package campusconnect.alias.com.campusconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import campusconnect.alias.com.campusconnect.model.College;
import campusconnect.alias.com.campusconnect.model.Department;

/**
 * Created by alias on 4/18/2017.
 */

    public class DatabaseHelper extends SQLiteOpenHelper {

        // Logcat tag
        private static final String TAG = "DatabaseHelper";

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "myDetails.db";

        // Table Names
        private static final String TABLE_COLLEGE = "collegelist";
        private static final String TABLE_DEPT = "deptlist";

        // Common column names
        private static final String KEY_COLLEGE_ID = "college_id";

        // COLLEGE Table - column names
        private static final String KEY_COLLEGE_NAME = "college_name";

        // DEPARTMENT Table - column names
        private static final String KEY_DEPT_NAME = "dept_name";
        private static final String KEY_DEPT_ID = "dept_id";

        // Database
        private SQLiteDatabase sqLiteDatabase;
        private Context mContext;


    // Table Create Statements
        // Todo table create statement
        private static final String CREATE_TABLE_COLLEGE = "CREATE TABLE "
                + TABLE_COLLEGE + "(" + KEY_COLLEGE_ID + " INTEGER PRIMARY KEY," + KEY_COLLEGE_NAME
                + " TEXT" + ")";

        // Tag table create statement
        private static final String CREATE_TABLE_DEPT = "CREATE TABLE " + TABLE_DEPT
                + "(" + KEY_DEPT_ID + " INTEGER PRIMARY KEY," + KEY_DEPT_NAME + " TEXT,"
                + KEY_COLLEGE_ID + " INTEGER" + ")";


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "No Tables yet . Creating them now.");
            // creating required tables
            db.execSQL(CREATE_TABLE_COLLEGE);
            db.execSQL(CREATE_TABLE_DEPT);
            Log.i(TAG, "Tables Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLEGE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPT);

            // create new tables
            onCreate(db);
        }


        public void open(){
            sqLiteDatabase = this.getWritableDatabase();
        }

        public void close(){
            sqLiteDatabase.close();
        }

    public void addColleges(List<College> collegeList){
        ContentValues values = new ContentValues();

        for(College college : collegeList){
            values.put(KEY_COLLEGE_ID, college.getCollegeID());
            values.put(KEY_COLLEGE_NAME, college.getCollegeName());
            long id = sqLiteDatabase.insert(TABLE_COLLEGE,null,values);
            if (-1 != id) {
                Toast.makeText(mContext, "Insert Success " + id, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "College Inserted = " + college.getCollegeName());
            }
        }
    }

    // add list of department to the database
    public void addDepartments(List<Department> deptList){
        ContentValues values = new ContentValues();

        for(Department dept : deptList){
            values.put(KEY_DEPT_ID, dept.getDeptId());
            values.put(KEY_DEPT_NAME, dept.getName());
            values.put(KEY_COLLEGE_ID, dept.getCollegeId().getCollegeID());
            long id = sqLiteDatabase.insert(TABLE_DEPT,null,values);
            if (-1 != id) {
                Toast.makeText(mContext, "Insert Success " + id, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Department Inserted = " + dept.getName());
            }
        }
    }

    // add list of colleges to the database
    public List<String> getCollegeList(){
        List<String> collegeList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COLLEGE;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                collegeList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // returning college list
        return collegeList;
    }


    //returns collegeId when college name is clicked on the spinner
    public int getCollegeId(String name){

        int collegeId = -1;
        String selectQuery = "SELECT " + KEY_COLLEGE_ID + " FROM " + TABLE_COLLEGE +
                " WHERE " + KEY_COLLEGE_NAME + " LIKE '"+ name + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        // getting collegeId for the selected row
        if (cursor != null && cursor.moveToFirst()) {
             collegeId = cursor.getInt(0);
        }
        // closing connection
        cursor.close();
        // returning college list
        return collegeId;

    }


    //returns deptId when dept name is clicked on the spinner
    public int getDeptId(String name){

        int deptId = -1;
        String selectQuery = "SELECT " + KEY_DEPT_ID + " FROM " + TABLE_DEPT +
                " WHERE " + KEY_DEPT_NAME + " LIKE '"+ name + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        // getting dept id for the selected row
        if (cursor != null && cursor.moveToFirst()) {
            deptId = cursor.getInt(0);
        }
        // closing connection
        cursor.close();
        // returning college list
        return deptId;

    }

    public List<String> getDeptListByCollegeId(int collegeId){
        List<String> deptList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEPT + " WHERE " +KEY_COLLEGE_ID+ " = " + collegeId;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                deptList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        // returning college list
        return deptList;
    }

}






//    public List<College> getCollegeList(){
//        List<College> collegeList = new ArrayList<College>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_COLLEGE;
//        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                collegeList.add(cursor.getString(KEY_COLLEGE_NAME));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        sqLiteDatabase.close();
//
//        // returning college list
//        return collegeList;
//    }

