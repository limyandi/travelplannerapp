package com.mad.madproject.chooseaccommodation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.retrofit.NearbyPlacesRepository;
import com.mad.madproject.R;
import com.mad.madproject.adapter.PlaceAutocompleteAdapter;
import com.mad.madproject.model.Accommodation;
import com.mad.madproject.model.Itineraries;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.PlacesResponse;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;
import com.mad.madproject.viewitinerary.ViewItineraryActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseAccommodationActivity extends AppCompatActivity implements OnMapReadyCallback {

    //widgets
    @BindView(R.id.input_search_et)
    AutoCompleteTextView mSearchText;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float CAMERA_ZOOM = 15f;

    //random number for the code.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mGoogleMap;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GeoDataClient mGeoDataClient;
    private GoogleApiClient mGoogleApiClient;

    double latitude = 0;
    double longitude = 0;

    private Accommodation mAccommodationInfo;
    private int numberOfTripDays;

    private int day = 0;
    private int startTime = 9;

    private ArrayList<ArrayList<com.mad.madproject.model.Place>> places = new ArrayList<>();
    private ArrayList<Itinerary> mItineraryArrayList = new ArrayList<>();

    private ProgressDialog mPrgDialog;

    //Database Reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    String itineraryPreviewKey = databaseReference.child("ItineraryPreview").push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_accommodation);

        ButterKnife.bind(this);

        //Latitude and longitude for the city.
        latitude = getIntent().getDoubleExtra(Constant.LATITUDE_KEY, 0);
        longitude = getIntent().getDoubleExtra(Constant.LONGITUDE_KEY, 0);
        //TODO: Number of trip days redundant, do not need this.
        numberOfTripDays = getIntent().getIntExtra(Constant.DAYS_KEY, 1);

        for (int i = 0; i < numberOfTripDays; i++) {
            places.add(new ArrayList<com.mad.madproject.model.Place>());
        }

        LatLng latLng = new LatLng(latitude, longitude);

        //Create the bound for the city, so that the autocompleteadapter will suggest mostly places that are close to the chosen city.
        //TODO: Does not seems like it is fully working yet.
        LatLngBounds cityBound = Util.toBounds(latLng, 0);

        mGeoDataClient = Places.getGeoDataClient(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(Constant.LOG_TAG, connectionResult.getErrorMessage());
                    }
                }).build();

        //set the filter to accommodation + exact address only.
        AutocompleteFilter accommodationFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE).build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(ChooseAccommodationActivity.this, mGeoDataClient, cityBound, accommodationFilter);

        //we need to set the search text to single line so that the keyevent can be read.
        //(Otherwise, the keyevent will not work and the geolocate function will never be called.)
        mSearchText.setSingleLine();

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);
        mSearchText.setOnItemClickListener(mAutoCompleteClickListener);

        //set drawable cancel on the right clicked.
        mSearchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //if the cancel button (drawable right button is clicked.
                    if (motionEvent.getRawX() >= (mSearchText.getRight() - mSearchText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //clear the button
                        mSearchText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(Constant.LOG_TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    //check all permissions.
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(Constant.LOG_TAG, "onRequestPermissionsResult: permission failed");
                            //if any of the permission is not granted, return.
                            return;
                        }
                    }
                    Log.d(Constant.LOG_TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map.
                    initMap();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constant.LOG_TAG, "onMapReady: map is ready.");
        mGoogleMap = googleMap;

        if (mLocationPermissionsGranted) {
            moveToCityLocation(CAMERA_ZOOM);
            initEditText();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //to make sure that we are not recreating a new one when the up navigation button is clicked.
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }

    private void getLocationPermission() {
        Log.d(Constant.LOG_TAG, "getting location permission");
        //After android marshmallow, we need to explicitly check the fine location and coarse location.
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    //TODO: This might be bad.
    private void initEditText() {
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                //handle when user is editing (or typing into) the textview, or is done editing the textview
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //execute our method for searching.
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void initMap() {
        Log.d(Constant.LOG_TAG, "initMap: initialising Map.");
        //refer to the map fragment we declared in xml.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //refer to the onMapReady listener.
        mapFragment.getMapAsync(ChooseAccommodationActivity.this);
    }

    private void moveToCityLocation(float zoom) {
        Log.d(Constant.LOG_TAG, "moveToCityLocation: Moving the camera to : lat: " + latitude + ", lng: " + longitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
    }

    private void moveCamera(final LatLng latLng, float zoom, Accommodation accommodationInfo) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mGoogleMap.clear();

        if (accommodationInfo != null) {
            try {
                //TODO: The Material Dialog here might not be a good idea.
                new MaterialDialog.Builder(this).title("Is this your accommodation address?").content(accommodationInfo.toString())
                        .positiveText(R.string.confirm).negativeText(R.string.cancel).positiveColor(getResources().getColor(R.color.green))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.d(Constant.LOG_TAG, mAccommodationInfo.toString());

                                //Start ProgressDialog
                                initProgressDialog();
                                getNearbyPlace(Util.getPlaceType(startTime), String.valueOf(mAccommodationInfo.getLatLng().latitude), String.valueOf(mAccommodationInfo.getLatLng().longitude));
                            }
                        }).show();
            } catch (NullPointerException e) {
                Log.e(Constant.LOG_TAG, "moveCamera: accommodation info is null, error: " + e.getMessage());
            }
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    private void geoLocate() {
        Log.d(Constant.LOG_TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(ChooseAccommodationActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(Constant.LOG_TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            //only get 0 because we want to get the details of the first in the list.
            Address address = list.get(0);

            Log.d(Constant.LOG_TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), CAMERA_ZOOM);
        }
    }

    private void getNearbyPlace(String type, final String latitude, final String longitude) {
        NearbyPlacesRepository.getInstance().getPlaces(type, latitude + "," + longitude, Constant.PROXIMITY_RADIUS, new NearbyPlacesRepository.NearbyPlacesCallback() {
            @Override
            public void onSuccess(PlacesResponse placesResponse) {
                if (placesResponse != null && placesResponse.getResults().size() != 0) {
                    if (places.get(day).size() < 6 && day != numberOfTripDays) {
                        int placeToGoIndex = Util.handlePlaceSearchIndexError(placesResponse.getResults().size());
                        placesResponse.getResults().get(placeToGoIndex).setTimeToGo(startTime);
                        String placeType = placesResponse.getResults().get(placeToGoIndex).getPlaceType();
                        places.get(day).add(placesResponse.getResults().get(placeToGoIndex));
                        double lat = placesResponse.getResults().get(placeToGoIndex).getGeometry().getLocation().getLat();
                        double lng = placesResponse.getResults().get(placeToGoIndex).getGeometry().getLocation().getLng();
                        placesResponse.getResults().get(placeToGoIndex).setTimeToGo(startTime);
                        //TODO: Fix static timing.
                        startTime += 2;
                        getNearbyPlace(placeType, String.valueOf(lat), String.valueOf(lng));
                    } else {
                        day++;
                        if (day != (numberOfTripDays)) {
                            //restart from the starting time again.
                            //static time.
                            startTime = 9;
                            //Restart from the accommodation again.
                            getNearbyPlace(Util.getPlaceType(startTime), String.valueOf(mAccommodationInfo.getLatLng().latitude), String.valueOf(mAccommodationInfo.getLatLng().longitude));
                        } else {
                            for (int j = 0; j < places.size(); j++) {
                                mItineraryArrayList.add(new Itinerary(places.get(j)));
                            }
                            ItineraryPreview itineraryPreview = (ItineraryPreview) getIntent().getSerializableExtra(Constant.ITINERARY_PREVIEW_KEY);
                            //set the key here now, we dont set it in add trip activity.
                            itineraryPreview.setItineraryPreviewId(itineraryPreviewKey);
                            Itineraries itineraries = new Itineraries(mItineraryArrayList, itineraryPreview.getTripName(), itineraryPreview.getStartDate(), itineraryPreview.getEndDate(), itineraryPreviewKey);
                            databaseReference.child("ItineraryPreview").child(itineraryPreviewKey).setValue(itineraryPreview);
                            databaseReference.child("Itinerary").push().setValue(itineraries);
                            //TODO: Handle progress dialog better.
                            mPrgDialog.dismiss();
                            Intent intent = new Intent(ChooseAccommodationActivity.this, ViewItineraryActivity.class);
                            intent.putExtra(Constant.DAYS_KEY, itineraryPreview.getDayInterval());
                            intent.putExtra(Constant.ITINERARY_PREVIEW_PUSH_KEY_KEY, itineraryPreviewKey);
                            startActivity(intent);
                        }
                    }
                } else {
                    places.clear();
                    mPrgDialog.dismiss();
                    new MaterialDialog.Builder(ChooseAccommodationActivity.this)
                            .title(R.string.sorry_text)
                            .content(R.string.failed_generate_itinerary_text)
                            .positiveText(R.string.close_text)
                            .show();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.d(Constant.LOG_TAG_MVVM, message);
            }
        });
    }

    private void initProgressDialog() {
        mPrgDialog = new ProgressDialog(ChooseAccommodationActivity.this);
        mPrgDialog.setMessage("Please wait while we are creating your itinerary");
        mPrgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPrgDialog.setCancelable(false);
        mPrgDialog.show();
    }

    //onItemClickListener, for each item in the dropdown list of the lists of accommodation that is clicked.
    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(pos);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            //set the request
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    //when the request is successfull, call this.
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(Constant.LOG_TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                //we need to release the placebuffer object when we dont need it anymore to prevent a memory leak
                places.release();
                return;
            }

            final Place place = places.get(0);

            try {
                mAccommodationInfo = new Accommodation();
                mAccommodationInfo.setName(place.getName().toString());
                mAccommodationInfo.setAddress(place.getAddress().toString());
                mAccommodationInfo.setLatLng(place.getLatLng());

                Log.d(Constant.LOG_TAG, "onResult: places: " + mAccommodationInfo.toString());
            } catch (NullPointerException e) {
                Log.e(Constant.LOG_TAG, "onResult: NullPointerException: " + e.getMessage());
            }

            moveCamera(place.getLatLng(), CAMERA_ZOOM, mAccommodationInfo);

            places.release();
        }
    };


}
