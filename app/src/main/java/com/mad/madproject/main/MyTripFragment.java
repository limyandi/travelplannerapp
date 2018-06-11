package com.mad.madproject.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.madproject.R;
import com.mad.madproject.main.FilterTripFragment;
import com.mad.madproject.utils.Util;

/**
 * Created by limyandivicotrico on 5/5/18.
 */

/**
 * My Trip Fragment handles displaying either all trip, past trip, or present trip, using spinner as the filter..
 */
public class MyTripFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_trip, container, false);
        Util.setFragmentToolbarTitle(this, getString(R.string.trip_history_text));

        //set the view pager.
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //set the tabs layout
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.result_tabs);
        //set up with the view pager.
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    /**
     * Set up the view pager.
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        FilterPagerAdapter adapter = new FilterPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
    }

    /**
     * The pager adapter to enable different fragment to be shown in each section.
     */
    public class FilterPagerAdapter extends FragmentPagerAdapter {

        public FilterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FilterTripFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //always 3, all, past, present.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.all_section);
                case 1:
                    return getString(R.string.present_section);
                case 2:
                    return getString(R.string.past_section);
            }
            return null;
        }
    }
}
