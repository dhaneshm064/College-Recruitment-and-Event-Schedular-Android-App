package com.example.faizrehman.campus_recruitment_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.faizrehman.campus_recruitment_system.Model.UserModel;
import com.example.faizrehman.campus_recruitment_system.R;

import java.util.ArrayList;

/**
 * Created by faizrehman on 1/26/17.
 */

public class CompanyListAdapter extends BaseAdapter {

    ArrayList<UserModel> userModels;
    Context context;
    LayoutInflater layoutInflater;

    public CompanyListAdapter(ArrayList<UserModel> userModels, Context context) {
        this.userModels = userModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public Object getItem(int position) {
        return userModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.companylistvieww,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.compName = (TextView)convertView.findViewById(R.id.company_name);
             viewHolder.compDetail = (TextView)convertView.findViewById(R.id.comp_detail);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.compName.setText(userModels.get(position).getFname()+" "+userModels.get(position).getLname());
        viewHolder.compDetail.setText(userModels.get(position).getEmail());

        return convertView;
    }
    private class ViewHolder{
        TextView compName;
        TextView compDetail;
    }
}
