package campusconnect.alias.com.campusconnect.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.CollegeDB;
import campusconnect.alias.com.campusconnect.model.College;
import campusconnect.alias.com.campusconnect.model.Department;
import campusconnect.alias.com.campusconnect.web.services.AddCourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCourseActivity extends AppCompatActivity {

    private static final String TAG ="AddCourseActivity";
    private List<Department> deptList;
    private List<College> collegeList;
    private CollegeDB collegeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

       //Fetch college list from the REST API
        loadCollegeList();

        //Fetch dept list from the REST API
        loadDeptList();

        //save college and dept list to local db if it does not exist
        collegeDB = new CollegeDB(this);
        collegeDB.open();
        collegeDB.addColleges(collegeList);



    }


    private void loadCollegeList() {

        AddCourseService.Factory.getInstance().getCollegeList().enqueue(new Callback<List<College>>() {
            @Override
            public void onResponse(Call<List<College>> call, Response<List<College>> response) {
                Log.i(TAG, "Getting response from college resource");
                collegeList = response.body();
                for (College col: collegeList)
                    Log.i(TAG, col.getCollegeName());
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
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                Log.i(TAG, "Failed establishing connection with dept resource");
            }
        });
    }
}
