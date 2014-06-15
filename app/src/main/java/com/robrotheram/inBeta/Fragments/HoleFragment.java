package com.robrotheram.inBeta.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.R;

/**
 * Created by robert on 12/06/2014.
 */
public class HoleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    private DataStore dataStore;

    TextView parView;
    TextView distView;
    TextView descView;


    public void setDataStore(DataStore dataStore){
        this.dataStore = dataStore;
        draw();
    }


    private void draw(){
        if ((dataStore.getHole()!=null)&&(parView!=null)){parView.setText("Par:    "+dataStore.getHole().getPar());}
        if ((dataStore.getHole()!=null)&&(distView!=null)){distView.setText("Distance To Pin:   "+dataStore.getHole().getDistanceToPin());}
        if ((dataStore.getHole()!=null)&&(descView!=null)){descView.setText("Description: \n"+ dataStore.getHole().getDescription());}

      
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_hole_page_1, container, false);
        parView = (TextView) rootView.findViewById(R.id.textView);
        distView = (TextView) rootView.findViewById(R.id.textView2);
        descView = (TextView) rootView.findViewById(R.id.textView3);
        draw();
        return rootView;
    }

    @Override
    public void onRefresh() {
       draw();
    }
}
