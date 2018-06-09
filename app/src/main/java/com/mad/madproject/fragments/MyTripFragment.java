package com.mad.madproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.adapter.ItineraryPreviewAdapter;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by limyandivicotrico on 5/5/18.
 */

/**
 * My Trip Fragment handles displaying either all trip, past trip, or present trip, using spinner as the filter..
 */
public class MyTripFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_trip, container, false);
        Util.setFragmentToolbarTitle(this, "My Trips");

        //set the view pager.
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //set the tabs layout
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.result_tabs);
        //set up with the view pager.
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    /**
     * Set up the view pager.
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        FilterPagerAdapter adapter = new FilterPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
    }

    public class FilterPagerAdapter extends FragmentPagerAdapter {

        public FilterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FilterTripFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //always 3, all, past, present.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Present";
                case 2:
                    return "Past";
            }
            return null;
        }
    }


//    /**
//     * Call database to get the data for the itinerary preview.
//     * @param rootView refer to the view to send the database file to
//     * @param status the spinner status.
//     */
//    private void getItineraryPreviewData(final View rootView, final String status) {
//
//        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                mItineraryPreviewList.clear();
//                for(DataSnapshot itineraryView: dataSnapshot.getChildren()) {
//                    Log.d(Constant.LOG_TAG, itineraryView.toString());
//                    ItineraryPreview crawledView = itineraryView.getValue(ItineraryPreview.class);
//                    Date endDate = new Date();
//                    Date todayDate = new Date();
//
//                    try {
//                        Log.d("Try", crawledView.getEndDate());
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//                        endDate = formatter.parse(crawledView.getEndDate());
//                    } catch (ParseException e) {
//                        Log.d("Try", "Failed");
//                        e.printStackTrace();
//                    }
//
//                    if(status.equals("All")) {
//                        Log.d(Constant.LOG_TAG, crawledView.toString());
//                        mItineraryPreviewList.add(crawledView);
//                    }
//                    if(status.equals("Past")) {
//                        if(endDate.before(todayDate)) {
//                            mItineraryPreviewList.add(crawledView);
//                        }
//                    }
//                    if(status.equals("Present")) {
//                        if(endDate.after(todayDate)) {
//                            mItineraryPreviewList.add(crawledView);
//                        }
//                    }
//
//                }
//                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
//                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
//                if(mItineraryPreviewList.size() == 0) {
//                    mNoTripTextTv.setVisibility(View.VISIBLE);
//                }
//                else {
//                    mNoTripTextTv.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


}
