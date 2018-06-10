package com.mad.madproject.viewitinerary;

import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.model.Itineraries;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.Place;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * View Itinerary Activity shows the trips plan associated with one trip created by the system.
 * Does not use the view model because the implementation is not in align for the fragments. (The fragment does not wait for the live data to gather all the data before doing so).
 */
public class ViewItineraryActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ViewItinerary";
    private int mDays;

    private TextView mItineraryPlace;
    private TextView mItineraryDate;

    private String mPreviewId;
    private Itineraries mCrawledItinerary;

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

        mViewPager = (ViewPager) findViewById(R.id.container);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mPreviewId = getIntent().getStringExtra(Constant.ITINERARY_PREVIEW_PUSH_KEY_KEY);

        mItineraryPlace = (TextView) findViewById(R.id.view_itinerary_activity_place);
        mItineraryDate = (TextView) findViewById(R.id.view_itinerary_activity_date);
        getItinerariesDetails();

    }

    /**
     * Return to parent activity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //return to the parent activity.
        NavUtils.navigateUpFromSameTask(this);
    }

    private void getItinerariesDetails() {

        Util.getDatabaseReference("Itinerary").orderByChild("itineraryPreviewId").equalTo(mPreviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itinerary: dataSnapshot.getChildren()) {
                    mCrawledItinerary = itinerary.getValue(Itineraries.class);
                    Log.d(Constant.LOG_TAG, mCrawledItinerary.toString());
                }
                mItineraryPlace.setText(mCrawledItinerary.getTripName());
                mItineraryDate.setText(mCrawledItinerary.getStartDate() + " - " + mCrawledItinerary.getEndDate());
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                // Set up the ViewPager with the sections adapter.
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TripAdapter mTripAdapter;
        private ArrayList<Place> mTripsList = new ArrayList<>();
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        public static PlaceholderFragment newInstance(int sectionNumber, Itineraries itineraries) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            Log.d(LOG_TAG, "Number of itinerary days: " + String.valueOf(itineraries.getItineraryLists().size() + 1));

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            //Use the itineraries object to get the itinerary for each day and use it to set the arguments for each fragment view.

            args.putSerializable(Constant.ITINERARY_LISTS_KEY, itineraries.getItineraryLists().get(sectionNumber - 1));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_itinerary, container, false);

            Itinerary itinerary = (Itinerary) getArguments().getSerializable(Constant.ITINERARY_LISTS_KEY);

            mTripsList = itinerary != null ? itinerary.getPlaces() : null;

            mTripAdapter = new TripAdapter(mTripsList, getActivity());
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.itinerary_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            recyclerView.setAdapter(mTripAdapter);
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
            return PlaceholderFragment.newInstance(position + 1, mCrawledItinerary);
        }

        @Override
        public int getCount() {
            mDays = getIntent().getIntExtra(Constant.DAYS_KEY, 1);
            return mDays;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day 1";
                case 1:
                    return "Day 2";
                case 2:
                    return "Day 3";
                case 3:
                    return "Day 4";
                case 4:
                    return "Day 5";
            }
            return null;
        }
    }
}
