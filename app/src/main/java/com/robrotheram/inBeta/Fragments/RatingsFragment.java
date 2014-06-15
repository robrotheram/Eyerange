package com.robrotheram.inBeta.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.robrotheram.inBeta.Adapters.ListAdpter;
import com.robrotheram.inBeta.BackgroundActivies.BackgroundImage;
import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.CourseRankings;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.DataStore.Rankings;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import static com.robrotheram.inBeta.Constants.ListConstants.FIRST_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.SECOND_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.THIRD_COLUMN;
import static com.robrotheram.inBeta.Constants.ListConstants.FOURTH_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by robert on 12/06/2014.
 */
public class RatingsFragment extends Fragment implements NetworkFeedback {

    private ArrayList<HashMap> list;
    private ImageView imageView;
    private DataStore dataStore;
    private ListView lview;
    private LayoutInflater inflater;
    private ListAdpter adapter;

    public void setUP(DataStore dataStore){
        this.dataStore = dataStore;
        getList();
    }

    public void getList(){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_type", "GETALLSCOREASC"));
        nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
        nameValuePairs.add(new BasicNameValuePair("usrToken", dataStore.getLogin().getToken()));
        nameValuePairs.add(new BasicNameValuePair("courseID", ""+dataStore.getCourse().getCourseID()));

        BackgroundTask bt = new BackgroundTask(this, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
        bt.execute("ddd");
    }


    public void draw(){

        if((imageView!=null)&&(dataStore.getDrawable()!=null)){imageView.setImageDrawable(dataStore.getDrawable());}
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_results, container, false);

        lview = (ListView) rootView.findViewById(R.id.listview);

        imageView = (ImageView) rootView.findViewById(R.id.imageView2);


        if(dataStore!=null){
            getList();
        }

        return rootView;
    }


    private void populateList() {

        if(dataStore!=null) {
            if(dataStore.getCourseRankings()!=null) {


                list = new ArrayList<HashMap>();

                for (int i = 0; i < dataStore.getCourseRankings().getRankings().size(); i++) {

                    HashMap temp = new HashMap();
                    Rankings r = dataStore.getCourseRankings().getRankings().get(i);

                    if(i==0){
                        String url = "http://gravatar.com/avatar/"+MD5(r.getUsername());
                        BackgroundImage bt = new BackgroundImage(this,new DataStore(),url);
                        bt.execute("ddd");

                    }

                    temp.put(FIRST_COLUMN, r.getPosition() + 1);
                    temp.put(SECOND_COLUMN, r.getName());
                    temp.put(THIRD_COLUMN, r.getScore());
                    temp.put(FOURTH_COLUMN, r.getWeather());
                    list.add(temp);
                }

                Log.d("Message","populate host");
                adapter = new ListAdpter(inflater, list);
                lview.setAdapter(adapter);
                lview.refreshDrawableState();

            }
        }

    }


    @Override
    public void onPostComplete(DataStore dataStore, ListConstants.TYPE type) {
        this.dataStore = dataStore;
        if(type == ListConstants.TYPE.JSON){this.dataStore.setCourseRankings(new CourseRankings(this.dataStore.getRawJson()));populateList();}
        else {
            draw();
        }

    }
}