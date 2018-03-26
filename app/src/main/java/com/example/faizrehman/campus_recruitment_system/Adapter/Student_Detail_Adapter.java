package com.example.faizrehman.campus_recruitment_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.faizrehman.campus_recruitment_system.Model.Profile_Model;
import com.example.faizrehman.campus_recruitment_system.R;

import java.util.ArrayList;

/**
 * Created by faizrehman on 1/26/17.
 */

public class Student_Detail_Adapter extends BaseAdapter {

    ArrayList<Profile_Model> profile_models;
    Context context;
    LayoutInflater layoutInflater;

    public Student_Detail_Adapter(ArrayList<Profile_Model> profile_models, Context context) {
        this.profile_models = profile_models;
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }

    @Override
    public int getCount() {
        return profile_models.size();
    }

    @Override
    public Object getItem(int position) {
        return profile_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View view = layoutInflater.inflate(R.layout.student_detail_for_company,null);

        TextView textViewname = (TextView) view.findViewById(R.id.stud_nameforcomp);
        TextView textViewEmail  = (TextView) view.findViewById(R.id.std_emailforcomp);



        textViewname.setText("Student Name: "+profile_models.get(position).getFname()+" "+profile_models.get(position).getLname());
        textViewEmail.setText("Student Email: "+profile_models.get(position).getEmail());



        return view;
    }
}
