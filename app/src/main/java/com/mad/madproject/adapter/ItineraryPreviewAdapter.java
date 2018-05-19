package com.mad.madproject.adapter;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.model.ItineraryPreview;

import java.util.ArrayList;

public class ItineraryPreviewAdapter extends RecyclerView.Adapter<ItineraryPreviewAdapter.MyViewHolder> {

    private ArrayList<ItineraryPreview> dataSet;
    private Context context;
    private LayoutInflater inflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tripNameTv;
        ImageView imageViewIcon;
        TextView placeTv;
        TextView inDaysTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tripNameTv = (TextView) itemView.findViewById(R.id.activity_main_view_itinerary_trip_name);
            this.placeTv = (TextView) itemView.findViewById(R.id.activity_main_place);
            this.inDaysTv = (TextView) itemView.findViewById(R.id.activity_main_days);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.city_image_view);
        }
    }

    public ItineraryPreviewAdapter(Context context, ArrayList<ItineraryPreview> data) {
        this.context = context;
        this.dataSet = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = inflater
                .inflate(R.layout.itinerary_card_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        holder.tripNameTv.setText(dataSet.get(listPosition).getTripName());
        holder.placeTv.setText(dataSet.get(listPosition).getCity());
        holder.inDaysTv.setText(dataSet.get(listPosition).getLengthOfTrip() + " days trip");
        //TODO: Fix this, should have its own picture.
        holder.imageViewIcon.setImageResource(R.drawable.background);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}