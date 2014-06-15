package com.robrotheram.inBeta.Constants;

/**
 *
 * @author Paresh N. Mayani
 */
public class ListConstants {

    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";

    public enum TYPE {
       IMAGE,JSON,BEACON
    }

    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_TOKEN_ID = "tokenid";
    public static final String TAG_BEACON_ID = "beaconID";

    // URLs
    public static final String URL_API = "http://eyerange.co.uk/api/";
    public static final String URL_BEACON = "http://eyerange.co.uk/api/beacon.php";

    // API Tokens
    public static final String TAG_AUTH = "usrToken";

    // HTTP codes
    public static final String TAG_SUCCESS = "response";

    // HTTP methods
    public static final String HTTP_POST = "POST";
    public static final String HTTP_GET = "GET";

    // API Tags
    public static final String TYPE_JSON = "JSON";
    public static final String CONTENT_TYPE = "content_type";
    public static final String REQUEST_TYPE = "request_type";

    // API request types
    public static final String REQUEST_LOGIN = "LOGIN";
    public static final String REQUEST_REGISTER = "REGISTER";
    public static final String REQUEST_BEACON = "GETBEACONID";

    // iBeacon UUID
    public static final String BEACON_UUID = "e2c56db5-dffb-48d2-b060-d0f5a71096e0";
    public static final int FIRST_MAJOR = 1;
    public static final int FIRST_MINOR = 8;
    public static final int SECOND_MAJOR = 1;
    public static final int SECOND_MINOR = 9;

    // distance
    public static final double DISTANCE = 0.5;


    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}