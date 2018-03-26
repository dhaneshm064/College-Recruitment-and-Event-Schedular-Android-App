package com.example.faizrehman.campus_recruitment_system.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.AppLogs;
import com.example.faizrehman.campus_recruitment_system.Model.UserModel;
import com.example.faizrehman.campus_recruitment_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by faizrehman on 1/25/17.
 */

public class signup_fragment extends android.support.v4.app.Fragment {

    private EditText email,userID,password,confirmpass,fname,lname;
     private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    private Spinner memberShiptype;
    private static final String[] Gender = new String[]{
            "Company", "Student"
    };
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.signup_view,null);
        super.onCreateView(inflater, container, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        editor.clear();
        email = (EditText) view.findViewById(R.id.editText_email);
        userID = (EditText) view.findViewById(R.id.editText_userID);
        password = (EditText) view.findViewById(R.id.editText_password);
        confirmpass = (EditText) view.findViewById(R.id.editText_cpassword);
        fname = (EditText) view.findViewById(R.id.editText_fname);
        lname = (EditText) view.findViewById(R.id.editText_lname);
        signup = (Button)view.findViewById(R.id.signup_btn);
        memberShiptype = (Spinner) view.findViewById(R.id.editGender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Gender);
        memberShiptype.setAdapter(adapter);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                final String type = memberShiptype.getSelectedItem().toString();
                final String confrim_passwordd = confirmpass.getText().toString();
                                //Checking the length of pasword while registering new USER;
                                        if (pass.length() <= 6) {
                                        main(pass);
                                    }else if(!pass.matches(confrim_passwordd)){
                                        Toast.makeText(getActivity(),"Something Wrong in Password",Toast.LENGTH_SHORT).show();
                                        confirmpass.setError("Pass not match");
                                    }else if(( fname.getText().toString().equals("")
                                                || lname.getText().toString().equals("")
                                                || userID.getText().toString().equals("")
                                                || pass.equals("")
                                                || confrim_passwordd.equals("")) ){
                                        Toast.makeText(getActivity(),"Fields Should not be left Empty",Toast.LENGTH_SHORT).show();

                                            }
                              else if(email.getText().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){
                                        email.setError("Enter Valid Email Address");
                                    }
                              else if(fname.getText().length()== 0 || !fname.getText().toString().matches("[a-zA-Z ]+")){
                                        fname.setError("Invalid Name");
                                    }
                              else if(lname.getText().length() == 0 || !lname.getText().toString().matches("[a-zA-Z ]+")){
                                        lname.setError("Invalid Name");
                                    }
                //Checking the length of pasword while registering new USER;
               else if (pass.length() <= 6) {
                    main(pass);
                } else {
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String uid = mAuth.getCurrentUser().getUid();
                                        firebase.child(type).child(uid).setValue(new UserModel(email.getText().toString(),pass,confrim_passwordd,uid,fname.getText().toString(),lname.getText().toString()));
//                                                if (task.isSuccessful()) {
                                      //  firebase.child("User").child(uid).setValue();
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                        AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());
                                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                                                               getActivity().getSupportFragmentManager()
                                                                               .beginTransaction().
                                                                               remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                                                         }
//                                                } else
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup",e.getMessage());

                            }
                        });

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
            }
        });






        return view;
    }
    private void main(String pass) {

                        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();
    }


}
