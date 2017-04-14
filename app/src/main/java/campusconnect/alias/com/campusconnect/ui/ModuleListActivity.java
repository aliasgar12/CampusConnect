package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import campusconnect.alias.com.campusconnect.R;

public class ModuleListActivity extends AppCompatActivity {

    private static final String TAG = "ModuleListActiviy";
    private static int uid;
    private static int subjectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        Log.i(TAG, "Redirected to Module acitvity");
        Intent intent = getIntent();
        uid = getIntent().getIntExtra("userId", 0);
        subjectId = getIntent().getIntExtra("subjectId",0);
        Toast.makeText(getBaseContext(), "Subject id" + subjectId, Toast.LENGTH_LONG).show();
    }
}
