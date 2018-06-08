package com.mad.madproject.addtripdetails;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mad.madproject.databinding.ActivityAddTripBinding;
import com.mad.madproject.R;
import com.mad.madproject.activity.ChooseAccommodationActivity;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTripDetailsActivity extends AppCompatActivity {

    @BindView(R.id.start_date_tv)
    TextView mStartDateTv;
    @BindView(R.id.end_date_tv)
    TextView mEndDateTv;
    @BindView(R.id.addtrip_activity_trip_name)
    TextView mTripNameTv;

    //for handling if user google play services version is not valid / error
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //setting initial interval day to prevent error.
    private int intervalDay = 1;

    private Date mStartDate;
    private Date mEndDate;

    private AddTripDetailsViewModel mTripDetailsViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddTripBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_trip);
        ButterKnife.bind(this);

        mTripDetailsViewModel = ViewModelProviders.of(this).get(AddTripDetailsViewModel.class);
        binding.setAddTripDetailsViewModel(mTripDetailsViewModel);

        //Set up data using the trip details.
        mTripDetailsViewModel.setInitialTripName(getIntent().getStringExtra("City"));

        onStartDateLayoutClicked();
        onEndDateLayoutClicked();
        onConfirmClicked();
    }

    private void onConfirmClicked() {
        ((Button) findViewById(R.id.addtrip_activity_confirm_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mStartDate = Util.parseDate(mStartDateTv.getText().toString());
                mEndDate = Util.parseDate(mEndDateTv.getText().toString());
                intervalDay = Util.convertDateToDayInterval(mEndDate, mStartDate);

                Intent intent = new Intent(getBaseContext(), ChooseAccommodationActivity.class);
                Log.d("Time", String.valueOf(intervalDay));
                intent.putExtra("Day", intervalDay);
                //TODO: Chaining intent put? is this bad practice?
                intent.putExtra("Latitude", getIntent().getDoubleExtra("Latitude", 0));
                intent.putExtra("Longitude", getIntent().getDoubleExtra("Longitude", 0));
                //Dont set the itinerary preview id for now.
                ItineraryPreview itineraryPreview = mTripDetailsViewModel.onNextClick(getIntent().getStringExtra("City"));
                //Itinerary Preview Data.
                intent.putExtra("ItineraryPreview", itineraryPreview);
                startActivity(intent);
            }
        });
    }

    private void onStartDateLayoutClicked() {
        ((LinearLayout) findViewById(R.id.start_date_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerSetup(startDateSetListener);
            }
        });
    }

    private void onEndDateLayoutClicked() {
        ((LinearLayout) findViewById(R.id.end_date_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerSetup(endDateSetListener);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dateString = initialSetUpListener(year, month, day);
            updateDisplay(mStartDateTv, dateString);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = Util.parseDate(dateString);
            //if start date is updated, update the end date to be only 1 day after the start date.
            updateDisplay(mEndDateTv, String.valueOf(dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)))));
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String dateString = initialSetUpListener(year, month, day);
            updateDisplay(mEndDateTv, dateString);
        }
    };

    private void datePickerSetup(DatePickerDialog.OnDateSetListener listener) {
        Calendar cal = Calendar.getInstance();
        //initialise the current year, month and day.
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dateDialog = new DatePickerDialog(AddTripDetailsActivity.this,
                android.R.style.Theme_Holo_Dialog,
                listener,
                year, month, day);

        if(listener == endDateSetListener) {
            DatePicker datePicker = dateDialog.getDatePicker();
            Date date = Util.parseDate(mStartDateTv.getText().toString());
            Log.d(Constant.LOG_TAG, date.toString());
            //set up min date and max date.
            long minDate = date.getTime();
            long maxDate = date.getTime() + Constant.DAY_LIMIT;
            Log.d(Constant.LOG_TAG, String.valueOf(maxDate));
            datePicker.setMinDate(minDate);
            datePicker.setMaxDate(maxDate);
        }

        DatePicker datePicker = dateDialog.getDatePicker();
        datePicker.setMinDate(cal.getTimeInMillis());
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDialog.show();
    }

    private String initialSetUpListener(int year, int month, int day) {
        month = month + 1;
        DecimalFormat df = new DecimalFormat("00");
        //add leading zeros to day and month less than 10.
        String leadingDay = df.format(day);
        String leadingMonth = df.format(month);
        String dateString = leadingDay + "-" + leadingMonth + "-" + year;
        return dateString;
    }


    private void updateDisplay(TextView dateDisplay, String date) {
        dateDisplay.setText(date);
    }

    /**
     * Function so we can check if the google play services is working properly. This
     * function can be used by other class so only if the services is okay we can start doing something with the map.
     * In this version, we dont need to use this, but lets use this in the next version.
     *
     * @return value whether the google play services working properly
     */
    public boolean isServicesOK() {
        Log.d(Constant.LOG_TAG, "isServicesOK: checking google play services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddTripDetailsActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //success means user can make map requests
            Log.d(Constant.LOG_TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //handle issue with google play services version not compatible.
            //use Dialog (Google Dialog to handle the issue, google handle it for us and will display a dialog to help user to resolve it.)
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddTripDetailsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            //cannot resolve at all
            //TODO: Clean this.
            Toast.makeText(AddTripDetailsActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}