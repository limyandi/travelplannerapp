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

    //for handling if user google play services version is not valid / error
    private static final int ERROR_DIALOG_REQUEST = 9001;
    //Request to choose accommodation.
    private static final int CHOOSE_ACCOMMODATION_REQUEST = 1;

    private int intervalDay;

    private ArrayList<Trip> mTrips = new ArrayList<>();

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

                intervalDay = Util.convertDateToDayInterval(mEndDateTv.getText().toString(), mStartDateTv.getText().toString());
                intent.putExtra("Day", intervalDay);
                //TODO: Chaining intent put? is this bad practice?
                intent.putExtra("Latitude", getIntent().getDoubleExtra("Latitude", 0));
                intent.putExtra("Longitude", getIntent().getDoubleExtra("Longitude", 0));

                //Itinerary Preview Data.
//                intent.putExtra("PreviewKey", itineraryPreviewKey);

                //Dont set the itinerary preview id for now.
                ItineraryPreview itineraryPreview = new ItineraryPreview(mTripNameTv.getText().toString(), getIntent().getStringExtra("City"),  Util.getUserUid(), "", mStartDateTv.getText().toString(), mEndDateTv.getText().toString());
                //Itinerary Preview Data.
                intent.putExtra("ItineraryPreview", itineraryPreview);
//                databaseReference.child("ItineraryPreview").child(itineraryPreviewKey).setValue(itineraryPreview);

//                mockUpDataForItinerariesCreation();

                startActivity(intent);
            }
        });

    }

//    private void mockUpDataForItinerariesCreation() {
//
//        String tripName = mTripNameTv.getText().toString();
//        String startDate = mStartDateTv.getText().toString();
//        String endDate = mEndDateTv.getText().toString();
//        String itineraryPreviewId = itineraryPreviewKey;
//
//        ArrayList<Trip> trips = new ArrayList<>(6);
//
//        ArrayList<Itinerary> itinerariesLists = new ArrayList<>(intervalDay);
//
//        for(int i = 0; i < intervalDay; i++) {
//            trips.clear();
//            for(int j = 0; j < 6; j++) {
//                trips.add(mTrips.get(j));
//            }
//            Itinerary itinerary = new Itinerary(trips);
//            itinerariesLists.add(itinerary);
//        }
//
//        fullItinerary = new Itineraries(itinerariesLists, tripName, startDate, endDate, itineraryPreviewId);
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(Constant.LOG_TAG, "onActivityResult: Get Result from ChooseAccommodationActivity");
//        switch (requestCode) {
//            case CHOOSE_ACCOMMODATION_REQUEST:
//                if (resultCode == Activity.RESULT_OK) {
//                    mAccommodationDetailsTv.setText(data.getStringExtra("Accommodation Address"));
//                    mLatitude = data.getDoubleExtra("Accommodation Latitude", 0);
//                    mLongitude = data.getDoubleExtra("Accommodation Longitude", 0);
//
//                    String amusement_park = "amusement_park";
//                    String url = Util.getUrl(mLatitude, mLongitude, amusement_park);
//
////                    new GetNearbyPlacesData().execute((Object) url);
//                }
//        }
//    }

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
            String leadingMonth = df.format(month);
            String date = leadingDay + "-" + leadingMonth + "-" + year;
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
            String leadingMonth = df.format(month);
            String date = leadingDay + "-" + leadingMonth + "-" + year;
            updateDisplay(mEndDateTv, date);
        }
    };

    private void updateDisplay(TextView dateDisplay, String date) {
        dateDisplay.setText(date);
    }

    /**
     * Function so we can check if the google play services is working properly. This
     * function can be used by other class so only if the services is okay we can start doing something with the map.
     * In this version, we dont need to use this, but lets use this in the next version.
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



//    private class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
//
//        String googlePlacesData;
//        String url;
//        ProgressDialog mProgressDialog;
//
//        public GetNearbyPlacesData() {
//        }
//
//        @Override
//        protected String doInBackground(Object... objects) {
//            url = (String) objects[0];
//
//            try {
//                googlePlacesData = Utils.readUrl(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return googlePlacesData;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //TODO: Handle progress dialog.
//            mProgressDialog = new ProgressDialog(AddTripActivity.this);
//            mProgressDialog.setTitle("Fetching trip for your itinerary");
//            mProgressDialog.setMessage("Please wait...");
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            List<HashMap<String, String>> nearbyPlaceList = null;
//
//            //call to the data parser.
//            DataParser parser = new DataParser();
//            nearbyPlaceList = parser.parse(s);
//
//            //TODO: Handle if nearbyPlaceList is empty. SUGGEST USER TO CHOOSE ANOTHER CITY?
//            HashMap<String, String> googlePlace = nearbyPlaceList.get(mTrips.size() % 2);
//
//            String placeName = googlePlace.get("place_name");
//            double lat = Double.parseDouble(googlePlace.get("lat"));
//            double lng = Double.parseDouble(googlePlace.get("lng"));
//
//            mTrips.add(new Trip(placeName, "A", "10:00 A.M"));
//
//            String[] placeList = {"restaurant", "department_store", "gym", "store", "shopping mall", "casino"};
//
//            String url = Util.getUrl(lat, lng, placeList[mTrips.size() - 1]);
//
//            if(mTrips.size() != 6) {
//                new GetNearbyPlacesData().execute((Object) url);
//            }
//            //TODO: handle the progress dialog.
//            else {
//                //TODO: We should handle this one better.
//                mProgressDialog.dismiss();
//            }
//        }
//    }
}
