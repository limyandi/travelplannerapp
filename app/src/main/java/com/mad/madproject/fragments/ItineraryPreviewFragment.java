package com.mad.madproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;

import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/20/18.
 */

public class ItineraryPreviewFragment extends Fragment {
    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_trip, container, false);
    }
}
