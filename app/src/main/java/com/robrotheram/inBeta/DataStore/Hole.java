package com.robrotheram.inBeta.DataStore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robert on 13/06/2014.
 */
public class Hole {
    private boolean response;
    private int holeNumber;
    private int distanceToPin;
    private int par;
    private String description;


    public Hole(JSONObject jobj) {

        try {
            JSONObject details = jobj.getJSONArray("Data").getJSONObject(0);
            setDescription(details.getString("Desc"));
            setHoleNumber(details.getInt("HoleID"));
            setDistanceToPin(details.getInt("DistanceToPin"));
            setPar(details.getInt("Par"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getDistanceToPin() {
        return distanceToPin;
    }

    public void setDistanceToPin(int distanceToPin) {
        this.distanceToPin = distanceToPin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
