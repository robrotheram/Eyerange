package com.robrotheram.inBeta.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ScoreFragment extends Fragment implements NetworkFeedback {


    private TextView score;
    private Button submit;
    private NetworkFeedback networkFeedback;

    private DataStore dataStore;


    public void setDataStore(DataStore ds){
        this.dataStore = ds;


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_score, container, false);

        score = (TextView) rootView.findViewById(R.id.textView);
        submit = (Button) rootView.findViewById(R.id.button);
        networkFeedback = this;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(score.getText().length()>0){

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("request_type", "ADDHOLESCORE"));
                    nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
                    nameValuePairs.add(new BasicNameValuePair("usrToken", dataStore.getLogin().getToken()));
                    nameValuePairs.add(new BasicNameValuePair("holeID", ""+dataStore.getHole().getHoleNumber()));
                    nameValuePairs.add(new BasicNameValuePair("score", score.getText().toString()));
                    dataStore.setTotalScore(dataStore.getTotalScore()+Integer.parseInt(score.getText().toString()));


                    BackgroundTask bt = new BackgroundTask(networkFeedback, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
                    bt.execute("ddd");
                }

            }
        });

        return rootView;
    }


    @Override
    public void onPostComplete(DataStore dataStore, ListConstants.TYPE type) {

        JSONObject jb= dataStore.getRawJson();
        if (dataStore.getHole().getHoleNumber()==18){

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("request_type", "ADDFINALHOLE"));
            nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
            nameValuePairs.add(new BasicNameValuePair("usrToken", dataStore.getLogin().getToken()));
            nameValuePairs.add(new BasicNameValuePair("courseID", ""+dataStore.getCourse().getCourseID()+""));
            nameValuePairs.add(new BasicNameValuePair("score", dataStore.getTotalScore()+""));
            nameValuePairs.add(new BasicNameValuePair("weather", dataStore.getCourse().getWeather()));
            dataStore.setTotalScore(dataStore.getTotalScore()+Integer.parseInt(score.getText().toString()));
            BackgroundTask bt = new BackgroundTask(networkFeedback, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
            bt.execute("ddd");


        }

        try {
            if (jb.getBoolean("response")) {
                Toast.makeText(getView().getContext(), "Score Submitted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getView().getContext(), "Submission Failed", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getView().getContext(), "Submission Failed", Toast.LENGTH_LONG).show();
        }

    }
}
