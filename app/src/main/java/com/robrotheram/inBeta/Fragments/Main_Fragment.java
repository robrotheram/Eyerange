package com.robrotheram.inBeta.Fragments;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.robrotheram.inBeta.BackgroundActivies.BackgroundTask;
        import com.robrotheram.inBeta.Constants.ListConstants;
        import com.robrotheram.inBeta.DataStore.DataStore;
        import com.robrotheram.inBeta.Interface.NetworkFeedback;
        import com.robrotheram.inBeta.R;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;

        import java.util.ArrayList;
        import java.util.List;

/**
 * This fragment hosts the viewpager that will use a FragmentPagerAdapter to display child fragments.
 */
public class Main_Fragment extends Fragment implements NetworkFeedback{

    public static final String TAG = Main_Fragment.class.getName();
    public DataStore dataStore;
    private MyAdapter mp;
    private ViewPager viewPager;
    private int pos = 0;


    public static Main_Fragment newInstance() {
        return new Main_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager = (ViewPager) root.findViewById(R.id.pager);
        /** Important: Must use the child FragmentManager or you will see side effects. */
        mp = new MyAdapter(getChildFragmentManager());
        mp.setDataStore(this.dataStore);
        viewPager.setAdapter(mp);
        viewPager.setCurrentItem(pos,true);

        if(dataStore==null){
            Log.d("Message", "Datatore is Null");

        } else {
            Log.d("Message", "ypypypypyyp");
            getHole();
        }
            return root;
    }

    public void setUP(DataStore dataStore){
        this.dataStore = dataStore;
        displaydata();
    }


    private void displaydata() {
        if (dataStore != null) {
        }

    }

    public void gotoScoreFragment(){
        if(viewPager!=null){viewPager.setCurrentItem(2,true);}
        pos = 2;

    }
    public void getHole(){

        if((dataStore.getLogin()!=null)&&(dataStore.getBeaconID()!=null)) {

            Log.d("Message","BeconID ="+dataStore.getBeaconID() );
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("request_type", "GETBEACONID"));
            nameValuePairs.add(new BasicNameValuePair("content_type", "JSON"));
            nameValuePairs.add(new BasicNameValuePair("usrToken", dataStore.getLogin().getToken()));
            nameValuePairs.add(new BasicNameValuePair("beaconID", dataStore.getBeaconID()));

            BackgroundTask bt = new BackgroundTask(this, dataStore, nameValuePairs,ListConstants.TYPE.JSON);
            bt.execute("ddd");
        }
    }

    @Override
    public void onPostComplete(DataStore ds, ListConstants.TYPE type) {
        this.dataStore = ds;
        this.dataStore.setHole(this.dataStore.getRawJson());
        mp.setDataStore(this.dataStore);
        viewPager.setAdapter(mp);
        mp.refresh();
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        private ImageFragment imageFragment;
        private HoleFragment holeFragment;
        private ScoreFragment scoreFragment;
        private DataStore dataStore;
        private int position;

        public void setDataStore(DataStore ds){

            dataStore = ds;
            getItem(position);
        }

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void refresh(){
            holeFragment.onRefresh();
            imageFragment.onRefresh();

        }
        @Override
        public Fragment getItem(int pos) {
            this.position = pos;
            switch (position){
                case 0: holeFragment = new HoleFragment();
                        if(dataStore != null){holeFragment.setDataStore(dataStore);}
                        return holeFragment;
                case 1: imageFragment = new ImageFragment();
                        if(dataStore != null){imageFragment.setDataStore(dataStore);}
                        return imageFragment;
                case 2: scoreFragment = new ScoreFragment();
                        scoreFragment.setDataStore(dataStore);
                        return scoreFragment;
                default: holeFragment = new HoleFragment();
                        return holeFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Child Fragment " + position;
        }

    }

}