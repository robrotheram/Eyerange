package com.robrotheram.inBeta.BackgroundActivies;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.Interface.NetworkFeedback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Created by robert on 15/06/2014.
 */
public class BackgroundImage extends AsyncTask<String, Integer, Double> {


        private HttpResponse response;
        private InputStream is = null;
        private String json;
        private String URL;
        private JSONObject jObj =null;
        private NetworkFeedback networkFeedbackresponse;
        private DataStore dataStore;
        private List<NameValuePair> nameValuePairs;
        private Drawable image;

    public BackgroundImage(NetworkFeedback networkFeedbackresponse, DataStore dataStore,String URL){
            this.URL = URL;
            this.networkFeedbackresponse = networkFeedbackresponse;
            this.dataStore = dataStore;
        }


        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }

        protected void onPostExecute(Double result){
            dataStore.setDrawable(image);
            networkFeedbackresponse.onPostComplete(dataStore, ListConstants.TYPE.IMAGE);

        }
        protected void onProgressUpdate(Integer... progress){

        }

        public void postData() {
            InputStream URLcontent = null;
            try {
                URLcontent = (InputStream) new java.net.URL(URL).getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
          image = Drawable.createFromStream(URLcontent, "your source link");

        }

    }

