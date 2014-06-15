package com.robrotheram.inBeta.DataStore;

import org.apache.http.cookie.Cookie;

import java.io.Serializable;

/**
 * Created by robert on 13/06/2014.
 */

public class Passable implements Serializable {
    public String cookieURL;
    public String cookieValue;
    public String token;
}
