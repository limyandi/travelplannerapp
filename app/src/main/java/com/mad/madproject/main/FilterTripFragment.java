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
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;

import java.util.ArrayList;

/**
 * The fragment for setting the different filter for the my trip page.
 */
public class FilterTripFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private TextView mNoTripTextView;
    MyTripFilterViewModel mTripFilterViewModel;

    private static final String POSITION = "POSITION";

    /**
     * Default constructor
     */
    public FilterTripFragment() {

    }

    /**
     * To create a new instance of the FilterTripFragment.
     * @param position the position of the fragment in.
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

        mTripFilterViewModel = ViewModelProviders.of(this).get(MyTripFilterViewModel.class);

        //setup the filter with int.
        int position = 0;
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
        }

        //change the database call when the position is changed.
        callOnChangedPosition(position);
        //observe the change in data.
        observeItineraryPreviewFiltered(rootView);

        return rootView;
    }

    //TODO: Can be better, we call this everytime we navigate to another page.
    /**
     * Call database to get the data for the itinerary preview that is already filtered.
     * @param position refer to the filter type and position
     */
    private void callOnChangedPosition(final int position) {
        if (position == 0) {
            mTripFilterViewModel.onMoveToAll();
        }
        if (position == 1) {
            mTripFilterViewModel.onMoveToPresent();
        }
        if (position == 2) {
            mTripFilterViewModel.onMoveToPast();
        }
    }

    /**
     * Observe the data change.
     * @param rootView the view to set the adapter to.
     */
    private void observeItineraryPreviewFiltered(final View rootView) {
        mTripFilterViewModel.getItineraryPreviews().observe(this, new Observer<ArrayList<ItineraryPreview>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ItineraryPreview> itineraryPreviews) {
                if(itineraryPreviews != null) {
                    mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), itineraryPreviews);
                    mRecyclerView.setAdapter(mItineraryPreviewAdapter);
                }
                else {
                    mNoTripTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
