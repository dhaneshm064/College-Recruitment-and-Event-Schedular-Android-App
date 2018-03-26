package com.example.faizrehman.campus_recruitment_system.ui.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.faizrehman.campus_recruitment_system.Adapter.TabAdapter;
import com.example.faizrehman.campus_recruitment_system.R;
import com.example.faizrehman.campus_recruitment_system.ui.Company.Student_detail_Fragment;
import com.example.faizrehman.campus_recruitment_system.ui.MainActivity;
import com.example.faizrehman.campus_recruitment_system.ui.Student.Company_fragment;
import com.example.faizrehman.campus_recruitment_system.ui.Student.Status_fragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayListl;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);
        editor = sharedPreferences.edit();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        fragmentArrayListl = new ArrayList<>();
        tabLayout.addTab(tabLayout.newTab().setText("Companies"));
        tabLayout.addTab(tabLayout.newTab().setText("Jobs"));
        tabLayout.addTab(tabLayout.newTab().setText("Students Profile"));

        Company_fragment company_fragment = new Company_fragment();
        Status_fragment status_fragment = new Status_fragment();
        Student_detail_Fragment profile_fragment = new Student_detail_Fragment();
        fragmentArrayListl.add(company_fragment);
        fragmentArrayListl.add(status_fragment);
        fragmentArrayListl.add(profile_fragment);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragmentArrayListl);
        //is line se tablayout k neche jo shade araaha hai woh change hoga pageviewer k mutabik
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setOffscreenPageLimit(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Exit !!");
                builder.setMessage("you want Logout..??");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(AdminActivity.this,MainActivity.class);
                        editor.clear();
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setPositiveButton("Back",null);
                builder.create().show();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }
}
