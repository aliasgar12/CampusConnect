package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.ModuleAdapter;
import campusconnect.alias.com.campusconnect.database.LocalDatabaseHelper;
import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.web.services.ModuleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleActivity extends AppCompatActivity implements ModuleAdapter.ItemClickCallback {


    @BindView(R.id.list_module)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final String TAG = "ModuleActivity";
    private static int uid;
    private static int subjectId;
    private static ArrayList<Module> mod;
    private ModuleAdapter moduleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LocalDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        ButterKnife.bind(this);

        // initialize local database
        dbHelper = new LocalDatabaseHelper(this);

        Log.i(TAG, "Redirected to Module activity");
        uid = getIntent().getIntExtra("userId", 0);
        subjectId = getIntent().getIntExtra("subjectId", 0);
//        Toast.makeText(getBaseContext(), "Subject id" + subjectId, Toast.LENGTH_LONG).show();


        //Load modules for the particular subject
        loadModules(uid, subjectId);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }


    private void loadModules(int uid, final int subjectId) {
        dbHelper.open();
        ModuleService.Factory.getInstance().getModules(uid, subjectId).enqueue(new Callback<HashSet<Module>>() {
            @Override
            public void onResponse(Call<HashSet<Module>> call, Response<HashSet<Module>> response) {
                Log.i(TAG, "Getting module list");
                Log.i(TAG, response.toString());
                Set<Module> moduleList = new HashSet<>(response.body());
                mod = new ArrayList<>(moduleList);
                updateAdapter(mod);
                //check if the subject contains any modules
                if (!mod.isEmpty()) {
                    //check if the local db already has modules for the given subject
                    if (!dbHelper.doesModulesExist(subjectId)) {
                        for (Module module : moduleList) {
                            Log.i(TAG, module.getModuleName());
                            dbHelper.addModule(module.getModuleId(), module.getModuleName(), subjectId);
                        }
                        Log.i(TAG, "Modules added to local db successfully " + subjectId);
                    } else
                        Log.i(TAG, "Modules exist for the subject " + subjectId);
                } else
                    Log.i(TAG, "Modules empty for the given subject");
            }


            @Override
            public void onFailure(Call<HashSet<Module>> call, Throwable t) {
                Log.i(TAG, "Failed establishing connection for module list");
            }
        });


    }

    private void updateAdapter(ArrayList<Module> modules) {
        moduleAdapter = new ModuleAdapter(this, modules, uid);
        recyclerView.setAdapter(moduleAdapter);
        moduleAdapter.setItemClickCallback(this);
    }


    @Override
    public void OnItemClick(int p) {
        final Module moduleClicked = mod.get(p);
        final int moduleId = moduleClicked.getModuleId();
        ModuleService.Factory.getInstance().getStudents(uid, subjectId, moduleId).enqueue(new Callback<List<UserDetails>>() {
            @Override
            public void onResponse(Call<List<UserDetails>> call, Response<List<UserDetails>> response) {
                Log.i(TAG, "Getting Student list for the module " + moduleClicked.getModuleName());
                Log.i(TAG, response.message());
                List<UserDetails> studentList = response.body();
                onSuccess(uid, studentList, moduleId);

            }

            @Override
            public void onFailure(Call<List<UserDetails>> call, Throwable t) {
                Log.i(TAG, "Failed to retrieve user list for the module list" + moduleClicked.getModuleName());
                Log.i(TAG, t.getMessage());
                Log.i(TAG, t.toString());
            }
        });
    }

    @Override
    public void OnModuleCompleteClick(int p) {
        //        Toast.makeText(getBaseContext(),mod.get(p).getModuleName() + "completed", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Clicked Module = " + mod.get(p).getModuleName());
        onModuleCompletion(p);
        UserDetails userTemp = new UserDetails();
        userTemp.setUserId(uid);
        mod.get(p).getUser().add(userTemp);
        moduleAdapter.notifyDataSetChanged();
    }

    private void onModuleCompletion(int p) {
        final Module module = mod.get(p);
        ModuleService.Factory.getInstance().completedModule(uid, subjectId, module).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(TAG, "Module completed = " + module.getModuleName());
                Toast.makeText(getBaseContext(), "Module completed = " + module.getModuleName(), Toast.LENGTH_LONG).show();
                //setting module completed in local db
                dbHelper.open();
                dbHelper.moduleCompleted(module.getModuleId());
                Log.i(TAG, module.getModuleName() + " marked completed in local db");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(TAG, "Couldn't complete module. Please try again.");
                Log.i(TAG, t.getMessage());
            }
        });
    }


    public void onSuccess(int uid, List<UserDetails> studentList, int moduleId) {
        Intent intent = new Intent(getBaseContext(), StudentActivity.class);
        intent.putExtra("studentList", Parcels.wrap(studentList));
        intent.putExtra("uid", uid);
        intent.putExtra("moduleId", moduleId);
        startActivity(intent);
    }
}
