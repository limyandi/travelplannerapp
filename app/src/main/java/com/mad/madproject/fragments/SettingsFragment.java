package com.mad.madproject.fragments;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by limyandivicotrico on 5/5/18.
 */

public class SettingsFragment extends Fragment {

    RelativeLayout usernameLayout;
    RelativeLayout deleteTripHistoryLayout;
    CheckBox receiveNotifCheckbox;
    TextView usernameText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        usernameLayout = (RelativeLayout) rootView.findViewById(R.id.username_setting);
        deleteTripHistoryLayout = (RelativeLayout) rootView.findViewById(R.id.delete_trip_history);
        receiveNotifCheckbox = (CheckBox) rootView.findViewById(R.id.settings_checkbox);
        usernameText = (TextView) rootView.findViewById(R.id.username_change_setting);

        Util.getDatabaseReference("Users").child(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                usernameText.setText(user.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameLayoutOnClick();
        deleteTripOnClick();
    }

    private void usernameLayoutOnClick() {
        usernameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title("Change username")
                        .positiveText("Confirm")
                        .negativeText("Cancel")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Username", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                usernameText.setText(input);
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d("Fragment2", usernameText.getText().toString());
                        Util.getDatabaseReference("Users").child(Util.getUserUid()).child("username").setValue(usernameText.getText().toString());
                    }
                }).show();
            }
        });
    }


    private void deleteTripOnClick() {
        deleteTripHistoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext()).title("Are you sure you want to delete your trip history?")
                        .positiveText(R.string.confirm).negativeText(R.string.cancel).positiveColor(getResources().getColor(R.color.green))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(Util.getUserUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot itineraryView : dataSnapshot.getChildren()) {
                                            ItineraryPreview itineraryPreview = itineraryView.getValue(ItineraryPreview.class);

                                            Date endDate = new Date();
                                            try {
                                                Log.d("Try", itineraryPreview.getEndDate());
                                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                                endDate = formatter.parse(itineraryPreview.getEndDate());
                                            } catch (ParseException e) {
                                                Log.d("Try", "Failed");
                                                e.printStackTrace();
                                            }

                                            if (Util.isExpired(endDate, new Date())) {
                                                itineraryView.getRef().removeValue();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }).show();
            }
        });
    }

    //TODO: Firebase push notification? Change database to allow user to get firebase push notif.


}
