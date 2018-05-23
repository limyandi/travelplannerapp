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
import android.widget.AdapterView;
import android.widget.Spinner;
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

public class MyTripFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private Spinner mSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_trip, container, false);

        mSpinner = rootView.findViewById(R.id.spinner_filter_itinerary);

        mSpinner.setOnItemSelectedListener(this);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.view_itinerary_history_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        //all acts as the default value.
        getItineraryPreviewData(rootView, "All");

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //TODO: Call the databse from here?
        Toast toast = Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT);
        toast.show();
        getItineraryPreviewData(view, parent.getItemAtPosition(pos).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getItineraryPreviewData(final View rootView, final String status) {
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

                    if(crawledView != null) {
                        if(status.equals("All")) {
                            Log.d(Constant.LOG_TAG, crawledView.toString());
                            mItineraryPreviewList.add(crawledView);
                        }
                        if(status.equals("Past")) {
                            if(endDate.before(todayDate)) {
                                mItineraryPreviewList.add(crawledView);
                            }
                        }
                        if(status.equals("Present")) {
                            if(endDate.after(todayDate)) {
                                mItineraryPreviewList.add(crawledView);
                            }
                        }

                    }
                }
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(rootView.getContext(), mItineraryPreviewList);
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
