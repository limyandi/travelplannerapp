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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.activity.MainActivity;
import com.mad.madproject.adapter.ItineraryPreviewAdapter;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/20/18.
 */

public class ItineraryPreviewFragment extends Fragment {
    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_itinerary_preview, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.view_itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        //TODO: Put async task here? So not too many task done in the front end?
        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mItineraryPreviewList.clear();
                for(DataSnapshot itineraryView: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryView.toString());
                    ItineraryPreview crawledView = itineraryView.getValue(ItineraryPreview.class);
                    Log.d(Constant.LOG_TAG, crawledView.toString());
                    mItineraryPreviewList.add(crawledView);
                }
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    private void getItineraryPreviews(final View rootView) {

    }
}
