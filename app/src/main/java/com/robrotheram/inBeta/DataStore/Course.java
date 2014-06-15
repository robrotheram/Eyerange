package com.robrotheram.inBeta.DataStore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robert on 14/06/2014.
 */
public class Course {


    private String courseName;
    private int courseID;
    private String info;
    private String weather;
    private int windSpeed;
    private int windDir;
    private String windDirString;


    public Course(JSONObject jObj) {

        try {
            JSONObject details = jObj.getJSONArray("Data").getJSONObject(0);
            setCourseID(details.getInt("CourseID"));
            setCourseName(details.getString("CouseName"));
            setInfo(details.getString("info"));
            JSONObject weather = details.getJSONObject("Weather");
            setWeather(weather.getString("summary"));
            setWindSpeed(weather.getInt("windSpeed"));
            setWindDir(weather.getInt("windBearing"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWindDirString() {
        return windDirString;
    }

    public void setWindDirString(String windDirString) {
        this.windDirString = windDirString;
    }

    public void setWindDirStringViaInt(int deg) {
        if((deg>=0)&&(deg<45)){
            windDirString = "North";
        }else if((deg>=45)&&(deg<90)){
            windDirString = "North East";
        }else if((deg>=90)&&(deg<135)){
            windDirString = "East";

        }else if((deg>=135)&&(deg<180)){

            windDirString = "South East";
        }else if((deg>=180)&&(deg<225)){

            windDirString = "South";
        }else if((deg>=225)&&(deg<270)){
            windDirString = "South West";

        }else if((deg>=270)&&(deg<315)){
            windDirString = "West";

        }else if((deg>=315)&&(deg<360)){
            windDirString = "North West";
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDir() {
        return windDir;
    }

    public void setWindDir(int windDir) {
        this.windDir = windDir;
        setWindDirStringViaInt(windDir);
    }
}
