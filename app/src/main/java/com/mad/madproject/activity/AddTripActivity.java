package com.mad.madproject.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
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
import com.mad.madproject.R;
import com.mad.madproject.utils.Constant;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mStartDateTv, mEndDateTv;
    private TextView mTripNameTv, mAccommodationTv, mAccommodationDetailsTv;
    private Button confirmBtn;
    private LinearLayout mStartDateLayout, mEndDateLayout;

    //for handling if user google play services version is not valid / error
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int CHOOSE_ACCOMMODATION_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        mStartDateTv = (TextView) findViewById(R.id.start_date_tv);
        mEndDateTv = (TextView) findViewById(R.id.end_date_tv);
        mTripNameTv = (EditText) findViewById(R.id.addtrip_activity_trip_name);
        mAccommodationTv = (TextView) findViewById(R.id.addtrip_activity_accommodation);
        mAccommodationDetailsTv = (TextView) findViewById(R.id.addtrip_activity_accommodation_details);
        mStartDateLayout = (LinearLayout) findViewById(R.id.start_date_layout);
        mEndDateLayout = (LinearLayout) findViewById(R.id.end_date_layout);

        confirmBtn = (Button) findViewById(R.id.addtrip_activity_confirm_btn);

        String textTrip = "Trip to " + getIntent().getStringExtra("City");
        mTripNameTv.setText(textTrip);

        mStartDateLayout.setOnClickListener(this);
        mEndDateLayout.setOnClickListener(this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (isServicesOK()) initAddTripActivity();

        //cannot use this because api level is too low
        //mStartDateDp.setOnDateChangedListener();
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

        DatePickerDialog startDateDialog = new DatePickerDialog(AddTripActivity.this,
                android.R.style.Theme_Holo_Dialog,
                dateSetListener,
                year, month, day);
        startDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        startDateDialog.show();
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
            updateDisplay(mStartDateTv, date);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month = month + 1;
            String date = day + "/" + month + "/" + year;
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
