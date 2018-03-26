package com.example.faizrehman.campus_recruitment_system.ui.Student;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.Model.Profile_Model;
import com.example.faizrehman.campus_recruitment_system.Model.UserModel;
import com.example.faizrehman.campus_recruitment_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by faiz on 1/25/2017.
 */

public class Profile_fragment extends android.support.v4.app.Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener mAuthListener;

    EditText fname, lname, email, cntactNo, address, ssc, fsc, university, password, cunPass;
    Button std_update;
    RadioButton mchkBX, fchkBX;
    Spinner spinnerSSCyear,spinnerHSCyear,spinnerDpt;
    String checkMale ;
    String checkFemale ;
    String emails,fnamee,lnamee;
    boolean flag;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_view,null);
        super.onCreateView(inflater, container, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        fname = (EditText) view.findViewById(R.id.std_first_name);
        lname = (EditText) view.findViewById(R.id.std_last_name);
        email = (EditText) view.findViewById(R.id.std_get_email);
        cntactNo = (EditText) view.findViewById(R.id.std_get_no);
        address = (EditText) view.findViewById(R.id.std_get_address);
        ssc = (EditText)view. findViewById(R.id.std_get_SSC);
        fsc = (EditText)view. findViewById(R.id.std_get_FSC);
        university = (EditText)view. findViewById(R.id.std_get_uni);
        std_update = (Button)view. findViewById(R.id.student_signUp_button);
        mchkBX = (RadioButton)view. findViewById(R.id.maleChkBox);
        fchkBX = (RadioButton)view. findViewById(R.id.femaleChkBox);
        spinnerHSCyear = (Spinner)view.findViewById(R.id.fscSpinner);
        spinnerSSCyear = (Spinner)view.findViewById(R.id.sscSpinner);
        spinnerDpt = (Spinner)view.findViewById(R.id.dptSpinner);


        myRef.child("Std-Profiles").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    fname.setEnabled(false);
                    lname.setEnabled(false);
                    email.setEnabled(false);
                    cntactNo.setEnabled(false);
                    address.setEnabled(false);
                    ssc.setEnabled(false);
                    fsc.setEnabled(false);
                    university.setEnabled(false);
                    std_update.setEnabled(false);
                    mchkBX.setEnabled(false);
                    fchkBX.setEnabled(false);
                    spinnerHSCyear.setEnabled(false);
                    spinnerSSCyear.setEnabled(false);
                    spinnerDpt.setEnabled(false);
                    std_update.setEnabled(false);

                    Profile_Model userModel = dataSnapshot.getValue(Profile_Model.class);
                    if(userModel!=null) {
                        email.setText(userModel.getEmail());
                        fname.setText(userModel.getFname());
                        lname.setText(userModel.getLname());
                        address.setText(userModel.getAddress());
                        spinnerDpt.setPrompt(userModel.getDpt());
                        cntactNo.setText(userModel.getContactno());
                        university.setText(userModel.getUniversity());
                        ssc.setText(userModel.getSsc());
                        fsc.setText(userModel.getFsc());
                        spinnerSSCyear.setPrompt(userModel.getSscyear());
                        spinnerHSCyear.setPrompt(userModel.getHscyear());
                        if (userModel.getGender().equals("Male")) {
                            mchkBX.setChecked(true);
                        } else {
                            fchkBX.setChecked(true);
                        }


                    }
                }else{
                    fname.setEnabled(false);
                    lname.setEnabled(false);
                    email.setEnabled(false);
                    cntactNo.setEnabled(true);
                    address.setEnabled(true);
                    ssc.setEnabled(true);
                    fsc.setEnabled(true);
                    university.setEnabled(true);
                    std_update.setEnabled(true);
                    mchkBX.setEnabled(true);
                    fchkBX.setEnabled(true);
                    spinnerHSCyear.setEnabled(true);
                    spinnerSSCyear.setEnabled(true);
                    spinnerDpt.setEnabled(true);
                    std_update.setEnabled(true);

                    fname.setText("");
                    lname.setText("");
                    email.setText("");
                    cntactNo.setText("");
                    address.setText("");
                    ssc.setText("");
                    fsc.setText("");
                    university.setText("");
                    std_update.setEnabled(true);
                    mchkBX.setChecked(false);
                    fchkBX.setChecked(false);
                    spinnerHSCyear.setEnabled(true);
                    spinnerSSCyear.setEnabled(true);
                    spinnerDpt.setEnabled(true);
                    std_update.setEnabled(true);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("Student").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    email.setText(userModel.getEmail());
                    fname.setText(userModel.getFname());
                    lname.setText(userModel.getLname());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            std_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                     String cntactNoo = cntactNo.getText().toString();

                     String addresss = address.getText().toString();
                     String sscc = ssc.getText().toString();
                     String fssc = fsc.getText().toString();
                     String uni = university.getText().toString();
                     String spinHSC = spinnerHSCyear.getSelectedItem().toString();
                     String spinSsc = spinnerSSCyear.getSelectedItem().toString();
                     String spindpt = spinnerDpt.getSelectedItem().toString();
                    emails = email.getText().toString();
                    fnamee = fname.getText().toString();
                    lnamee = lname.getText().toString();



                    if(mchkBX.isChecked()){
                        checkMale = "Male";
                    }else if(fchkBX.isChecked()) {
                        checkMale = "Female";
                    }else {
                        checkMale = "";
                    }

                   if (fnamee.equals("") || lnamee.equals("") || emails.equals("") || cntactNoo.equals("") || addresss.equals("") || sscc.equals("") || fssc.equals("")
                            || uni.equals("") ||  checkMale.equals("")
                            ) {
                        Toast.makeText(getActivity(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else if (email.getText().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email.setError("Enter Valid Email Address");
                    }else if(Double.parseDouble(sscc) >100 ){
                       ssc.setError("Out of Range");
                   }else if(Double.parseDouble(fssc) >100){
                        fsc.setError("Out of Range");
                    }
                   else {
                        myRef.child("Std-Profiles").child(mAuth.getCurrentUser().getUid()).setValue(new Profile_Model(fnamee, lnamee, emails, cntactNoo, addresss, sscc, fssc, uni,checkMale, spinSsc, spinHSC, spindpt,mAuth.getCurrentUser().getUid().toString()));
                    Toast.makeText(getActivity(),"Successfuly Updated",Toast.LENGTH_SHORT).show();
                       fname.setEnabled(false);
                       lname.setEnabled(false);
                       email.setEnabled(false);
                       cntactNo.setEnabled(false);
                       address.setEnabled(false);
                       ssc.setEnabled(false);
                       fsc.setEnabled(false);
                       university.setEnabled(false);
                       std_update.setEnabled(false);
                       mchkBX.setEnabled(false);
                       fchkBX.setEnabled(false);
                       spinnerHSCyear.setEnabled(false);
                       spinnerSSCyear.setEnabled(false);
                       spinnerDpt.setEnabled(false);
                       std_update.setEnabled(false);
                   }
                }
            });




        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
