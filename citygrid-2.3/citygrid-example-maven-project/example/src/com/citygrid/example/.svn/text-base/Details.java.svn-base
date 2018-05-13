/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.example;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import com.citygrid.CGAddress;
import com.citygrid.CGException;
import com.citygrid.CityGrid;
import com.citygrid.content.offers.CGOffersLocation;
import com.citygrid.content.offers.CGOffersOffer;
import com.citygrid.content.offers.detail.CGOffersDetail;
import com.citygrid.content.offers.detail.CGOffersDetailResults;

public class Details extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        String offerId = getIntent().getExtras().getString("offerId");
        Log.d("Details", "Got offerId: " + offerId);

        CityGrid.setSimulation(false);
        CGOffersDetail detail = CityGrid.offersDetail();
        detail.setOfferId(offerId);

        CGOffersDetailResults results = null;
        String offerDetail = "You are seeing some placeholder text, something went wrong.  Rant goes here......";
        try {
            results = detail.detail();
        } catch (CGException e) {
            e.printStackTrace();
            StringBuilder sb = new StringBuilder("Exception retrieving offer details for offer ").append(offerId)
                    .append("Exception message: \n").append(e.getMessage());
            offerDetail = sb.toString();

        }

        if (results != null) {
            CGOffersOffer offer = results.getOffer();
            Log.d("Details", "Got CGOffersOffer: " + offer.toString());
            StringBuilder sb = new StringBuilder();
            CGOffersLocation location = offer.getLocations()[0];
            CGAddress address = location.getAddress();
            sb.append("Name: ").append(location.getName()).append("\n");

            sb.append("Offer: ").append(offer.getTitle()).append("\n\n");

            sb.append("Description: ").append(offer.getOfferDescription()).append("\n\n");

            if (offer.getStartDate() != null) {
                sb.append("Start Date: ").append(offer.getStartDate()).append("\n");
            }
            if (offer.getExpirationDate() != null) {
                sb.append("Expires: ").append(offer.getExpirationDate()).append("\n\n");
            }

            sb.append("Terms: ").append(offer.getTerms()).append("\n\n");

            sb.append("Address: ")
                    .append(address.getStreet()).append("; ")
                    .append(address.getCity()).append(", ")
                    .append(address.getState()).append(", ")
                    .append(address.getZip()).append("\n");
            if (location.getPhone() != null) {
                sb.append("Phone: ").append(location.getPhone()).append("\n");
            }
            offerDetail = sb.toString();
        }

        Log.d("Details", "Got offer detail: " + offerDetail);
        TextView textDetail = (TextView) findViewById(R.id.textDetail);
        textDetail.setText(offerDetail);
        textDetail.setMovementMethod(new ScrollingMovementMethod());


    }
}
