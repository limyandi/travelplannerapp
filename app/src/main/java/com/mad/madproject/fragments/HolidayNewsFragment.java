package com.mad.madproject.fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    private LinearLayout fullLayout;
    private GridLayout mMainGrid;
    private RelativeLayout mRelativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Util.setFragmentToolbarTitle(this, "News");
        final View rootView = inflater.inflate(R.layout.fragment_holiday_news, container, false);


        fullLayout = (LinearLayout) rootView .findViewById(R.id.parent_layout);
        mMainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.nextversion_layout);

        mMainGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularReveal();
            }
        });

        return rootView;

    }

    //TODO: Target API?
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularReveal() {
        int x = fullLayout.getRight();
        int y= fullLayout.getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(fullLayout.getWidth(), fullLayout.getHeight());

        mMainGrid.setVisibility(View.GONE);
        Animator anim = ViewAnimationUtils.createCircularReveal(mRelativeLayout, x, y, startRadius, endRadius);

        mRelativeLayout.setVisibility(View.VISIBLE);

        anim.start();
        textAnimation();
    }

    private void textAnimation() {
        TransitionManager.beginDelayedTransition(mRelativeLayout);
    }
}
