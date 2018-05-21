package com.mad.madproject.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.R;
import com.mad.madproject.model.Itineraries;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.Trip;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTripActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.start_date_tv)
    TextView mStartDateTv;
    @BindView(R.id.end_date_tv)
    TextView mEndDateTv;
    @BindView(R.id.addtrip_activity_trip_name)
    TextView mTripNameTv;
    @BindView(R.id.addtrip_activity_accommodation)
    TextView mAccommodationTv;
    @BindView(R.id.addtrip_activity_accommodation_details)
    TextView mAccommodationDetailsTv;

    //for handling if user google play services version is not valid / error
    private static final int ERROR_DIALOG_REQUEST = 9001;
    //Request to choose accommodation.
    private static final int CHOOSE_ACCOMMODATION_REQUEST = 1;

    private String mLatitude;
    private String mLongitude;

    private Itineraries fullItinerary;

    private int intervalDay;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    String itineraryPreviewKey = databaseReference.child("ItineraryPreview").push().getKey();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        ButterKnife.bind(this);

        LinearLayout startDateLayout = (LinearLayout) findViewById(R.id.start_date_layout);
        LinearLayout endDateLayout = (LinearLayout) findViewById(R.id.end_date_layout);
        Button confirmBtn = (Button) findViewById(R.id.addtrip_activity_confirm_btn);

        String textTrip = "Trip to " + getIntent().getStringExtra("City");
        mTripNameTv.setText(textTrip);

        startDateLayout.setOnClickListener(this);
        endDateLayout.setOnClickListener(this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewItineraryActivity.class);

                intervalDay = Util.convertDateToDayInterval(mEndDateTv.getText().toString(), mStartDateTv.getText().toString());
                intent.putExtra("Day", intervalDay);
                //TODO: Chaining intent put? is this bad practice?
                intent.putExtra("Latitude", mLatitude);
                intent.putExtra("Longitude", mLongitude);

                intent.putExtra("PreviewKey", itineraryPreviewKey);

                ItineraryPreview itineraryPreview = new ItineraryPreview(mTripNameTv.getText().toString(), "A", getIntent().getStringExtra("City"),  Util.getUserUid(), itineraryPreviewKey, mStartDateTv.getText().toString(), mEndDateTv.getText().toString());
                databaseReference.child("ItineraryPreview").child(itineraryPreviewKey).setValue(itineraryPreview);

                mockUpDataForItinerariesCreation();
                databaseReference.child("Itinerary").push().setValue(fullItinerary);

                startActivity(intent);
            }
        });

        if (isServicesOK()) initAddTripActivity();

        //cannot use this because api level is too low
        //mStartDateDp.setOnDateChangedListener();
    }

    private void mockUpDataForItinerariesCreation() {

        String tripName = mTripNameTv.getText().toString();
        String startDate = mStartDateTv.getText().toString();
        String endDate = mEndDateTv.getText().toString();
        String itineraryPreviewId = itineraryPreviewKey;

        ArrayList<Trip> trips = new ArrayList<>(6);

        ArrayList<Itinerary> itinerariesLists = new ArrayList<>(intervalDay);

        for(int i = 0; i < intervalDay; i++) {
            trips.clear();
            for(int j = 0; j < 6; j++) {
                //get trip data from web service.
                trips.add(new Trip("Yoyogi Park", "A", "10:00 A.M"));
            }
            Itinerary itinerary = new Itinerary(trips);
            itinerariesLists.add(itinerary);
        }

        fullItinerary = new Itineraries(itinerariesLists, tripName, startDate, endDate, itineraryPreviewId);

    }

    private void initAddTripActivity() {
        mAccommodationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                //TODO: Chain passing of intent value?
                LatLng lat = i.getParcelableExtra("LatLng");
                Intent chooseAccommodationIntent = new Intent(AddTripActivity.this, ChooseAccommodationActivity.class);
                chooseAccommodationIntent.putExtra("Latitudelongitude", lat);
                startActivityForResult(chooseAccommodationIntent, CHOOSE_ACCOMMODATION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Constant.LOG_TAG, "onActivityResult: Get Result from ChooseAccommodationActivity");
        switch (requestCode) {
            case CHOOSE_ACCOMMODATION_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    mAccommodationDetailsTv.setText(data.getStringExtra("Accommodation Address"));
                    mLatitude = data.getStringExtra("Accommodation Latitude");
                    mLongitude = data.getStringExtra("Accommodation Longitude");
                }
        }
    }

    @Override
    public void onClick(View v) {
        Calendar cal = Calendar.getInstance();
        //initialise the current year, month and day.
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener;

        switch (v.getId()) {
            case R.id.start_date_layout:
                dateSetListener = startDateSetListener;
                break;
            case R.id.end_date_layout:
                dateSetListener = endDateSetListener;
                break;
            default:
                dateSetListener = null;
        }

        DatePickerDialog dateDialog = new DatePickerDialog(AddTripActivity.this,
                android.R.style.Theme_Holo_Dialog,
                dateSetListener,
                year, month, day);
        DatePicker datePicker = dateDialog.getDatePicker();
        datePicker.setMinDate(cal.getTimeInMillis());
        //set the min date and max date for the end date text view, for min date, should be at least for a day,
        // for max date, 5 day after the start date. (at least for current version).
        if (dateSetListener == endDateSetListener) {
            Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                date = sdf.parse(mStartDateTv.getText().toString());
            } catch (ParseException e) {
                //TODO: Handle catch.
                e.printStackTrace();
            }
            Log.d(Constant.LOG_TAG, date.toString());
            long minDate = date.getTime();
            long maxDate = date.getTime() + Constant.DAY_LIMIT;
            Log.d(Constant.LOG_TAG, String.valueOf(maxDate));
            datePicker.setMinDate(minDate);
            datePicker.setMaxDate(maxDate);
        }
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDialog.show();
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            DecimalFormat df = new DecimalFormat("00");
            //add leading zeros to day less than 10.
            String leadingDay = df.format(day);
            String date = leadingDay + "-" + month + "-" + year;
            updateDisplay(mStartDateTv, date);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            //add leading zeros to day less than 10.
            DecimalFormat df = new DecimalFormat("00");
            String leadingDay = df.format(day);
            String date = leadingDay + "-" + month + "-" + year;
            updateDisplay(mEndDateTv, date);
        }
    };

    private void updateDisplay(TextView dateDisplay, String date) {
        dateDisplay.setText(date);
    }

    public boolean isServicesOK() {
        Log.d(Constant.LOG_TAG, "isServicesOK: checking google play services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddTripActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //success means user can make map requests
            Log.d(Constant.LOG_TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //handle issue with google play services version not compatible.
            //use Dialog (Google Dialog to handle the issue, google handle it for us and will display a dialog to help user to resolve it.)
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddTripActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            //cannot resolve at all
            //TODO: Clean this.
            Toast.makeText(AddTripActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


}
