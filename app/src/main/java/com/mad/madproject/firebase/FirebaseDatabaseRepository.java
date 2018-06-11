package com.mad.madproject.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.model.Itineraries;
import com.mad.madproject.model.Itinerary;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * The single source of truth for data for the database data retrieval and insert. (Repository).
 * TODO: Singleton pattern?? (Public static synchronized?).
 */
public class FirebaseDatabaseRepository {

    /**
     * Get user details
     * @param userUid the user firebase token id
     * @return the live data of user (to be observed).
     */
    public LiveData<User> getUserDetails(String userUid) {
        final MutableLiveData<User> user = new MutableLiveData<>();

        Util.getDatabaseReference("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setValue(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                user.setValue(null);
            }
        });

        return user;
    }

    public LiveData<ArrayList<ItineraryPreview>> getUpcomingItineraryPreviews(String userId) {
        final MutableLiveData<ArrayList<ItineraryPreview>> itineraryPreviewsLive = new MutableLiveData<>();
        final ArrayList<ItineraryPreview> itineraryPreviews = new ArrayList<>();

        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itineraryPreview: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryPreview.toString());
                    ItineraryPreview crawledView = itineraryPreview.getValue(ItineraryPreview.class);

                    Date endDate = new Date();
                    //get today's date
                    Date todayDate = new Date();

                    //if no error, overwrite end date with the date from database.
                    try {
                        Log.d(Constant.LOG_TAG, crawledView.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(crawledView.getEndDate());
                    } catch (ParseException e) {
                        Log.d(Constant.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    //if the end date is after today's date, don't add the itinerary to the list.
                    if(endDate.after(todayDate)) {
                        Log.d(Constant.LOG_TAG, crawledView.toString());
                        itineraryPreviews.add(crawledView);
                    }
                }
                if(itineraryPreviews.size() != 0) {
                    itineraryPreviewsLive.setValue(itineraryPreviews);
                }
                else {
                    itineraryPreviewsLive.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constant.LOG_TAG_MVVM, databaseError.getMessage());
                itineraryPreviewsLive.setValue(null);
            }
        });
        return itineraryPreviewsLive;
    }

    public LiveData<ArrayList<ItineraryPreview>> getAllItineraryPreviews(String userId) {
        final MutableLiveData<ArrayList<ItineraryPreview>> itineraryPreviewsLive = new MutableLiveData<>();
        final ArrayList<ItineraryPreview> itineraryPreviews = new ArrayList<>();

        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itineraryPreview: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryPreview.toString());
                    itineraryPreviews.add(itineraryPreview.getValue(ItineraryPreview.class));
                }
                if(itineraryPreviews.size() != 0) {
                    itineraryPreviewsLive.setValue(itineraryPreviews);
                }
                else {
                    itineraryPreviewsLive.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constant.LOG_TAG_MVVM, databaseError.getMessage());
                itineraryPreviewsLive.setValue(null);
            }
        });
        return itineraryPreviewsLive;
    }

    public LiveData<ArrayList<ItineraryPreview>> getPastItineraryPreviews(String userId) {
        final MutableLiveData<ArrayList<ItineraryPreview>> itineraryPreviewsLive = new MutableLiveData<>();
        final ArrayList<ItineraryPreview> itineraryPreviews = new ArrayList<>();

        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itineraryPreview: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryPreview.toString());
                    ItineraryPreview crawledView = itineraryPreview.getValue(ItineraryPreview.class);

                    Date endDate = new Date();
                    //get today's date
                    Date todayDate = new Date();

                    //if no error, overwrite end date with the date from database.
                    try {
                        Log.d(Constant.LOG_TAG, crawledView.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(crawledView.getEndDate());
                    } catch (ParseException e) {
                        Log.d(Constant.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    //if the end date is after today's date, don't add the itinerary to the list.
                    if(endDate.before(todayDate)) {
                        Log.d(Constant.LOG_TAG, crawledView.toString());
                        itineraryPreviews.add(crawledView);
                    }
                }
                if(itineraryPreviews.size() != 0) {
                    itineraryPreviewsLive.setValue(itineraryPreviews);
                }
                else {
                    itineraryPreviewsLive.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constant.LOG_TAG_MVVM, databaseError.getMessage());
                itineraryPreviewsLive.setValue(null);
            }
        });
        return itineraryPreviewsLive;
    }


    public void deletePastTrips(String userId) {
        Util.getDatabaseReference("ItineraryPreview").orderByChild("ownerId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itineraryView : dataSnapshot.getChildren()) {
                    ItineraryPreview itineraryPreview = itineraryView.getValue(ItineraryPreview.class);

                    Date endDate = new Date();
                    try {
                        Log.d(Constant.LOG_TAG, itineraryPreview.getEndDate());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        endDate = formatter.parse(itineraryPreview.getEndDate());
                    } catch (ParseException e) {
                        Log.d(Constant.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }

                    //check if the endDate past today's date
                    if (Util.isExpired(endDate, new Date())) {
                        //if yes, get the reference and then delete it from itinerary preview database.
                        itineraryView.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constant.LOG_TAG_MVVM, databaseError.getMessage());
            }
        });
    }

    public LiveData<Itineraries> getItineraryDetails(String itineraryPreviewId) {
        final MutableLiveData<Itineraries> itineraries = new MutableLiveData<>();


        Util.getDatabaseReference("Itinerary").orderByChild("itineraryPreviewId").equalTo(itineraryPreviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itineraries.setValue(dataSnapshot.getValue(Itineraries.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                itineraries.setValue(null);
            }
        });
        return itineraries;
    }

    public void deleteItinerary(String itineraryPreviewId) {
        Util.getDatabaseReference("ItineraryPreview").child(itineraryPreviewId).removeValue();
    }

}

