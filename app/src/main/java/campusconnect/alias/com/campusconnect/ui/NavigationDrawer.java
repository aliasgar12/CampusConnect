package campusconnect.alias.com.campusconnect.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewFragment;
import android.widget.TextView;

import campusconnect.alias.com.campusconnect.R;
import campusconnect.alias.com.campusconnect.database.SharedPrefManager;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String CURRENT_TAG = "";
    private WebView myWebView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myWebView= new WebView(getBaseContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);

        TextView nameView = (TextView) v.findViewById(R.id.nameView);
        nameView.setText(SharedPrefManager.getInstance(this).getUserName());


        String uid = String.valueOf(SharedPrefManager.getInstance(this).getUserId());
        int prefixSize = 8 - uid.length();

        if (prefixSize == 0)
            uid = "U" + uid;
        else if (prefixSize == 1)
            uid = "U0" + uid;
        else if (prefixSize == 2)
            uid = "U00" + uid;

        TextView uidView = (TextView) v.findViewById(R.id.uidView);
        uidView.setText(uid);

        TextView emailView = (TextView) v.findViewById(R.id.emailView);
        emailView.setText(SharedPrefManager.getInstance(this).getEmail());

        displaySelectedScreen(R.id.nav_dashboard);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displaySelectedScreen(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_dashboard:
                fragment = new DashboardActivity();
                CURRENT_TAG = "Dashboard";
                break;
            case R.id.nav_signOut:
                fragment = new SignOutActivity();
                CURRENT_TAG = "SignOut";
                break;
            case R.id.nav_studyRoom:
                fragment = new BookStudyRoomActivity();
                CURRENT_TAG = "BookRoom";
                break;
            case R.id.nav_profile:
                fragment = new MyProfile();
                CURRENT_TAG = "Profile";
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, CURRENT_TAG);
            ft.addToBackStack(CURRENT_TAG);
            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);
        return true;
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Check it the currently existing fragment is a web view.
        if (CURRENT_TAG == "BookRoom") {
            Fragment frag = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
            View view = frag.getView();
            myWebView = (WebView) view.findViewById(R.id.webView);
        }

        // if the existing view is web view and it contains previous pages in browsing history
        // go back to the previous pages in the browsing history
        if (myWebView.canGoBack()) {
            myWebView.goBack();

        }

        // check if the navigation drawer is open . if it close the drawer
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        // if the displayed fragment is dashboard , get out of the application
        else if (CURRENT_TAG == "Dashboard") {
            int i = getSupportFragmentManager().getBackStackEntryCount();
            while (i > 0) {
                getSupportFragmentManager().popBackStack();
                i--;
            }
            super.onBackPressed();
        }
        // if the current fragment is not dashboard , go to the previous fragment in the fragment
        // backStack and assigning the name of displayed fragment's tag to the CURRENT_TAG
        else if (CURRENT_TAG != "Dashboard") {
            getSupportFragmentManager().popBackStack();
            int current = getSupportFragmentManager().getBackStackEntryCount();
            FragmentManager.BackStackEntry frag = getSupportFragmentManager().getBackStackEntryAt(current - 2);
            CURRENT_TAG = frag.getName();
            Log.i("BackStackEntry ", CURRENT_TAG);
        } else
            super.onBackPressed();
    }
}
