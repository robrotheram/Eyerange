package com.robrotheram.inBeta.DataStore;

import android.graphics.drawable.Drawable;
import android.location.Location;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by robert on 13/06/2014.
 */
public class DataStore implements Serializable{

   public DataStore(){
       setDefaultHttpClient(new DefaultHttpClient());
   }


    public DefaultHttpClient getDefaultHttpClient() {
        return defaultHttpClient;
    }

    public void setDefaultHttpClient(DefaultHttpClient defaultHttpClient) {
        this.defaultHttpClient = defaultHttpClient;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(JSONObject jsonObject) {
        this.login = new Login(jsonObject);
    }

    public JSONObject getRawJson() {
        return rawJson;
    }

    public void setRawJson(JSONObject rawJson) {
        this.rawJson = rawJson;
    }

    public Hole getHole() {
        return hole;
    }

    public void setHole(JSONObject rawJson) {
        this.hole = new Hole(rawJson);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public CourseRankings getCourseRankings() {
        return courseRankings;
    }

    public void setCourseRankings(CourseRankings courseRankings) {
        this.courseRankings = courseRankings;
    }

    public String getUsername() {
        return username;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) {
        this.beaconID = beaconID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private DefaultHttpClient defaultHttpClient;
    private Login login;
    private String username;
    private Hole hole;
    private Course course;
    private JSONObject rawJson;
    private Drawable drawable;
    private String beaconID;
    private CourseRankings courseRankings;

    private int totalScore;

    private Location location;


}
