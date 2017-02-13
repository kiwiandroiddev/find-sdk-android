package com.orchestral.findsdksample;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.orchestral.findsdksample.internal.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences sharedPreferences;
    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMenuDrawer(toolbar);

        rootView = (ViewGroup) findViewById(R.id.content);
        requestRuntimePermissionsIfNeeded();

        // Calling function to set some default values if its our first run
        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        initDefaultPreferencesIfNeeded();

        initDefaultFragment();
    }

    private void initMenuDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void requestRuntimePermissionsIfNeeded() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(SnackbarOnDeniedPermissionListener.Builder
                .with(rootView, R.string.location_permission_rationale)
                .withOpenSettingsButton(R.string.settings)
                .build())
        .check();
    }

    // Setting default values in case fo 1st run
    private void initDefaultPreferencesIfNeeded() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isFirstRun = !sharedPreferences.contains(Constants.IS_FIRST_RUN);

        if (isFirstRun) {
            editor.putString(Constants.USER_NAME, Constants.DEFAULT_USERNAME);
            editor.putString(Constants.SERVER_NAME, Constants.DEFAULT_SERVER);
            editor.putString(Constants.GROUP_NAME, Constants.DEFAULT_GROUP);
            editor.putInt(Constants.TRACK_INTERVAL, Constants.DEFAULT_TRACKING_INTERVAL);
            editor.putInt(Constants.LEARN_PERIOD, Constants.DEFAULT_LEARNING_PERIOD);
            editor.putInt(Constants.LEARN_INTERVAL, Constants.DEFAULT_LEARNING_INTERVAL);
            editor.putBoolean(Constants.IS_FIRST_RUN, false);
            editor.apply();
        }
    }

    private void initDefaultFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new LearnFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_FIND_github:
                launchBrowserWithUrl(Constants.FIND_GITHUB_URL);
                break;
            case R.id.action_app_github:
                launchBrowserWithUrl(Constants.FIND_APP_URL);
                break;
            case R.id.action_issue:
                launchBrowserWithUrl(Constants.FIND_ISSUES_URL);
                break;
            case R.id.action_Find:
                launchBrowserWithUrl(Constants.FIND_WEB_URL);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchBrowserWithUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_track) {
            fragment = new TrackFragment();
        } else {
            // Anything else is home
            fragment = new LearnFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();      // FIXME java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
