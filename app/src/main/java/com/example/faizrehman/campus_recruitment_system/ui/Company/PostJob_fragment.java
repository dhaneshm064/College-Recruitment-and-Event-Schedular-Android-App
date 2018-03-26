package com.example.faizrehman.campus_recruitment_system.ui.Company;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faizrehman.campus_recruitment_system.Adapter.Job_postAdapter;
import com.example.faizrehman.campus_recruitment_system.Model.Job_Model;
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

public class PostJob_fragment extends android.support.v4.app.Fragment {

    private FloatingActionButton floatingActionButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    ArrayList<Job_Model> job_modelArrayList;
    Job_postAdapter job_postAdapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postjob_view,null);
        job_modelArrayList = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.job_list);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.add_job);
        database = FirebaseDatabase.getInstance();

        job_postAdapter = new Job_postAdapter(job_modelArrayList,getActivity());
        listView.setAdapter(job_postAdapter);
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        myRef.child("C-jobs").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                job_modelArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Job_Model job_model = dataSnapshot1.getValue(Job_Model.class);
                    job_modelArrayList.add(new Job_Model(job_model.getJob_title(),job_model.getComp_detail(),job_model.getComp_name(),job_model.getComp_email(),job_model.getCategory()));
                    job_postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = LayoutInflater.from(getActivity());
                final View view = inflater1.inflate(R.layout.addjob_view, null);

                final EditText job_title = (EditText) view.findViewById(R.id.job_title);
                final EditText comp_detail = (EditText) view.findViewById(R.id.company_location);
                final EditText comp_name = (EditText) view.findViewById(R.id.company_name);
                final EditText comp_email = (EditText) view.findViewById(R.id.company_email);
                final Spinner spinner = (Spinner) view.findViewById(R.id.Category);
                comp_email.setText(mAuth.getCurrentUser().getEmail());
                comp_email.setEnabled(false);
                comp_name.setText(mAuth.getCurrentUser().getDisplayName());
                comp_name.setEnabled(false);
                builder.setTitle("Add Jobs");
                builder.setMessage("Like to Add Jobs.. ??");
                builder.setPositiveButton("Back", null);


                builder.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                if(job_title.getText().toString().equals("")||comp_detail.getText().toString().equals("")||comp_detail.getText().toString().equals("")||spinner.getSelectedItem().equals("")){
                    Toast.makeText(getActivity(),"Please Re enter you Data.",Toast.LENGTH_SHORT).show();
                }else{
                    myRef.child("C-jobs").child(mAuth.getCurrentUser().getUid()).push().setValue(new Job_Model(job_title.getText().toString(), comp_detail.getText().toString(), comp_name.getText().toString(), comp_email.getText().toString(), spinner.getSelectedItem().toString()));
                    myRef.child("jobs").push().setValue(new Job_Model(job_title.getText().toString(), comp_detail.getText().toString(), comp_name.getText().toString(), comp_email.getText().toString(), spinner.getSelectedItem().toString()));
                    job_postAdapter.notifyDataSetChanged();
                    }
                    }
                });

                builder.setView(view);
                builder.create().show();
            }
        });




        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
