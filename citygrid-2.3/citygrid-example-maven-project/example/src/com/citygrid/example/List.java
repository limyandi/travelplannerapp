package com.citygrid.example;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.citygrid.CGException;
import com.citygrid.CityGrid;
import com.citygrid.content.offers.CGOffersOffer;
import com.citygrid.content.offers.search.CGOffersSearch;
import com.citygrid.content.offers.search.CGOffersSearchResults;


public class List extends ListActivity implements AdapterView.OnItemClickListener{
    CGOffersOffer[] cgOffers = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String zipCode = getIntent().getExtras().getString("zipCode");
		String[] sushiOffers = findSushiOffers(zipCode);
        Toast.makeText(getApplicationContext(), "Number of sushi offers found: " + sushiOffers.length, Toast.LENGTH_LONG).show();

		setListAdapter(new ArrayAdapter<String>(this, R.layout.list, sushiOffers));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(this);

    }


    private String[] findSushiOffers(String zip) {
		String[] offers = null;
		
        CityGrid.setPublisher("test");
        CityGrid.setSimulation(false);
        CGOffersSearch search = CityGrid.offersSearch();
        search.setWhat("sushi");
        search.setWhere(zip);

        CGOffersSearchResults results = null;
        try {
            results = search.search();
            
            if (results != null) {
            	 cgOffers =  results.getOffers();
            	if (cgOffers != null) {
            		offers = new String[cgOffers.length];
            		int i = 0;
            		for (CGOffersOffer offer : cgOffers) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(offer.getTitle()).append("\n");
                        if (offer.getExpirationDate() != null) {
                            sb.append("Ends: ").append(offer.getExpirationDate());
                        }
                        sb.append("Location: ").append(offer.getLocations()[0].getName()).append("\n");
            			offers[i++] = sb.toString();
            		}
            	}
            }
        } catch (CGException e) {
            Log.e("List Activity", "Exception finding sushi offers in " + zip );
            offers = new String[] {"Exception finding sushi offers: " + e.getMessage()};
        }
        return offers;		
	}


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Getting details of this offer, sit tight...",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Details.class);
        String offerId = cgOffers[position].getOfferId();
        intent.putExtra("offerId", offerId);
        startActivity(intent);
    }
}
