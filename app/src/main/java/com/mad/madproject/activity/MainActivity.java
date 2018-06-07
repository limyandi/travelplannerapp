package com.mad.madproject.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.fragments.AboutFragment;
import com.mad.madproject.fragments.HolidayNewsFragment;
import com.mad.madproject.fragments.ItineraryPreviewFragment;
import com.mad.madproject.fragments.MyTripFragment;
import com.mad.madproject.fragments.SettingsFragment;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main Activity contains different fragments that can be displayed through a click of the option in the navigation drawer.
 * Also check if the user has a token session for authentication on, if it is off, user will be thrown to the login page.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    //TODO: This 3 attribute can be better if we choose to handle it in view model.
    private FirebaseAuth mAuth;
    //handle the listener for whether user has an existing token session for authentication.
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    //to get the details of the current mUser from the firebase.
    private User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        ButterKnife.bind(this);

        setAuthenticationListener();

        //initialize the drawertoggle using the constructor (Activity, DrawerLayout, String, String)
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open,
                R.string.close
        );

        //set the action bar so that the item can be viewed/seen. assert condition not null to prevent crash if it returns null pointer exception.
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //add the drawerToggle toggle to the layout
        drawerLayout.addDrawerListener(drawerToggle);
        //sync current state.
        drawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);

        //to get the header file of the navigation file.
        View header = navigationView.getHeaderView(0);
        //find the username textview from the header.
        final TextView username = (TextView) header.findViewById(R.id.username_nav_header);
        final TextView email = (TextView) header.findViewById(R.id.email_nav_header);

        //set the navigation item handler for each item.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selectedId = item.getItemId();

                menuHandler(selectedId);
                return true;
            }
        });

        //set the default checked item as the homepage (The main itinerary view).
        navigationView.setCheckedItem(R.id.homepage);

        setDefaultFragment();
        setUserDetails(username, email);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Handle what happens every time each item is clicked.
     * @param menuId the menu id param get the menu id of each item.
     */
    private void menuHandler(int menuId) {
        Fragment fragment = null;
        switch(menuId) {
            case R.id.homepage:
                fragment = new ItineraryPreviewFragment();
                break;
            case R.id.mytrip:
                fragment = new MyTripFragment();
                break;
            case R.id.aboutus:
                fragment = new AboutFragment();
                break;
            case R.id.holidaynews:
                fragment = new HolidayNewsFragment();
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                break;
            case R.id.logout:
                logOutHandler();
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        //after an option is clicked, close the drawer.
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void setAuthenticationListener() {
        //listener for if mUser is already logged out.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if(mUser == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    private void setDefaultFragment() {
        //Initialise the first fragment as the homepage (Where we can see the itinerary).
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, new ItineraryPreviewFragment());
        tx.commit();
    }

    private void logOutHandler() {
        mAuth.signOut();
    }

    //setting the listener
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Using firebase to set the user's username. (Should be in ViewModel Logic).
     * @param username
     * @param email
     */
    private void setUserDetails(final TextView username, final TextView email) {
        Util.getDatabaseReference("Users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                username.setText(currentUser != null ? currentUser.getUsername() : "Anonymous");
                email.setText(currentUser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
