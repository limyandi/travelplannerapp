package com.mad.madproject.adapter;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.mad.madproject.R;
import com.mad.madproject.activity.ViewItineraryActivity;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

public class ItineraryPreviewAdapter extends RecyclerView.Adapter<ItineraryPreviewAdapter.MyViewHolder> {

    private ArrayList<ItineraryPreview> dataSet;
    private Context context;
    private LayoutInflater inflater;
    private StorageReference mCityStorageReference;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView rootView;
        TextView tripNameTv;
        ImageView imageViewIcon;
        TextView placeTv;
        TextView inDaysTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.rootView = (CardView) itemView.findViewById(R.id.card_view);
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
        mCityStorageReference = Util.getStorageReference("city");
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
        final ItineraryPreview itineraryPreview = dataSet.get(listPosition);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewItineraryActivity.class);
                intent.putExtra("Day", itineraryPreview.getDayInterval());
                intent.putExtra("PreviewKey", itineraryPreview.getItineraryPreviewId());
                view.getContext().startActivity(intent);
            }
        });

        holder.tripNameTv.setText(itineraryPreview.getTripName());
        holder.placeTv.setText(itineraryPreview.getCity());
        holder.inDaysTv.setText(itineraryPreview.getDayInterval() + " days trip");
        //TODO: Fix this, should have its own picture.

        StorageReference cityReference = mCityStorageReference.child(itineraryPreview.getCity()+".jpg");
        Log.d("ItineraryPreview", itineraryPreview.getCity()+".jpg");
        //TODO: Set default picture if we cant find the picture?
        Glide.with(holder.imageViewIcon.getContext()).using(new FirebaseImageLoader()).load(cityReference).into(holder.imageViewIcon);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}