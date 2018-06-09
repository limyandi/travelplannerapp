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

/**
 * This fragment lists all the current ongoing itinerary(ies) that the user has/have in a recycler view.
 */
public class ItineraryPreviewFragment extends Fragment {
    //holds the list of the itinerary preview.
    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private ProgressBar mMainProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_itinerary_preview, container, false);
        Util.setFragmentToolbarTitle(this, "My Trips");

        mMainProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_itinerary_preview_whole_progress_bar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.view_itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        setItineraryPreviews(rootView);

        return rootView;
    }

    /**
     * Set all the current ongoing itinerary adapter to the recycler view.
     * @param rootView the view that the function is attached to.
     */
    private void setItineraryPreviews(final View rootView) {
        mMainProgressBar.setVisibility(View.VISIBLE);
        //Call to ItineraryPreview Database.
        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clear the database first.
                mItineraryPreviewList.clear();

                //loops through all the children.
                for(DataSnapshot itineraryView: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryView.toString());
                    //store each children.
                    ItineraryPreview crawledView = itineraryView.getValue(ItineraryPreview.class);

                    Date endDate = new Date();
                    //get today's date
                    Date todayDate = new Date();

                    //if no error, overwrite end date with the date from database.
                    try {
                        Log.d(Constant.LOG_TAG, crawledView.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(crawledView.getEndDate());
                    } catch (ParseException e) {
                        Log.d(Constant.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    //if the end date is after today's date, don't add the itinerary to the list.
                    if(endDate.after(todayDate)) {
                        Log.d(Constant.LOG_TAG, crawledView.toString());
                        mItineraryPreviewList.add(crawledView);
                    }
                }
                //initialise the adapter
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
                //set the adapter to the recycler view.
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
                //done loading, make the progress bar invisible again.
                mMainProgressBar.setVisibility(View.INVISIBLE);
                //if the itinerary preview list is empty, then set a text telling user that they have no current itinerary.
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
