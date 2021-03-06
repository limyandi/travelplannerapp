package com.mad.madproject.viewitinerary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.model.Place;
import com.mad.madproject.utils.Constant;

import java.util.ArrayList;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

/**
 *  Adapter that holds the value to display to the user their itinerary details (where to go, what time).
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private ArrayList<Place> mTrips;
    private Context mContext;
    private LayoutInflater mInflater;

    /**
     * Constructor to be called by another class, requires the context to be called in and the content of the trips details.
     * @param trips lists of the trip object
     * @param context represents the environment for the adapter to be instantiated.
     */
    public TripAdapter(ArrayList<Place> trips, Context context) {
        mTrips = trips;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.itinerary_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        Log.d(Constant.LOG_TAG, "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, int position) {
        Place trip = mTrips.get(position);
        //set the time, might not be too good yet.
        String timeDisplay = String.valueOf(trip.getTimeToGo() + ":00");
        holder.itineraryTimeTv.setText(timeDisplay);
        holder.itineraryPlaceTv.setText(trip.getName());
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    /**
     * View holder class link the java object to all xml view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itineraryTimeTv, itineraryPlaceTv;

        public ViewHolder(View itemView) {
            super(itemView);
            itineraryTimeTv = (TextView) itemView.findViewById(R.id.itinerary_time);
            itineraryPlaceTv = (TextView) itemView.findViewById(R.id.itinerary_place);
        }
    }
}
