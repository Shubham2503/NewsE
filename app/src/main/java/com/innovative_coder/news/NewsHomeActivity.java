package com.innovative_coder.news;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.innovative_coder.news.Adapter.MyFragmentAdapter;
import com.innovative_coder.news.Fragment.TrendingFragment;
import com.innovative_coder.news.common.common;

public class NewsHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ViewPager viewPager;
    TabLayout tabLayout;
    MyFragmentAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_news_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("NEWS E");

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewPager);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(1);
        SharedPref sharedpr;
        sharedpr = new SharedPref(this);
        if (!sharedpr.loadNightModeState()){///Day
            toolbar.setBackgroundColor(Color.parseColor("#00BFFF"));
            tabLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            viewPager.setBackgroundColor(Color.parseColor("#ffffff"));
            navigationView.setBackgroundColor(Color.parseColor("#ffffff"));
            navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));

            Log.e("error", String.valueOf(navigationView.getItemIconTintList()));
            tabLayout.setTabTextColors(Color.parseColor("#808080"),Color.parseColor("#000000"));
        }
        else {///Night
            toolbar.setBackgroundColor(Color.parseColor("#1E90FF"));
            tabLayout.setBackgroundColor(Color.parseColor("#303030"));
            viewPager.setBackgroundColor(Color.parseColor("#303030"));
            navigationView.setBackgroundColor(Color.parseColor("#303030"));
            navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            tabLayout.setTabTextColors(Color.parseColor("#808080"),Color.parseColor("#ffffff"));
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    TrendingFragment.getInstance().LoadJson(query);
                }
                else {
                    Toast.makeText(NewsHomeActivity.this, "Type more than two letters!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settin) {
            Intent i = new Intent(NewsHomeActivity.this,settingsActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(1);

        }else if (id == R.id.nav_signIn){
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.LoginTheme)
                                .setLogo(R.mipmap.logo).build(),
                        common.SIGN_IN_REQUEST_CODE);
            }
            else {
                Toast.makeText(this, "Already Signed In", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_sources) {
            viewPager.setCurrentItem(2);

        } else if (id == R.id.nav_category) {
            viewPager.setCurrentItem(0);

        }  else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.innovative_coder.news"));
            startActivity(intent);
        }else if (id == R.id.nav_more){
            try {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:innovative._.coder")));
            }
            catch (android.content.ActivityNotFoundException anfe) {
                //replace &quot;Unified+Apps&quot; with your developer name
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:innovative._.coder")));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
