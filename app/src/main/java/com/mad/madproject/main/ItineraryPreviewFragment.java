package com.mad.madproject.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/20/18.
 */

/**
 * This fragment lists all the current ongoing itinerary(ies) that the user has/have in a recycler view.
 */
public class ItineraryPreviewFragment extends Fragment {
    //holds the list of the itinerary preview.
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private ProgressBar mMainProgressBar;
    private ItineraryPreviewViewModel mItineraryPreviewViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_itinerary_preview, container, false);
        Util.setFragmentToolbarTitle(this, getString(R.string.my_trips_title));

        mItineraryPreviewViewModel = ViewModelProviders.of(this).get(ItineraryPreviewViewModel.class);

        mMainProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_itinerary_preview_whole_progress_bar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.view_itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        mMainProgressBar.setVisibility(View.VISIBLE);

        observeItineraryPreview(rootView);

        return rootView;
    }

    private void observeItineraryPreview(final View rootView) {
        mItineraryPreviewViewModel.getItineraryPreviews().observe(this, new Observer<ArrayList<ItineraryPreview>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ItineraryPreview> itineraryPreviews) {
                if(itineraryPreviews != null) {
                    mMainProgressBar.setVisibility(View.INVISIBLE);
                    mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), itineraryPreviews);
                    mRecyclerView.setAdapter(mItineraryPreviewAdapter);
                }
                else {
                    mMainProgressBar.setVisibility(View.INVISIBLE);
                    TextView noItineraryTv = (TextView) rootView.findViewById(R.id.text_no_itinerary);
                    noItineraryTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
