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

import com.example.faizrehman.campus_recruitment_system.Adapter.CompanyListAdapter;
import com.example.faizrehman.campus_recruitment_system.Model.UserModel;
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

public class Company_fragment extends android.support.v4.app.Fragment {

    private ListView listView;
    private CompanyListAdapter companyListAdapter;
    private ArrayList<UserModel> userModelsArray;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private String checkUser;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.company_view,null);
         super.onCreateView(inflater, container, savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checkUser = sharedPreferences.getString("TAG","");
        userModelsArray = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.company_list);
        companyListAdapter = new CompanyListAdapter(userModelsArray,getActivity());
        listView.setAdapter(companyListAdapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();


        myRef.child("Company").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UserModel userModel = dataSnapshot1.getValue(UserModel.class);
                        userModelsArray.add(new UserModel(userModel.getEmail(), userModel.getPassword(), userModel.getCpassword(), userModel.getUserID(), userModel.getFname(), userModel.getLname()));
                        companyListAdapter.notifyDataSetChanged();
                    }
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

                            myRef.child("jobs").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            if (dataSnapshot1.child("comp_email").getValue().toString().matches(userModelsArray.get(position).getEmail())) {
                                                DatabaseReference ref = dataSnapshot1.getRef();
                                                ref.removeValue();
                                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                                //  userModelsArray.remove(position);
                                                //  companyListAdapter.notifyDataSetChanged();
                                            }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });



                            myRef.child("C-jobs").child(userModelsArray.get(position).getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (i == position) {
                                            DatabaseReference ref = dataSnapshot1.getRef();
                                            ref.removeValue();
                                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                          //  userModelsArray.remove(position);
                                          //  companyListAdapter.notifyDataSetChanged();
                                        }
                                        i++;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

                            myRef.child("Company").child(userModelsArray.get(position).getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i = 0;
                                    //   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    if (i == position) {
                                        DatabaseReference ref = dataSnapshot.getRef();
                                        ref.removeValue();
                                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                        userModelsArray.remove(position);
                                        companyListAdapter.notifyDataSetChanged();
                                    }
                                    i++;
                                }
                                //    }

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
