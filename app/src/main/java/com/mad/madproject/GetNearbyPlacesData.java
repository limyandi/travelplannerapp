package com.mad.madproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.madproject.activity.ViewItineraryActivity;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.Trip;
import com.mad.madproject.utils.Util;
import com.mad.madproject.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

/**
 * This class handles the nearby places retrieval.
 */
