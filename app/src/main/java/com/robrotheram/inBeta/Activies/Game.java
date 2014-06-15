package com.robrotheram.inBeta.Activies;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;


import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;

import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;
import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
import com.robrotheram.inBeta.Constants.ListConstants;
import com.robrotheram.inBeta.DataStore.DataStore;
import com.robrotheram.inBeta.Fragments.CourseFragment;
import com.robrotheram.inBeta.Fragments.Main_Fragment;
import com.robrotheram.inBeta.Fragments.NavigationDrawerFragment;
import com.robrotheram.inBeta.Fragments.RatingsFragment;
import com.robrotheram.inBeta.Interface.NetworkFeedback;
import com.robrotheram.inBeta.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;





public class Game extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, NetworkFeedback, IBeaconConsumer {

    private DataStore dataStore;
    protected static final String TAG = "RangingActivity";
    private IBeaconManager iBeaconManager = IBeaconManager.getInstanceForApplication(this);



    /*   Fragment  */

    private Main_Fragment main_fragment = new Main_Fragment();
    private CourseFragment courseFragment = new CourseFragment();
    private RatingsFragment ratingsFragment = new RatingsFragment();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Location location;
    private ArrayList<IBeacon> devices;

    private ArrayList<String> deviceNames;
    private FragmentManager fragmentManager;
    private String beconID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        location = getLastBestLocation();
        android.util.Log.e("Message", "Location:" + location.getLatitude() + " long = " + location.getLongitude());
        setUpDatastore(i.getStringExtra("USER"), i.getStringExtra("PASS"));
        iBeaconManager.bind(this);
        devices = new ArrayList<IBeacon>();
        deviceNames = new ArrayList<String>();
        this.dataStore.setBeaconID(ListConstants.BEACON_UUID+"-1-8");





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iBeaconManager.unBind(this);
    }


    private Location getLastBestLocation() {
        LocationManager mLocationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }

    private void setUpDatastore(String username, String password){
        dataStore = new DataStore();


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_type", "LOGIN"));
        nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        BackgroundTask bt = new BackgroundTask(this, dataStore,nameValuePairs,ListConstants.TYPE.JSON);
        bt.execute("ddd");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {

            case 0:

                courseFragment = new CourseFragment();
                if(dataStore!=null){courseFragment.setUP(dataStore);}
                fragmentManager.beginTransaction()
                        .replace(R.id.container, courseFragment)
                        .commit();
                break;
            case 1:
                main_fragment = new Main_Fragment();
                main_fragment.setUP(dataStore);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, main_fragment)
                        .commit();
                break;
            case 2:
                ratingsFragment = new RatingsFragment();
                ratingsFragment.setUP(dataStore);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ratingsFragment)
                        .commit();
                break;

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mNavigationDrawerFragment !=null) {
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.game, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostComplete(DataStore datas , ListConstants.TYPE type) {
        if(type == ListConstants.TYPE.BEACON){

            try {
                JSONObject jb = datas.getRawJson();
                JSONObject details = jb.getJSONArray("Data").getJSONObject(0);
                String beconType = details.getString("Type");
                if(beconType.equals("TEE")){
                    gotoScoreFragment();
                }else if (beconType.equals("HOLE")){
                    this.dataStore.setBeaconID(this.beconID);
                    gotoHoleFragment();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else {

            this.dataStore = datas;
            this.dataStore.setLogin(this.dataStore.getRawJson());

            this.dataStore.setLocation(location);
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getFragmentManager().findFragmentById(R.id.navigation_drawer);
            mTitle = getTitle();

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
            courseFragment.setUP(dataStore);
        }


    }

    public void gotoHoleFragment(){
        main_fragment = new Main_Fragment();
        main_fragment.setUP(dataStore);
        fragmentManager.beginTransaction()
                .replace(R.id.container, main_fragment)
                .commit();
    }

    public void gotoScoreFragment(){
        main_fragment = new Main_Fragment();
        main_fragment.setUP(dataStore);
        main_fragment.gotoScoreFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, main_fragment)
                .commit();
    }


    @Override
    public void onIBeaconServiceConnect() {
        iBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<IBeacon> arg0,
                                                Region arg1) {

                devices.clear();
                deviceNames.clear();

                if (arg0.size() > 0) {
                    Iterator<IBeacon> itt = arg0.iterator();
                    while (itt.hasNext()) {
                        IBeacon device = itt.next();
                        if (!devices.contains(device)) {
                            devices.add(device);
                        }
                    }
                    checkDevices(devices);
                }
            }
        });

        try {
            iBeaconManager.startRangingBeaconsInRegion(new Region(
                    "myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            Log.e("iBeacon Service", "Unable to start ranging service.");
        }
    }



    protected void checkDevices(ArrayList<IBeacon> devices) {
        for (IBeacon dev : devices) {
            if ((dev.getMajor() == ListConstants.FIRST_MAJOR)
                    && ((dev.getMinor() == ListConstants.FIRST_MINOR) && (dev
                    .getAccuracy() < ListConstants.DISTANCE))) {
                // switch to holes tab when detected our first beacon and pass
                // token.
                    dataStore.setBeaconID(ListConstants.TAG_BEACON_ID+"-"+dev.getMajor()+"-"+dev.getMinor());
                    searchbeacon(ListConstants.TAG_BEACON_ID+"-"+dev.getMajor()+"-"+dev.getMinor());

                finish();
            }
        }
    }

    private void searchbeacon(String beconID){


        this.beconID = beconID;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_type", "GETBEACONID"));
        nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
        nameValuePairs.add(new BasicNameValuePair("usrToken", dataStore.getLogin().getToken()));
        nameValuePairs.add(new BasicNameValuePair("beaconID", beconID));

        BackgroundTask bt = new BackgroundTask(this, dataStore, nameValuePairs,ListConstants.TYPE.BEACON);
        bt.execute("ddd");

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_game, container, false);


            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Game) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
