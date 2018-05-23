package com.mad.madproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.model.Trip;
import com.mad.madproject.utils.Constant;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by limyandivicotrico on 5/15/18.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private ArrayList<Trip> mTrip;
    private Context mContext;
    private LayoutInflater mInflater;

    public TripAdapter(ArrayList<Trip> trip, Context context) {
        mTrip = trip;
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
        Trip trip = mTrip.get(position);
        holder.itineraryTimeTv.setText(trip.getTripTime());
        holder.itineraryPlaceTv.setText(trip.getTripPlace());
        //TODO: Fix this later. The Image should be dynamic, depending on the place.
        holder.circleImageView.setImageResource(R.drawable.background);
    }

    @Override
    public int getItemCount() {
        return mTrip.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itineraryTimeTv, itineraryPlaceTv;
        public CircleImageView circleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itineraryTimeTv = (TextView) itemView.findViewById(R.id.itinerary_time);
            itineraryPlaceTv = (TextView) itemView.findViewById(R.id.itinerary_place);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.itinerary_place_image);
        }
    }
}
