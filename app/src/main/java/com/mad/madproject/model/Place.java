package com.mad.madproject.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

public class Place {
    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("name")
    private String mName;
    @SerializedName("vicinity")
    private String mVicinity;


//    @BindingAdapter({"bind:imageUrl"})
//    public static void loadImage(ImageView view, String url) {
//        Glide.with(view.getContext()).load(url)
//                .crossFade()
//                .fitCenter()
//                .into(view);
//    }


    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        this.mGeometry = geometry;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        this.mVicinity = vicinity;
    }

}
