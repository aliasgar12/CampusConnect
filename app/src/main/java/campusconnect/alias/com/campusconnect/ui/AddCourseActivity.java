package campusconnect.alias.com.campusconnect.ui;

import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.DatabaseHelper;
import campusconnect.alias.com.campusconnect.model.College;
import campusconnect.alias.com.campusconnect.model.Department;
import campusconnect.alias.com.campusconnect.model.Subject;
import campusconnect.alias.com.campusconnect.web.services.AddCourseService;
import campusconnect.alias.com.campusconnect.web.services.SearchService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCourseActivity extends AppCompatActivity {

    private static final String TAG ="AddCourseActivity";
    private List<Department> deptList;
    private List<College> collegeList;
    private DatabaseHelper db;
    private Handler handler;
    private static final int STEP_ONE_COMPLETE = 0;
    private static final int STEP_TWO_COMPLETE = 1;
    private static final int STEP_THREE_COMPLETE = 2;
    @BindView(R.id.spinner_college) Spinner spinnerCollege;
    @BindView(R.id.spinner_dept) Spinner spinnerDept;
    @BindView(R.id.btn_search) Button btn_search;
    @BindView(R.id.input_subjectId) EditText subjectCRN;
    private static int collegeId;
    private static int deptId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);


        this.deleteDatabase("myDetails.db");

        //check if the database already exists
        Log.i(TAG, "Checking if the database exist");
        boolean check = doesDatabaseExist(this,"myDetails.db");

        if(!check) {

            Log.i(TAG, "Database doesn't exist");
            loadFromServer();

        }else {
            //loading college spinner and dept spinner from localdb
            Log.i(TAG, "Loading college spinner without creating db");
            loadSpinnerCollege();
        }

        //College Spinner
        spinnerCollege.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String collegeName = (String) parent.getItemAtPosition(position);
                loadSpinnerDept(collegeName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                collegeId = -1;
            }
        });


        //College Spinner
        spinnerDept.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String deptName = (String) parent.getItemAtPosition(position);

                //get dept id for the selected dept from localdb
                Log.i(TAG, "Getting dept id for clicked dept");
                db.open();
                deptId = db.getDeptId(deptName);
                db.close();
                Log.i(TAG, "Department id clicked = " + deptId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                deptId = -1;
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subCRN = subjectCRN.getText().toString();
                if(!(subCRN.isEmpty()))
                    SearchService.Factory.getInstance().getSubjectByCRN(Integer.parseInt(subCRN.toString()))
                            .enqueue(new Callback<Subject>() {
                                @Override
                                public void onResponse(Call<Subject> call, Response<Subject> response) {
                                    Log.i(TAG, "Getting Subjects by subject CRN");
                                    Subject subject = response.body();
                                    Log.i(TAG, "Fetched Subject = " + subject.getSubjectName());
                                }

                                @Override
                                public void onFailure(Call<Subject> call, Throwable t) {

                                }
                            });



            }
        });



    }




    private void loadSpinnerDept(String collegeName){
        //get collegeId for clicked college
        Log.i(TAG, "Getting Id for clicked college");
        db.open();
        collegeId = db.getCollegeId(collegeName);
        Log.i(TAG, "college clicked with id = " + collegeId);

        //get dept list and load dept spinner
        List<String> departments = db.getDeptListByCollegeId(collegeId);
        db.close();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDept.setAdapter(dataAdapter);
        db.close();
    }




    private void loadFromServer() {
        //Fetch college list from the REST API
        Log.i(TAG, "Fetching College list from REST");
        loadCollegeList();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STEP_ONE_COMPLETE:
                        //Fetch dept list from the REST API
                        Log.i(TAG, "Fetching Department list from REST");
                        loadDeptList();
                        break;
                    case STEP_TWO_COMPLETE:
                        //save to local db
                        saveToLocalDB();
                        break;
                    case STEP_THREE_COMPLETE:
                        Log.i(TAG, "Creating db and loading college spinner");
                        loadSpinnerCollege();
                }}
        };
    }

    private void loadSpinnerCollege() {

        db = new DatabaseHelper(this);
        db.open();
        List<String> colleges = db.getCollegeList();
        db.close();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colleges);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCollege.setAdapter(dataAdapter);
    }

    //check if the database exist in the localdb
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        Log.i(TAG,dbFile.toString());
        return dbFile.exists();
    }

    //saving items (college and dept list to localdb)
    private void saveToLocalDB() {
        //save college and dept list to local db if it does not exist
        Log.i(TAG, "Creating database now");
        db = new DatabaseHelper(this);
        db.open();
        Log.i(TAG, "Adding College List to localdb");
        db.addColleges(collegeList);
        Log.i(TAG, "Adding Dept List to localdb");
        db.addDepartments(deptList);
        db.close();

        //finish third step and informing handler
        Message msg = Message.obtain();
        msg.what = STEP_THREE_COMPLETE;
        handler.sendMessage(msg);

    }


    private void loadCollegeList() {

        AddCourseService.Factory.getInstance().getCollegeList().enqueue(new Callback<List<College>>() {
            @Override
            public void onResponse(Call<List<College>> call, Response<List<College>> response) {
                Log.i(TAG, "Getting response from college resource");
                collegeList = response.body();
                for (College col: collegeList)
                    Log.i(TAG, col.getCollegeName());

                // finished first step and informing handler
                Message msg = Message.obtain();
                msg.what = STEP_ONE_COMPLETE;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<College>> call, Throwable t) {
                Log.i(TAG, "Failed establishing connection with college resource");
            }
        });
    }

    private void loadDeptList() {
        AddCourseService.Factory.getInstance().getDeptList().enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                Log.i(TAG, "Getting response from dept resource");
                deptList = response.body();
                for (Department dept: deptList)
                    Log.i(TAG, (dept.getName()));

                // finished second step and informing handler
                Message msg = Message.obtain();
                msg.what = STEP_TWO_COMPLETE;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                Log.i(TAG, "Failed establishing connection with dept resource");
            }
        });
    }
}



//    /**
//     * Check if the database exist and can be read.
//     *
//     * @return true if it exists and can be read, false if it doesn't
//     */
//    private boolean checkDataBase() {
//        SQLiteDatabase checkDB = null;
//        try {
//            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
//                    SQLiteDatabase.OPEN_READONLY);
//            checkDB.close();
//        } catch (SQLiteException e) {
//            // database doesn't exist yet.
//        }
//        return checkDB != null;
//    }