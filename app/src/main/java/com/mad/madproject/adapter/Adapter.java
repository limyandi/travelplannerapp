//package com.mad.madproject.adapter;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.text.style.StyleSpan;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.Toast;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.places.AutocompleteFilter;
//import com.google.android.gms.location.places.AutocompletePrediction;
//import com.google.android.gms.location.places.AutocompletePredictionBuffer;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;
//import com.google.android.gms.maps.model.LatLngBounds;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by limyandivicotrico on 5/9/18.
// */
//
//public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
//
//    private static final String TAG = "PlacesAutoCompleteAdapter";
//    // or ArrayList<AutocompletePrediction>???
//    private ArrayList<PlaceAutocomplete> mResultList;
//    private GoogleApiClient mGoogleApiClient;
//    private LatLngBounds mBounds;
//    private AutocompleteFilter mPlaceFilter;
//
//    private Context mContext;
//    private int layout;
//
//    public Adapter(Context context, int resource, GoogleApiClient googleApiClient,
//                                     LatLngBounds bounds, AutocompleteFilter filter) {
//        mContext = context;
//        layout = resource;
//        mGoogleApiClient = googleApiClient;
//        mBounds = bounds;
//        mPlaceFilter = filter;
//    }
//
//    @NonNull
//    @Override
//    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//                // Skip the autocomplete query if no constraints are given.
//                if (constraint != null) {
//                    // Query the autocomplete API for the (constraint) search string.
//                    mResultList = getAutocomplete(constraint);
//                    if (mResultList != null) {
//                        // The API successfully returned results.
//                        results.values = mResultList;
//                        results.count = mResultList.size();
//                    }
//                }
//                return results;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if (results != null && results.count > 0) {
//                    // The API returned at least one result, update the data.
//                    notifyDataSetChanged();
//                } else {
//                    // The API did not return any results, invalidate the data set.
//                    //notifyDataSetInvalidated();
//                }
//            }
//        };
//        return filter;
//    }
//
//    private ArrayList<PlaceAutocomplete> getAutocomplete(CharSequence constraint) {
//        if (mGoogleApiClient.isConnected()) {
//            Log.i("", "Starting autocomplete query for: " + constraint);
//
//            // Submit the query to the autocomplete API and retrieve a PendingResult that will
//            // contain the results when the query completes.
//            PendingResult<AutocompletePredictionBuffer> results =
//                    Places.GeoDataApi
//                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
//                                    mBounds, mPlaceFilter);
//
//            // This method should have been called off the main UI thread. Block and wait for at most 60s
//            // for a result from the API.
//            AutocompletePredictionBuffer autocompletePredictions = results
//                    .await(60, TimeUnit.SECONDS);
//
//            // Confirm that the query completed successfully, otherwise return null
//            final Status status = autocompletePredictions.getStatus();
//            if (!status.isSuccess()) {
//                Toast.makeText(mContext, "Error contacting API: " + status.toString(),
//                        Toast.LENGTH_SHORT).show();
//                Log.e("", "Error getting autocomplete prediction API call: " + status.toString());
//                autocompletePredictions.release();
//                return null;
//            }
//
//            Log.i("", "Query completed. Received " + autocompletePredictions.getCount()
//                    + " predictions.");
//
//            // Copy the results into our own data structure, because we can't hold onto the buffer.
//            // AutocompletePrediction objects encapsulate the API response (place ID and description).
//
//            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
//            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
//            while (iterator.hasNext()) {
//                AutocompletePrediction prediction = iterator.next();
//                // Get the details of this prediction and copy it into a new PlaceAutocomplete object.
//                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(),
//                        prediction.getPrimaryText(new StyleSpan(Typeface.BOLD))));
//            }
//
//            // Release the buffer now that all data has been copied.
//            autocompletePredictions.release();
//
//            return resultList;
//        }
//        Log.e("", "Google API client is not connected for autocomplete query.");
//        return null;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//}
