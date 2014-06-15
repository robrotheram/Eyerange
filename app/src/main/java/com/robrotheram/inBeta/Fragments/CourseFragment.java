package com.robrotheram.inBeta.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.Course;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 12/06/2014.
 */
public class CourseFragment extends Fragment implements NetworkFeedback {

    private DataStore dataStore;
    private TextView weather;
    private ViewGroup rootView;
    private TextView titleView;
    private TextView windSpeedView;
    private TextView windDir;
    private TextView infoView;


    public void setUP(DataStore dataStore){
        this.dataStore = dataStore;
        Location l = dataStore.getLocation();
        String tmpLat = ""+l.getLatitude();
        String tempLNG = ""+l.getLongitude();

        String lat = tmpLat.substring(0, Math.min(tmpLat.length(), 5));
        String lng = tempLNG.substring(0, Math.min(tempLNG.length(), 5));

        android.util.Log.e("Message", "Location:" + lat + " long = " + lng);


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_type", "GETCOURSE"));
        nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
        nameValuePairs.add(new BasicNameValuePair("usrToken", this.dataStore.getLogin().getToken()));
        nameValuePairs.add(new BasicNameValuePair("lat", lat));
        nameValuePairs.add(new BasicNameValuePair("long", lng));

        BackgroundTask bt = new BackgroundTask(this, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
        bt.execute("ddd");

    }


    private void displaydata(){
        if(dataStore!=null) {

            if((weather!=null)&&(dataStore.getCourse()!=null)){weather.setText("Weather: "+dataStore.getCourse().getWeather());}
            if((titleView!=null)&&(dataStore.getCourse()!=null)){titleView.setText(dataStore.getCourse().getCourseName());}
            if((windSpeedView!=null)&&(dataStore.getCourse()!=null)){windSpeedView.setText("Wind Speed: " + dataStore.getCourse().getWindSpeed());}
            if((windDir!=null)&&(dataStore.getCourse()!=null)){windDir.setText("Wind Direction: " + dataStore.getCourse().getWindDirString());}
            if((infoView!=null)&&(dataStore.getCourse()!=null)){infoView.setText("info: \n" + dataStore.getCourse().getInfo());}
        }
    }


    


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_course, container, false);

        weather = (TextView) rootView.findViewById(R.id.weatherData);
        titleView = (TextView) rootView.findViewById(R.id.title);
        windSpeedView = (TextView) rootView.findViewById(R.id.textView2);
        windDir = (TextView) rootView.findViewById(R.id.textView3);
        infoView = (TextView) rootView.findViewById(R.id.textView4);

        if(dataStore==null){
            Log.d("Message", "Datatore is Null");

        } else{
            Log.d("Message", "ypypypypyyp");

            displaydata();
        }

        return rootView;
    }

    @Override
    public void onPostComplete(DataStore dataStore, ListConstants.TYPE type) {
        this.dataStore = dataStore;
        this.dataStore.setCourse(new Course(this.dataStore.getRawJson()));
        displaydata();
    }
}
