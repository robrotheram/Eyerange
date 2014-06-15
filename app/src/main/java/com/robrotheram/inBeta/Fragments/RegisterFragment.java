package com.robrotheram.inBeta.Fragments;

import android.content.Intent;
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
import com.robrotheram.inBeta.Activies.Game;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by robert on 12/06/2014.
 */
public class RegisterFragment extends Fragment implements NetworkFeedback {



    private TextView username;
    private TextView name;
    private TextView password;
    private TextView password2;
    private Pattern pattern;
    private Matcher matcher;
    private ViewGroup rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_regiter, container, false);

        username = (TextView) rootView.findViewById(R.id.username);
        name = (TextView) rootView.findViewById(R.id.displayName);
        password = (TextView) rootView.findViewById(R.id.password);
        password2 = (TextView) rootView.findViewById(R.id.password2);

        pattern = Pattern.compile(ListConstants.EMAIL_PATTERN);
        Button submit = (Button) rootView.findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return rootView;
    }

    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    public void register(){
        if(validate(username.getText().toString())){
            if(password.getText().toString().equals(password2.getText().toString())){

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("request_type", "REGISTER"));
                nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
                nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));

                BackgroundTask bt = new BackgroundTask(this, new DataStore(),nameValuePairs,ListConstants.TYPE.JSON);
                bt.execute("ddd");

            }else{
                Toast.makeText(this.getActivity(), "Passwords do not Match", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this.getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();
        }

        

    }

    @Override
    public void onPostComplete(DataStore dataStore, ListConstants.TYPE type) {

        JSONObject jb= dataStore.getRawJson();
        try {
            if(jb.getBoolean("response")) {
                Toast.makeText(this.getActivity(), "Registration Successful", Toast.LENGTH_LONG).show();

                Intent i = new Intent(rootView.getContext(), Game.class);
                i.putExtra("USER", username.getText().toString());
                i.putExtra("PASS", password.getText().toString());
                startActivity(i);
            }else{
                Toast.makeText(this.getActivity(), "Registration Failed", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Toast.makeText(this.getActivity(), "Registration Failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
