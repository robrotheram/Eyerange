package com.robrotheram.inBeta.DataStore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robert on 13/06/2014.
 */
public class Login{
    private String token;
    private Boolean success;


    public Login(JSONObject jObj){
        parseJSON(jObj);
    }

    private void parseJSON(JSONObject jObj){

        try {
            setSuccess(jObj.getBoolean("response"));
            setToken(jObj.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}