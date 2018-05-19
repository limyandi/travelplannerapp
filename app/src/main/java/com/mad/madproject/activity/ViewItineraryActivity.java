package com.mad.madproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.adapter.ItineraryAdapter;
import com.mad.madproject.adapter.ItineraryPreviewAdapter;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

public class ViewItineraryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ViewItinerary";
    private int mDays;
    private String mCity;
    private String mStartDate;
    private String mEndDate;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itinerary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mCity = getIntent().getStringExtra("City");
        mStartDate = getIntent().getStringExtra("Start Date");
        mEndDate = getIntent().getStringExtra("End Date");
        TextView itineraryPlace = (TextView) findViewById(R.id.view_itinerary_activity_place);
        TextView itineraryDate = (TextView) findViewById(R.id.view_itinerary_activity_date);
        itineraryPlace.setText(mCity);
        itineraryDate.setText(mStartDate + " - " + mEndDate);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_itinerary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ItineraryAdapter mItineraryAdapter;
        private ArrayList<Itinerary> mItinerariesList = new ArrayList<>();
        private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        private DatabaseReference mUsersRef = mDatabase.getReference("Users");
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

            Log.d(Constant.LOG_TAG, "Trying to adding to the itinerary");
            mUsersRef.child(Util.getUserUid()).child("Day1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mItinerariesList.clear();
                    for(DataSnapshot itinerary: dataSnapshot.getChildren()) {
                        Itinerary crawledView = itinerary.getValue(Itinerary.class);
                        mItinerariesList.add(crawledView);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        //TODO: This might be wrong.
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_itinerary, container, false);
            TextView daylist = (TextView) rootView.findViewById(R.id.section_label);

            mItineraryAdapter = new ItineraryAdapter(mItinerariesList, getActivity());
            daylist.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            itineraryTitle.setText(getString(R.string.example_string), getArguments().getInt(ARG_SECTION_NUMBER));
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.itinerary_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setAdapter(mItineraryAdapter);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            mDays = getIntent().getIntExtra("Day", 1);
            return mDays;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
