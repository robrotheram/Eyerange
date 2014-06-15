package com.robrotheram.inBeta.Adapters;

/**
 * Created by robert on 12/06/2014.
 */




import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.robrotheram.inBeta.Constants.ListConstants.FIRST_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.SECOND_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.THIRD_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.FOURTH_COLUMN;

import com.robrotheram.inBeta.R;

public class ListAdpter extends BaseAdapter
{
    public ArrayList<HashMap> list;
    LayoutInflater layoutInflater;

    public ListAdpter(LayoutInflater layoutInflater, ArrayList<HashMap> list) {
        super();
        this.layoutInflater = layoutInflater;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;


        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.results_row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        map.get(FIRST_COLUMN);
        holder.txtFirst.setText("dd");
        holder.txtFirst.setText(map.get(FIRST_COLUMN).toString());
        holder.txtSecond.setText(map.get(SECOND_COLUMN).toString());
        holder.txtThird.setText(map.get(THIRD_COLUMN).toString());
        holder.txtFourth.setText(map.get(FOURTH_COLUMN).toString());

        return convertView;
    }

}

