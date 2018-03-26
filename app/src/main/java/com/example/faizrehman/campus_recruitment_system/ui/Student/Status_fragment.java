package com.example.faizrehman.campus_recruitment_system.ui.Student;

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
import android.widget.ListView;
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
 * Created by faizrehman on 1/25/17.
 */

public class Status_fragment extends android.support.v4.app.Fragment {

    ListView listView;

    ArrayList<Job_Model> job_modelArrayList;
    Job_postAdapter job_postAdapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    private String checkUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.status_view,null);
        super.onCreateView(inflater, container, savedInstanceState);
        job_modelArrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checkUser = sharedPreferences.getString("TAG","");

        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        listView = (ListView)view.findViewById(R.id.list_forstd);
        job_postAdapter = new Job_postAdapter(job_modelArrayList,getActivity());
        listView.setAdapter(job_postAdapter);

        myRef.child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (checkUser.matches("admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Remove");
                    builder.setMessage("You want tO Delete..?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (i == position) {
                                                DatabaseReference ref = dataSnapshot1.getRef();
                                                ref.removeValue();
                                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                                job_modelArrayList.remove(position);
                                                job_postAdapter.notifyDataSetChanged();
                                            }

                                        }
                                        i++;
                                    }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });
                        }
                    });
                    builder.setNegativeButton("Back", null);
                    builder.show();

                }
            }

});
        return view;
    }
}
