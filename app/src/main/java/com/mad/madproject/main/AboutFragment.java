package com.mad.madproject.main;

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
 * This fragment inflates the layout for the 'about us' section in the navigation drawer menu. It does not have any logic.
 */
public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //set the title located in the toolbar.
        Util.setFragmentToolbarTitle(this, getString(R.string.about_us_title));
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }
}
