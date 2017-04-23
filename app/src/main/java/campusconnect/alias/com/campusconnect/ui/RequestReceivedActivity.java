package campusconnect.alias.com.campusconnect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import campusconnect.alias.com.campusconnect.R;

/**
 * Created by alias on 4/23/2017.
 */

public class RequestReceivedActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_request_received, container, false);
    }
}
