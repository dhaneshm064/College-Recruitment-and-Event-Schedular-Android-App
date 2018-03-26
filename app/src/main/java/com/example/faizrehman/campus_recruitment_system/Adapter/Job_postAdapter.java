package com.example.faizrehman.campus_recruitment_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.faizrehman.campus_recruitment_system.Model.Job_Model;
import com.example.faizrehman.campus_recruitment_system.R;

import java.util.ArrayList;

/**
 * Created by faizrehman on 1/26/17.
 */

public class Job_postAdapter extends BaseAdapter {

    private ArrayList<Job_Model> job_modelArrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView jobtitle;
    private TextView comp_detail, comp_name, comp_email, comp_cat;

    public Job_postAdapter(ArrayList<Job_Model> job_modelArrayList, Context context) {
        this.job_modelArrayList = job_modelArrayList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return job_modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return job_modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.job_views, null);
        comp_cat = (TextView) view.findViewById(R.id.comp_jobcat);
        comp_email = (TextView) view.findViewById(R.id.comp_jobemail);
        comp_name = (TextView) view.findViewById(R.id.comp_namee);
        comp_detail = (TextView) view.findViewById(R.id.comp_detail);
        jobtitle = (TextView) view.findViewById(R.id.job_name);

        comp_name.setText("Compny Name: "+job_modelArrayList.get(position).getComp_name());
        comp_email.setText("Email: "+job_modelArrayList.get(position).getComp_email());
        comp_cat.setText("Categories: "+job_modelArrayList.get(position).getCategory());
        jobtitle.setText(job_modelArrayList.get(position).getJob_title());
        comp_detail.setText("Location: "+job_modelArrayList.get(position).getComp_detail());


        return view;
    }

}
