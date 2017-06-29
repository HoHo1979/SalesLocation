package com.howinecafe.saleslocation;

import android.location.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howinecafe.saleslocation.LocationsViewAdapter;
import com.howinecafe.saleslocation.R;
import com.howinecafe.saleslocation.data.SalesLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationsActivity extends AppCompatActivity implements ChildEventListener, ValueEventListener {

    private static final String TAG = "**********"+LocationsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    List<SalesLocation> locationList = new ArrayList<>();
    private LocationsViewAdapter locationsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("locations").child(user.getUid()).addChildEventListener(this);
        database.getReference("locations").child(user.getUid()).addValueEventListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        locationsViewAdapter = new LocationsViewAdapter(locationList);
        recyclerView.setAdapter(locationsViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

             SalesLocation location =  dataSnapshot1.getValue(SalesLocation.class);
             locationList.add(location);

        }
        locationsViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
