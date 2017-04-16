package campusconnect.alias.com.campusconnect.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.adapter.ModuleAdapter;
import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.web.services.ModuleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleActivity extends AppCompatActivity implements ModuleAdapter.ItemClickCallback {


    private static final String TAG = "ModuleActiviy";
    private static int uid;
    private static int subjectId;
    private HashSet<Module> moduleList;
    private ArrayList<Module> mod;
    private ModuleAdapter moduleAdapter;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.list_module) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        ButterKnife.bind(this);

        Log.i(TAG, "Redirected to Module acitvity");
        Intent intent = getIntent();
        uid = getIntent().getIntExtra("userId", 0);
        subjectId = getIntent().getIntExtra("subjectId",0);
//        Toast.makeText(getBaseContext(), "Subject id" + subjectId, Toast.LENGTH_LONG).show();

        //Load modules for the particular subject
        loadModules(uid,subjectId);

        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);




    }

    private void loadModules(int uid, final int subjectId) {

        ModuleService.Factory.getInstance().getModules(uid, subjectId).enqueue(new Callback<HashSet<Module>>() {
            @Override
            public void onResponse(Call<HashSet<Module>> call, Response<HashSet<Module>> response) {
                Log.i(TAG, "Getting module list");
                Log.i(TAG, response.toString());
                Set<Module> moduleList = new HashSet<Module>(response.body());
                for(Module module: moduleList)
                    Log.i(TAG, module.getModuleName());
                mod = new ArrayList<Module>(moduleList);
                updateAdapter(mod);
            }

            @Override
            public void onFailure(Call<HashSet<Module>> call, Throwable t) {
                Log.i(TAG, "Failed establishing connection for module list");
            }
        });


    }

    private void updateAdapter(ArrayList<Module> modules) {
        moduleAdapter = new ModuleAdapter(this, modules);
        recyclerView.setAdapter(moduleAdapter);
        moduleAdapter.setItemClickCallback(this);
    }

    @Override
    public void OnItemClick(int p) {
        Toast.makeText(getBaseContext(),mod.get(p).getModuleName(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnModuleCompleteClick(int p) {
        Toast.makeText(getBaseContext(),mod.get(p).getModuleName() + "completed", Toast.LENGTH_LONG).show();
    }
}
