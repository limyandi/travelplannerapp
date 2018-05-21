package com.mad.madproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mad.madproject.R;
import com.mad.madproject.adapter.CityAdapter;
import com.mad.madproject.adapter.PlaceAutocompleteAdapter;
import com.mad.madproject.model.Accommodation;
import com.mad.madproject.model.City;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCityActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private ArrayList<City> mCities = new ArrayList<>();

    @BindView(R.id.search_field)
    AutoCompleteTextView mSearchTextField;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private City mCityInfo;
    private GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialising the geo data client for the Google Places API for android.
        mGeoDataClient = Places.getGeoDataClient(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //TODO: Handle connection failed.
                    }
                }).build();

        //set the filter to city only.
        AutocompleteFilter cityFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        //intialising for the placeautocompleteadapter.
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, Constant.LAT_LNG_BOUNDS, cityFilter);

        mSearchTextField.setAdapter(mPlaceAutocompleteAdapter);
        mSearchTextField.setOnItemClickListener(mAutoCompleteClickListener);

        //initialising for the city adapter.
        CityAdapter cityAdapter = new CityAdapter(this, mCities);
        mRecyclerView.setAdapter(cityAdapter);

        mSearchTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate() {
        Log.d(Constant.LOG_TAG, "geoLocate: geolocating");

        String searchString = mSearchTextField.getText().toString();

        Geocoder geocoder = new Geocoder(SearchCityActivity.this);
        //List is abstract, we instantiate it with a new ArrayList.
        List<Address> addresses = new ArrayList<>();
        try {
            //maximum number of result is 1.
            addresses = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(Constant.LOG_TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if(addresses.size() > 0) {
            Address address = addresses.get(0);
            Log.d(Constant.LOG_TAG, "geoLocate: found a location:");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


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
            if(!places.getStatus().isSuccess()) {
                Log.d(Constant.LOG_TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                //we need to release the placebuffer object when we dont need it anymore to prevent a memory leak
                places.release();
                return;
            }

            final Place place = places.get(0);

            try {

                final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(place.getId());

//                photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
//                        // Get the list of photos.
//                        PlacePhotoMetadataResponse photos = task.getResult();
//                        // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
//                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
//                        // Get the first photo in the list.
//                        PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
//                        CharSequence attribution = photoMetadata.getAttributions();
//                        // Get a full-size bitmap for the photo.
//                        Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
//                        photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
//                            @Override
//                            public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
//                                PlacePhotoResponse photo = task.getResult();
//                                Bitmap bitmap = photo.getBitmap();
//                            }
//                        });
//
//                        Log.d(Constant.LOG_TAG, String.valueOf(photoMetadata.getAttributions()));
//                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                        StorageReference photoReference = storageRef.child("photos");
//
//                    }
//                });
                mCityInfo = new City();
                mCityInfo.setImage("");
                mCityInfo.setCity(place.getName().toString());
                mCityInfo.setCountry("");
                mCityInfo.setLatLng(place.getLatLng());
                Log.d(Constant.LOG_TAG, mCityInfo.getCity());
                Log.d(Constant.LOG_TAG, mCityInfo.getCountry());
                Log.d(Constant.LOG_TAG, "" + mCityInfo.getLatLng());

            } catch (NullPointerException e) {
                Log.e(Constant.LOG_TAG, "onResult: NullPointerException: " + e.getMessage());
            }



            places.release();
            Intent intent = new Intent(SearchCityActivity.this, AddTripActivity.class);

            intent.putExtra("City", mCityInfo.getCity());
            intent.putExtra("LatLng", mCityInfo.getLatLng());

            startActivity(intent);
        }
    };
}
