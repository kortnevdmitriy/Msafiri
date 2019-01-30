package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.models.VehicleDetails;

public class Booking extends AppCompatActivity {

    private final String TAG = Booking.class.getName();
    private TextView vehicleCompanyName, vehicleTravelRoute, vehicleNumberPlate, vehicleDepartureTime;
    private TextView vehicleNumberOfSeats, vehicleBoardingPoint, vehicleOptionalDescription;
    private ImageView header_cover_image;
    private String data;
    private FirebaseDatabase db;
    private VehicleDetails vehicleDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
//        Toolbar toolbar = findViewById(R.id.toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DirectBook.class);
                intent.putExtra("recordByNumberOfSeats", vehicleDetails.getNumberOfSeats());
                intent.putExtra("recordByPriceInKsh", vehicleDetails.getPriceInKsh());
                intent.putExtra("recordByKeyValue", vehicleDetails.getKey());
                startActivity(intent);
            }
        });
//        setSupportActionBar(toolbar);
        data = getIntent().getStringExtra("recordsByKey");

        // find views by id
        vehicleCompanyName = findViewById(R.id.vehicleCompanyName);
        vehicleTravelRoute = findViewById(R.id.vehicleTravelRoute);
        vehicleNumberPlate = findViewById(R.id.vehicleNumberPlate);
        vehicleDepartureTime = findViewById(R.id.vehicleDepatureTime);
        vehicleOptionalDescription = findViewById(R.id.vehicleOptionalDescription);
        vehicleBoardingPoint = findViewById(R.id.vehicleBoardingPoint);
        header_cover_image = findViewById(R.id.header_cover_image);
        viewVehicleInformation();


    }

    // Access Database to retrieve the full vehicle information
    private void viewVehicleInformation() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles"); // Database child name

        // Read from the database by querying using the orderByKey to return values by key.
        myRef.orderByKey().equalTo(data).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                vehicleDetails = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    vehicleCompanyName.setText(vehicleDetails.getCompanyName());
                    vehicleTravelRoute.setText(vehicleDetails.getTravelRoute());
                    vehicleNumberPlate.setText(vehicleDetails.getNumberPlate());
                    vehicleDepartureTime.setText(vehicleDetails.getDepartureTime());
                    vehicleOptionalDescription.setText(vehicleDetails.getOptionalDescription());
                    vehicleBoardingPoint.setText(vehicleDetails.getBoardingPoint());
                    Picasso.with(getApplicationContext()).load(Uri.parse(vehicleDetails.getImg_url())).into(header_cover_image);
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
