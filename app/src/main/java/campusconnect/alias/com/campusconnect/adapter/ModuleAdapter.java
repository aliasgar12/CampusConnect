package campusconnect.alias.com.campusconnect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.model.Module;
import campusconnect.alias.com.campusconnect.ui.ModuleActivity;

/**
 * Created by alias on 4/14/2017.
 */

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder>{

    private ModuleActivity moduleActivity;
    private ArrayList<Module> moduleList;
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback{
        void OnItemClick(int p);
        void OnModuleCompleteClick(int p);
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public ModuleAdapter(ModuleActivity moduleActivity, ArrayList<Module> moduleList){
        this.moduleActivity = moduleActivity;
        this.moduleList = moduleList;
    }

    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(moduleActivity).inflate(R.layout.item_module,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ModuleAdapter.ViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.moduleName.setText(module.getModuleName());
        holder.moduleId.setText(String.valueOf(module.getModuleId()));

    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView moduleName;
        private TextView moduleId;
        private ImageView completeIcon;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);

            moduleName = (TextView) itemView.findViewById(R.id.item_module_name);
            moduleId = (TextView)itemView.findViewById(R.id.item_moduleId) ;
            completeIcon = (ImageView) itemView.findViewById(R.id.item_icon_complete);
            container = itemView.findViewById(R.id.cont_root_module);

            moduleName.setOnClickListener(this);
            completeIcon.setOnClickListener(this);
            container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.item_module_name){
                itemClickCallback.OnItemClick(getAdapterPosition());
            }else if(v.getId()== R.id.item_icon_complete){
                itemClickCallback.OnModuleCompleteClick(getAdapterPosition());
            }
        }
    }
}
