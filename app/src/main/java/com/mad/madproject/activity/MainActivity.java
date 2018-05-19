package com.mad.madproject.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.madproject.R;
import com.mad.madproject.adapter.ItineraryPreviewAdapter;
import com.mad.madproject.fragments.AboutFragment;
import com.mad.madproject.fragments.HolidayNewsFragment;
import com.mad.madproject.fragments.MyTripFragment;
import com.mad.madproject.fragments.SendFeedbackFragment;
import com.mad.madproject.fragments.SettingsFragment;
import com.mad.madproject.login.LoginActivity;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.model.User;
import com.mad.madproject.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private ItineraryPreviewAdapter mItineraryPreviewAdapter;
    private ArrayList<ItineraryPreview> mItineraryPreviewList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    //to get the details of the current user from the firebase
    private User currentUser;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersRef = database.getReference("Users");

    private TextView username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        ButterKnife.bind(this);

        //setting up the recycler view for viewing the created itinerary.
        mRecyclerView = (RecyclerView) findViewById(R.id.view_itinerary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TODO: Use async task for this, maybe do this during the splash screen?
        usersRef.child(user.getUid()).child("ItineraryPreview").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mItineraryPreviewList.clear();
                for(DataSnapshot itineraryView: dataSnapshot.getChildren()) {
                    Log.d(Constant.LOG_TAG, itineraryView.toString());
                    ItineraryPreview crawledView = itineraryView.getValue(ItineraryPreview.class);
                    Log.d(Constant.LOG_TAG, crawledView.toString());
                    mItineraryPreviewList.add(crawledView);
                }
                mItineraryPreviewAdapter = new ItineraryPreviewAdapter(MainActivity.this, mItineraryPreviewList);
                mRecyclerView.setAdapter(mItineraryPreviewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //initialize the drawertoggle using the constructor (Activity, DrawerLayout, String, String)
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open,
                R.string.close
        );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add the drawerToggle toggle to the layout
        drawerLayout.addDrawerListener(drawerToggle);
        //sync current state.
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);

        //to get the header file of the navigation file.
        View header = navigationView.getHeaderView(0);
        //find the username textview from the header.
        username = (TextView) header.findViewById(R.id.username_nav_header);

        //set the navigation item handler for each item.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selectedId = item.getItemId();

                menuHandler(selectedId);
                return true;
            }
        });

        //listener for if user is already logged out.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });

        //TODO: Might need async task because this takes some time //do something in the background.
        usersRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                username.setText(currentUser.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            case R.id.mytrip:
                fragment = new MyTripFragment();
                break;
            case R.id.answerquestions:
                answerQuestionsHandler();
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
            case R.id.sendfeedback:
                fragment = new SendFeedbackFragment();
                break;
            case R.id.logout:
                logOutHandler();
                break;
        }

        if(fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void answerQuestionsHandler() {
        Toast.makeText(MainActivity.this, "Answer Questions", Toast.LENGTH_SHORT).show();
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
}
