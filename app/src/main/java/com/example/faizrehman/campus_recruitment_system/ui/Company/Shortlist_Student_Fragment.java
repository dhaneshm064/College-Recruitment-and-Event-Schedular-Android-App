package com.example.faizrehman.campus_recruitment_system.ui.Company;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
 * Created by faiz on 2/4/2017.
 */

public class Shortlist_Student_Fragment extends android.support.v4.app.Fragment {

    private ListView listView_shortList;
    private ArrayList<Profile_Model> profile_modelArrayList;
    private Student_Detail_Adapter  student_detail_adapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference firebase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shortlist_std_view,null);
        super.onCreateView(inflater, container, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        listView_shortList = (ListView)view.findViewById(R.id.short_list_std);
        profile_modelArrayList = new ArrayList<>();
        student_detail_adapter = new Student_Detail_Adapter(profile_modelArrayList,getActivity());
        listView_shortList.setAdapter(student_detail_adapter);


        firebase.child("shortlist-std").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile_modelArrayList.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                 Profile_Model profile_model = dataSnapshot1.getValue(Profile_Model.class);
                    profile_modelArrayList.add(profile_model);
                    student_detail_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         listView_shortList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                     builder.setTitle("Remove");
                     builder.setMessage("Remove Student From Shortlist ..!!");
                     builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             firebase.child("shortlist-std").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                     int i = 0;
                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                         if (i == position) {
                                             DatabaseReference ref = dataSnapshot1.getRef();
                                             ref.removeValue();
                                             Toast.makeText(getActivity(), "Remove From ShortList", Toast.LENGTH_SHORT).show();
                                             profile_modelArrayList.remove(position);
                                             student_detail_adapter.notifyDataSetChanged();
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

                 }

         });


        return view;

    }
}
