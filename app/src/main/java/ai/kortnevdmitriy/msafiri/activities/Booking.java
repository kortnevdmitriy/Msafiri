package ai.kortnevdmitriy.msafiri.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class Booking extends AppCompatActivity {

    private final String TAG = Booking.class.getName();
    private TextView vehicleCompanyName, vehicleTravelRoute, vehicleNumberPlate, vehicleVehicleType, vehicledepartureTime;
    private TextView vehicleNumberOfSeats, vehiclePriceInKsh, vehicleBoardingPoint, vehicleOptionalDescription;
    private ImageView header_cover_image;
    private String data;
    private FirebaseDatabase db;
    private VehicleDetails vehicleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = getIntent().getStringExtra("recordsByKey");

        // find views by id
        vehicleCompanyName = findViewById(R.id.vehicleCompanyName);
        vehicleTravelRoute = findViewById(R.id.vehicleTravelRoute);
        header_cover_image = findViewById(R.id.header_cover_image);
        viewVehicleInformation();
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/msafiri-80193.appspot.com/o/vehicles%2Fhorizon.jpg?alt=media&token=77c33523-bf66-48e6-857d-e98f5d3726b6").into(header_cover_image);
    }

    // Access Database to retrieve data using a Search Algorithm
    private void viewVehicleInformation() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles");

        // Read from the database by searching through the travel route children
        myRef.orderByKey().equalTo(data).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                vehicleDetails = dataSnapshot.getValue(ai.kortnevdmitriy.msafiri.entities.VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    vehicleCompanyName.setText(vehicleDetails.getCompanyName());
                    vehicleTravelRoute.setText(vehicleDetails.getTravelRoute());
                }
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
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
