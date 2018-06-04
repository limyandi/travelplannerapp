package com.mad.madproject.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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

    //for handling if user google play services version is not valid / error
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //setting initial interval day to prevent error.
    private int intervalDay = 1;

    private Date mStartDate;
    private Date mEndDate;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        ButterKnife.bind(this);

        LinearLayout startDateLayout = (LinearLayout) findViewById(R.id.start_date_layout);
        LinearLayout endDateLayout = (LinearLayout) findViewById(R.id.end_date_layout);
        Button confirmBtn = (Button) findViewById(R.id.addtrip_activity_confirm_btn);

        String textTrip = "Trip to " + getIntent().getStringExtra("City");
        mTripNameTv.setText(textTrip);

        Date today = new Date();
        setInitialDate(mStartDateTv, today);
        //make it tommorow.
        setInitialDate(mEndDateTv, new Date(today.getTime() + (1000 * 60 * 60 * 24)));

        startDateLayout.setOnClickListener(this);
        endDateLayout.setOnClickListener(this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChooseAccommodationActivity.class);

                intervalDay = Util.convertDateToDayInterval(mEndDate, mStartDate);

                Log.d("Time", String.valueOf(intervalDay));
                intent.putExtra("Day", intervalDay);
                //TODO: Chaining intent put? is this bad practice?
                intent.putExtra("Latitude", getIntent().getDoubleExtra("Latitude", 0));
                intent.putExtra("Longitude", getIntent().getDoubleExtra("Longitude", 0));

                //Dont set the itinerary preview id for now.
                ItineraryPreview itineraryPreview = new ItineraryPreview(mTripNameTv.getText().toString(), getIntent().getStringExtra("City"), Util.getUserUid(), "", mStartDateTv.getText().toString(), mEndDateTv.getText().toString());
                //Itinerary Preview Data.
                intent.putExtra("ItineraryPreview", itineraryPreview);

                startActivity(intent);
            }
        });

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
        // for max date, 5 day after the start date. (including this day) (at least for current version).
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
            String leadingMonth = df.format(month);
            String dateString = leadingDay + "-" + leadingMonth + "-" + year;
            updateDisplay(mStartDateTv, dateString);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            //TODO: Handle this better, should write a function because we keep using the parsing method.
            Date date = null;
            try {
                date = dateFormat.parse(dateString);
                //set the local variable end date as the date format so we can find the day interval.
                mStartDate = date;
                Log.d("Time", mStartDate.toString());
                //if start date is updated, update the end date to be only 1 day after the start date.
                updateDisplay(mEndDateTv, String.valueOf(dateFormat.format(new Date(date.getTime() + (1000 * 60 * 60 * 24)))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            //add leading zeros to day less than 10.
            DecimalFormat df = new DecimalFormat("00");
            String leadingDay = df.format(day);
            String leadingMonth = df.format(month);
            String dateString = leadingDay + "-" + leadingMonth + "-" + year;
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date  = null;
            try {
                date = dateFormat.parse(dateString);
                //if start date is updated, update the end date to be only 1 day after the start date.

                //set the local variable end date as the date format so we can find the day interval.
                mEndDate = date;
                Log.d("Time", mEndDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            updateDisplay(mEndDateTv, dateString);
        }
    };

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

    private void setInitialDate(TextView dateTv, Date day) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//formating according to my need
        String date = formatter.format(day);
        dateTv.setText(date);
    }
}