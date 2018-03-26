package com.example.faizrehman.campus_recruitment_system.ui.Company;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.Adapter.Student_Detail_Adapter;
import com.example.faizrehman.campus_recruitment_system.Model.Profile_Model;
import com.example.faizrehman.campus_recruitment_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by faiz on 1/26/2017.
 */

public class Student_detail_Fragment extends android.support.v4.app.Fragment {

    ArrayList<Profile_Model> profile_models;
    Student_Detail_Adapter adapter ;
    ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String checkUser;
    EditText fname, lname, email, cntactNo, address, ssc, fsc, university, password, cunPass;
    Button std_signUp;
    RadioButton mchkBX, fchkBX;
    Spinner spinnerSSCyear,spinnerHSCyear,spinnerDpt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_profiles,null);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        profile_models = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checkUser = sharedPreferences.getString("TAG","");
        editor = sharedPreferences.edit();
        listView = (ListView)view.findViewById(R.id.profile_list);
        adapter = new Student_Detail_Adapter(profile_models,getActivity());
        listView.setAdapter(adapter);


        myRef.child("Std-Profiles").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  Profile_Model profile_modelObject = dataSnapshot1.getValue(Profile_Model.class);
                    profile_models.add(new Profile_Model(profile_modelObject.getFname(),profile_modelObject.getLname(),profile_modelObject.getEmail(),profile_modelObject.getContactno(),profile_modelObject.getAddress(),profile_modelObject.getSsc(),profile_modelObject.getFsc(),profile_modelObject.getUniversity(),profile_modelObject.getGender(),profile_modelObject.getSscyear(),profile_modelObject.getHscyear(),profile_modelObject.getDpt(),profile_modelObject.getUid()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(checkUser.matches("admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Remove");
                    builder.setMessage("You want tO Delete..?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child("Std-Profiles").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (i == position) {
                                            DatabaseReference ref = dataSnapshot1.getRef();
                                            ref.removeValue();
                                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                            profile_models.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                        i++;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });
                        }
                    });
                    builder.setNegativeButton("Back", null);
                    builder.show();

                }else if(checkUser.matches("company")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("View Student Data");
                    builder.setMessage("Student Profile");

                    LayoutInflater layoutInflater = (LayoutInflater.from(getActivity()));
                    View viewProfile =  layoutInflater.inflate(R.layout.profileview_company_std,null);

                    fname = (EditText) viewProfile.findViewById(R.id.std_first_name);
            //        lname = (EditText) viewProfile.findViewById(R.id.std_last_name);
                    email = (EditText) viewProfile.findViewById(R.id.std_get_email);
                    cntactNo = (EditText) viewProfile.findViewById(R.id.std_get_no);
                    address = (EditText) viewProfile.findViewById(R.id.std_get_address);
                    ssc = (EditText)viewProfile. findViewById(R.id.std_get_SSC);
                    fsc = (EditText)viewProfile. findViewById(R.id.std_get_FSC);
                    university = (EditText)viewProfile. findViewById(R.id.std_get_uni);
            //        std_signUp = (Button)viewProfile. findViewById(R.id.student_signUp_button);
                    mchkBX = (RadioButton)viewProfile. findViewById(R.id.maleChkBox);
                    fchkBX = (RadioButton)viewProfile. findViewById(R.id.femaleChkBox);
                    spinnerHSCyear = (Spinner)viewProfile.findViewById(R.id.fscSpinner);
                    spinnerSSCyear = (Spinner)viewProfile.findViewById(R.id.sscSpinner);
                    spinnerDpt = (Spinner)viewProfile.findViewById(R.id.dptSpinner);

              //      std_signUp.setVisibility(View.GONE);

                    myRef.child("Std-Profiles").child(profile_models.get(position).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                fname.setEnabled(false);
                                email.setEnabled(false);
                                cntactNo.setEnabled(false);
                                address.setEnabled(false);
                                ssc.setEnabled(false);
                                fsc.setEnabled(false);
                                university.setEnabled(false);
                                mchkBX.setEnabled(false);
                                fchkBX.setEnabled(false);
                                spinnerHSCyear.setEnabled(false);
                                spinnerSSCyear.setEnabled(false);
                                spinnerDpt.setEnabled(false);

                                Profile_Model profile_model = dataSnapshot.getValue(Profile_Model.class);
                                fname.setText(profile_model.getFname());
                                //              lname.setText(profile_model.getLname());
                                email.setText(profile_model.getEmail());
                                cntactNo.setText(profile_model.getContactno());
                                address.setText(profile_model.getAddress());
                                ssc.setText(profile_model.getSsc());
                                fsc.setText(profile_model.getFsc());
                                university.setText(profile_model.getUniversity());
                                if (profile_model.getGender().matches("Male")) {
                                    mchkBX.setChecked(true);
                                } else {
                                    fchkBX.setChecked(true);
                                }
                                spinnerSSCyear.setPrompt(profile_model.getSscyear());
                                spinnerHSCyear.setPrompt(profile_model.getHscyear());
                                spinnerDpt.setPrompt(profile_model.getDpt());




                            }else{

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    builder.setPositiveButton("Back",null);
                    builder.setNeutralButton("Shotlist", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                      Profile_Model profile_model_ref = profile_models.get(position);
                       myRef.child("shortlist-std").child(mAuth.getCurrentUser().getUid()).push().setValue(new Profile_Model(profile_model_ref.getFname(),profile_model_ref.getLname(),profile_model_ref.getEmail(),profile_model_ref.getContactno(),profile_model_ref.getAddress(),profile_model_ref.getSsc(),profile_model_ref.getFsc(),profile_model_ref.getUniversity(),profile_model_ref.getGender(),profile_model_ref.getSscyear(),profile_model_ref.getHscyear(),profile_model_ref.getDpt(),profile_model_ref.getUid()));
                        }
                    });
                    builder.setView(viewProfile);
                    builder.show();
                }
                }

            });

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
