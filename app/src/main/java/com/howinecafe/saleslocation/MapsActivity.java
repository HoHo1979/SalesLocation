package com.howinecafe.saleslocation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.howinecafe.saleslocation.data.SalesLocation;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, OnCompleteListener<Void>, View.OnClickListener {

    private static final int REQUEST_LOCATION = 2;
    private static final String TAG = MapsActivity.class.getSimpleName() ;
    private GoogleMap mMap;
    private Button but_checkin;
    private Button but_checkout;
    private final int CHECK_IN=100;
    private final int CHECK_OUT=200;

    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        but_checkin = (Button) findViewById(R.id.but_checkin);
        but_checkin.setOnClickListener(this);
        but_checkout= (Button) findViewById(R.id.but_checkout);
        but_checkout.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            setupMyLocation();
        }
        // Add a marker in Sydney and move the camera

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //noinspection MissingPermission
                    setupMyLocation();
                } else {




                }
            break;

        }


    }

    private void setupMyLocation() {

        LatLng sydney = new LatLng(-33.867, 151.206);
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

//                Criteria criteria = new Criteria();
//                criteria.setAccuracy(Criteria.ACCURACY_FINE);

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                String provider = LocationManager.GPS_PROVIDER;
                //noinspection MissingPermission
                Location location = manager.getLastKnownLocation(provider);

                if(location!=null) {
                    Log.e(TAG,location.getLatitude()+" "+location.getLongitude()+"");
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Office"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));

                    Date date = new Date();
                    SalesLocation salesLocation = new SalesLocation(location.getLatitude(),location.getLongitude(),provider,date.getTime());
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("locations");
                    String key = reference.child(user.getUid()).push().getKey();

                    reference.child(user.getUid()).child(key)
                            .setValue(salesLocation)
                            .addOnCompleteListener(MapsActivity.this);

                    //update sales location record
                    DatabaseReference salesReference = FirebaseDatabase.getInstance().getReference("sales");
                    salesReference.child(user.getUid()).child("locations").child(key).setValue(true);


                }

                return false;
            }
        });
    }


    @Override
    public void onComplete(@NonNull Task<Void> task) {

        if(task.isSuccessful()){

            setResult(RESULT_OK);
            finish();

        }else{
            new AlertDialog.Builder(this).setTitle("Cannot save location")
                    .setMessage("Please change location and click again")
                    .show();
        }


    }

    @Override
    public void onClick(View v) {

        if(v==but_checkin){

            Log.i(TAG, "onClick: "+"Check in Button clicked");

        }else if(v==but_checkout){

            Log.d(TAG, "onClick: "+"Check out Button CLicked");

        }


    }
}
