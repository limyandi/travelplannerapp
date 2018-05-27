package com.mad.madproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.madproject.R;
import com.mad.madproject.utils.Util;

/**
 * Created by limyandivicotrico on 5/5/18.
 */

/**
 * TODO: HolidayNewsFragment For next build version.
 * This fragment now only handles showing some fancy messages telling that this feature will be available in the next version of the application.
 */
public class HolidayNewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Util.setFragmentToolbarTitle(this, "News");
        return inflater.inflate(R.layout.fragment_holiday_news, container, false);
    }
}
