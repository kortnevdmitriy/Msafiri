package ai.kortnevdmitriy.msafiri.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.TicketsAdapter;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class Tickets extends AppCompatActivity {

    private final String TAG = Tickets.class.getName();
    private RecyclerView ticketsRecyclerView;
    private TicketsAdapter ticketsAdapter;
    private List<VehicleDetails> listOfAllTickets = new ArrayList<>();
    private VehicleDetails vehicleDetails;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readAllTickets();
    }

    // Access Database to retrieve data
    private void readAllTickets() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles");

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                vehicleDetails = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    prepareAllTicketsData();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ticketsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ticketsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                ticketsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // This method prepares and loads data from the database.
    private void prepareAllTicketsData() {
        // Create a RecyclerView & find it's view by id to populate it with tickets
        ticketsRecyclerView = findViewById(R.id.ticketsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ticketsRecyclerView.setLayoutManager(mLayoutManager);
        ticketsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ticketsAdapter = new TicketsAdapter(listOfAllTickets, new TicketsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VehicleDetails item) {

            }
        });
        ticketsRecyclerView.setAdapter(ticketsAdapter);
        listOfAllTickets.add(vehicleDetails);
        Log.d(TAG, vehicleDetails.getCompanyName());
        ticketsAdapter.notifyDataSetChanged();
    }

}
