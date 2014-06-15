package com.robrotheram.inBeta.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.R;

/**
 * Created by robert on 12/06/2014.
 */
public class ImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView imageView;
    private DataStore dataStore;
    private int  imgageid = 0;
    private final int[] imagesList = new int[]{
            0,
            R.drawable.maphole1,
            R.drawable.maphole2,
            R.drawable.maphole3,
            R.drawable.maphole4,
            R.drawable.maphole5,
            R.drawable.maphole6,
            R.drawable.maphole7,
            R.drawable.maphole8,
            R.drawable.maphole9,
            R.drawable.maphole10,
            R.drawable.maphole11,
            R.drawable.maphole12,
            R.drawable.maphole13,
            R.drawable.maphole14,
            R.drawable.maphole15,
            R.drawable.maphole16,
            R.drawable.maphole17,
            R.drawable.maphole18
    };

    public void setDataStore(DataStore ds){
        this.dataStore = ds;
        draw();

    }

    public void draw(){
        if (dataStore.getHole()!=null){imgageid = imagesList[dataStore.getHole().getHoleNumber()];}
        if((imageView!=null)&&(imgageid!=0)){imageView.setImageResource(imgageid);}
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_hole_page_2, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        draw();
        return rootView;
    }

    @Override
    public void onRefresh() {
        draw();
    }
}
