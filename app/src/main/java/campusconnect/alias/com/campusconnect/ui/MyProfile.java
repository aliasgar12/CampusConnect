package campusconnect.alias.com.campusconnect.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;
import campusconnect.alias.com.campusconnect.model.UserDetails;
import campusconnect.alias.com.campusconnect.web.services.UpdateInfoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfile extends Fragment implements View.OnClickListener {

    @BindView(R.id.profile_name)
    TextView nameTextView;
    @BindView(R.id.profile_email)
    TextView emailTextView;
    @BindView(R.id.profile_uid)
    TextView uidTextView;
    @BindView(R.id.profile_password)
    TextView passwordTextView;
    @BindView(R.id.btn_update_info)
    Button updateButton;

    private String TAG = "MyProfile";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        ButterKnife.bind(this, view);

        String uid = String.valueOf(SharedPrefManager.getInstance(getContext()).getUserId());
        int prefixSize = 8 - uid.length();

        if (prefixSize == 0)
            uid = "U" + uid;
        else if (prefixSize == 1)
            uid = "U0" + uid;
        else if (prefixSize == 2)
            uid = "U00" + uid;

        nameTextView.setText(SharedPrefManager.getInstance(getContext()).getUserName());
        emailTextView.setText(SharedPrefManager.getInstance(getContext()).getEmail());
        uidTextView.setText(uid);
        passwordTextView.setText(SharedPrefManager.getInstance(getContext()).getPassword());
        uidTextView.setEnabled(false);
        emailTextView.setEnabled(false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update_info) {
            UserDetails user = new UserDetails();
            final String newName = nameTextView.getText().toString();
            final String newPassword = passwordTextView.getText().toString();
            user.setUserId(SharedPrefManager.getInstance(getContext()).getUserId());
            user.setUserName(newName);
            user.setPassword(newPassword);

            UpdateInfoService.Factory.getInstance().updateUser(user).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Successfully updated.... Now updating shared preferences");
                        SharedPrefManager.getInstance(getContext()).storeUsername(newName);
                        SharedPrefManager.getInstance(getContext()).storePassword(newPassword);
                        Log.i(TAG,SharedPrefManager.getInstance(getContext()).getUserName());
                        Log.i(TAG,SharedPrefManager.getInstance(getContext()).getPassword());
                        Log.i(TAG, "Shared preferences updated");
                    } else
                        Toast.makeText(getContext(), "Failed to update. Please try again", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to update. Please try again", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
