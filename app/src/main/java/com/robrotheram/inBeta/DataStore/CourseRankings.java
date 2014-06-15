package com.robrotheram.inBeta.DataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by robert on 15/06/2014.
 */
public class CourseRankings {
    private ArrayList<Rankings> rankings;

    public CourseRankings(JSONObject jObj) {
        rankings = new ArrayList<Rankings>();
        try {
            JSONArray details = jObj.getJSONArray("Data");

            for(int i=0; i<details.length();i++){
                JSONObject data = details.getJSONObject(i);
                rankings.add(new Rankings(
                        data.getString("Username"),
                        data.getString("Name"),
                        data.getString("Socre"),
                        data.getString("Weather"),
                        i,
                        data.getString("Date")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rankings> getRankings() {
        return rankings;
    }
    public Rankings getRankingsPos(int i){
        return rankings.get(i);
    }

    public void setRankings(ArrayList<Rankings> rankings) {
        rankings = rankings;
    }
}
