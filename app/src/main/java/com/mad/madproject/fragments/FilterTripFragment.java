package com.mad.madproject.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class FilterTripFragment extends Fragment {

    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private TextView mNoTripTextView;

    private static final String POSITION = "POSITION";

    /**
     * Default constructor
     */
    public FilterTripFragment() {

    }

    /**
     * To create a new instance of the FilterTripFragment.
     * @param position
     * @return
     */
    public static FilterTripFragment newInstance(int position) {
        FilterTripFragment fragment = new FilterTripFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter_itinerary, container, false);

        mNoTripTextView = (TextView) rootView.findViewById(R.id.text_no_itinerary);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.filter_itinerary_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));


        //setup the filter with int.
        int position = 0;
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }

        //setup the  data.
        getItineraryPreviewDataFiltered(rootView, position);

        return rootView;
    }


    //TODO: Can be better, we call this everytime we navigate to another page.
    /**
     * Call database to get the data for the itinerary preview that is already filtered.
     * @param rootView refer to the view to send the database file to.
     * @param position refer to the filter type and position
     */
    private void getItineraryPreviewDataFiltered(final View rootView, final int position) {

        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mItineraryPreviewList.clear();
                for(DataSnapshot itineraryView: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryView.toString());
                    ItineraryPreview crawledView = itineraryView.getValue(ItineraryPreview.class);
                    Date endDate = new Date();
                    Date todayDate = new Date();

                    try {
                        Log.d(Constant.LOG_TAG, crawledView.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(crawledView.getEndDate());
                    } catch (ParseException e) {
                        Log.d(Constant.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    //if position == 0 ("All")
                    if(position == 0) {
                        Log.d(Constant.LOG_TAG, crawledView.toString());
                        mItineraryPreviewList.add(crawledView);
                    }
                    //if position == 1 ("Present")
                    if(position == 1) {
                        if(endDate.after(todayDate)) {
                            mItineraryPreviewList.add(crawledView);
                        }
                    }
                    //if position == 2("Past")
                    if(position == 2) {
                        if(endDate.before(todayDate)) {
                            mItineraryPreviewList.add(crawledView);
                        }
                    }

                }
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
                if(mItineraryPreviewList.size() == 0) {
                    mNoTripTextView.setVisibility(View.VISIBLE);
                }
                else {
                    mNoTripTextView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
