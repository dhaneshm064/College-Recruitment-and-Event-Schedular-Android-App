package com.example.faizrehman.campus_recruitment_system.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.R;
import com.example.faizrehman.campus_recruitment_system.ui.Admin.AdminActivity;
import com.example.faizrehman.campus_recruitment_system.ui.Company.Company_Activity;
import com.example.faizrehman.campus_recruitment_system.ui.Student.Student_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainLogin";
    private Button adminLogin,companyLogin,studentLogin;
   private   TextView signupText;
    private android.support.v4.app.FragmentManager fragmentManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference firebase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            adminLogin = (Button)findViewById(R.id.admin_Login_btn);
            companyLogin = (Button)findViewById(R.id.company_login_btn);
            studentLogin = (Button)findViewById(R.id.student_login_btn);
            signupText = (TextView)findViewById(R.id.sign_up);
        firebase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedPreferences.edit();
        editor.clear();

        fragmentManager = getSupportFragmentManager();

            signupText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction().add(R.id.container, new signup_fragment()).addToBackStack(null).commit();
                }
            });

            adminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","admin");
                    editor.putString("TAG","admin");
                    editor.commit();
                    startActivity(intent);
                }
            });

            studentLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","student");
                    editor.putString("TAG","student");
                    editor.commit();
                    startActivity(intent);
                }
            });

            companyLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("TAG","company");
                    editor.putString("TAG","company");
                    editor.commit();
                    startActivity(intent);
                }
            });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String channel = sharedPreferences.getString("TAG", "");
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getEmail().toString().matches("admin@gmail.com")){
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        editor.clear();
                        startActivity(intent);
                        finish();
                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    }else{
                      //  AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                        checkUser(user.getUid(),channel);

                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    public void checkUser(final String uid,String usercheck){
        if(uid!=null) {
            if (usercheck.matches("company")) {
                firebase.child("Company").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.hasChild(uid)) {
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Company_Activity.class);
                                editor.clear();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else if(usercheck.matches("student")) {
                firebase.child("Student").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.hasChild(uid)) {
                                Intent intent = new Intent(MainActivity.this, Student_Activity.class);
                                editor.clear();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
