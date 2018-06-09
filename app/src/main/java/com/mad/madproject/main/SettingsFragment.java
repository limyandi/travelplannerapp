package com.mad.madproject.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by limyandivicotrico on 5/5/18.
 */

/**
 * Fragment that displays settings for user to change their username, delete the past trip history and receiving notifications.
 */
public class SettingsFragment extends Fragment {

    RelativeLayout mDeleteTripHistoryLayout;
    CheckBox mReceiveNotifCheckbox;

    SettingsViewModel mSettingsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Util.setFragmentToolbarTitle(this, "Settings");

        mSettingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        mDeleteTripHistoryLayout = (RelativeLayout) rootView.findViewById(R.id.delete_trip_history);
        mReceiveNotifCheckbox = (CheckBox) rootView.findViewById(R.id.settings_checkbox);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deleteTripOnClick();
    }

    /**
     * create a material dialog to delete all past trips.
     */
    private void deleteTripOnClick() {
        mDeleteTripHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext()).title("Are you sure you want to delete your trip history?")
                        .positiveText(R.string.confirm).negativeText(R.string.cancel).positiveColor(getResources().getColor(R.color.green))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mSettingsViewModel.onConfirmClicked();
                            }
                        }).show();
            }
        });
    }



}
