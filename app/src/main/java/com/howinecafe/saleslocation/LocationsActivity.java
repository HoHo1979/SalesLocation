package com.howinecafe.saleslocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.howinecafe.saleslocation.data.SalesLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationsActivity extends AppCompatActivity implements ChildEventListener, ValueEventListener {

    private static final String TAG = "**********"+LocationsActivity.class.getSimpleName();
    private static final int CLICK_OK = 100;
    private RecyclerView recyclerView;
    Set<SalesLocation> locationList = new HashSet<>();
    private LocationsViewAdapter locationsViewAdapter;
    private List<SalesLocation> salesList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==CLICK_OK){

            if(resultCode==RESULT_OK) {
                locationsViewAdapter.notifyDataSetChanged();
            }
        }

    }

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
        salesList = new ArrayList<>();
        salesList.addAll(locationList);
        locationsViewAdapter = new LocationsViewAdapter(salesList);
        recyclerView.setAdapter(locationsViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationsActivity.this,MapsActivity.class);
                startActivityForResult(intent,CLICK_OK);
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

        salesList.removeAll(locationList);

        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

             SalesLocation location =  dataSnapshot1.getValue(SalesLocation.class);
             locationList.add(location);

        }

        salesList.addAll(locationList);
        locationsViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
