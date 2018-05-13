/*
 * Created by Lolay, Inc.
 * Modified by Christophe VONG
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */

package com.citygrid.example;

import com.citygrid.CityGrid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener{
    
	String categories [];
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CityGrid.setPublisher("test");
        CityGrid.setSimulation(false);
        Toast.makeText(getApplicationContext(), "Choose a category", Toast.LENGTH_LONG).show();
        categories = new String [3];
        categories[0] = "Places"; categories[1] = "Offers"; categories[2] = "Reviews"; 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.main, categories));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(this);
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Toast.makeText(getApplicationContext(), "Let's search for " + this.categories[position] + "...", Toast.LENGTH_SHORT).show();
	    Intent intent = new Intent(this, Parameters.class);
	    intent.putExtra("category",this.categories[position]);
	    intent.putExtra("simulation",CityGrid.getSimulation());
	    startActivity(intent);
	}
}