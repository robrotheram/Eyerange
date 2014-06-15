package com.robrotheram.inBeta.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.Activies.Game;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 12/06/2014.
 */
public class LoginFragment extends Fragment implements NetworkFeedback {

    ViewPager mPager;
    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;
    private TextView link;
    private Button loginBTN;
    private ViewGroup rootView;
    private DataStore dataStore;
    private String username ="Robert";
    private String password ="test";

    private TextView usernameView;
    private TextView passwordView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        link = (TextView) rootView.findViewById(R.id.reg);
        usernameView = (TextView) rootView.findViewById(R.id.username);
        passwordView = (TextView) rootView.findViewById(R.id.password);
        loginBTN = (Button) rootView.findViewById(R.id.Loginbutton);
        Log.d("INFO", link.getText().toString());


        setUpListeners();
        return rootView;
    }

    public void setmPager(ViewPager viewPager) {
        this.mPager = viewPager;
    }


    public void setUpListeners(){


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1,true);
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });



    }

    public void sendMessage() {
        //Intent i = new Intent(rootView.getContext(), Game.class);
       // startActivity(i);

        dataStore = new DataStore();
        dataStore.setDefaultHttpClient(new DefaultHttpClient());



        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_type", "LOGIN"));
        nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
        nameValuePairs.add(new BasicNameValuePair("username", usernameView.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", passwordView.getText().toString()));

        username = usernameView.getText().toString();
        password = passwordView.getText().toString();
        BackgroundTask bt = new BackgroundTask(this, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
        bt.execute("ddd");
    }


    @Override
    public void onPostComplete(DataStore dataStore, ListConstants.TYPE type) {
        this.dataStore = dataStore;
        this.dataStore.setLogin(this.dataStore.getRawJson());
        if(this.dataStore.getLogin().getSuccess()) {
            Intent i = new Intent(rootView.getContext(), Game.class);
            i.putExtra("USER", username);
            i.putExtra("PASS", password);
            startActivity(i);
        }else{
            Toast.makeText(this.getActivity(),"Worng Username and/or passowrd tryagain",Toast.LENGTH_LONG).show();
        }

    }
}
