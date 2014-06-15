package com.robrotheram.inBeta.DataStore;

/**
 * Created by robert on 15/06/2014.
 */
public class Rankings {

    private String score;
    private String weather;
    private int position;
    private String Date;
    private String Name;
    private String Username;



    public Rankings(String username, String name, String score, String weather, int position, String date) {
        this.score = score;
        this.weather = weather;
        this.position = position;
        this.Name = name;
        this.Username = username;
        Date = date;


    }

    public String getUsername() {   return Username;    }

    public void setUsername(String username) { Username = username; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
