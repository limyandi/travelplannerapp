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
import android.widget.ProgressBar;
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

/**
 * Created by limyandivicotrico on 5/20/18.
 */

public class ItineraryPreviewFragment extends Fragment {
    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private ProgressBar mMainProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_itinerary_preview, container, false);

        mMainProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_itinerary_preview_whole_progress_bar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.view_itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        getItineraryPreviews(rootView);


        return rootView;
    }



    private void getItineraryPreviews(final View rootView) {
        //Progressbar before and after?
        mMainProgressBar.setVisibility(View.VISIBLE);
        //TODO: Put async task here? So not too many task done in the UI Thread.
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
                        Log.d("Try", crawledView.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(crawledView.getEndDate());
                    } catch (ParseException e) {
                        Log.d("Try", "Failed");
                        e.printStackTrace();
                    }

                    if(endDate.after(todayDate)) {
                        Log.d(Constant.LOG_TAG, crawledView.toString());
                        mItineraryPreviewList.add(crawledView);
                    }
                }
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
                mMainProgressBar.setVisibility(View.INVISIBLE);
                if(mItineraryPreviewList.size() == 0) {
                    TextView noItineraryTv = (TextView) rootView.findViewById(R.id.text_no_itinerary);
                    noItineraryTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
