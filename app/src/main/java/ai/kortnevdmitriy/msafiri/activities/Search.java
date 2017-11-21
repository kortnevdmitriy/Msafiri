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
import ai.kortnevdmitriy.msafiri.adapters.SearchAdapter;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class Search extends AppCompatActivity {

    private final String TAG = "Search";
    private FirebaseDatabase db;
    private RecyclerView searchRecyclerView;
    private SearchAdapter searchAdapter;
    private List<VehicleDetails> listOfSearchedVehicles = new ArrayList<>();
    private VehicleDetails vehicleDetails;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = getIntent().getStringExtra("keyName");

        searchBookableVehicles();
    }

    // Access Database to retrieve data using a Search Algorithm
    private void searchBookableVehicles() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles");

        // Read from the database by searching through the travel route children
        myRef.orderByChild("travelRoute").equalTo(data).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                vehicleDetails = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    prepareAllVehicleData();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // This method prepares and loads data from the database.
    private void prepareAllVehicleData() {
        // Create a RecyclerView & find it's view by id to populate it with news articles
        searchRecyclerView = findViewById(R.id.listView_Search);
        searchAdapter = new SearchAdapter(listOfSearchedVehicles);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        searchRecyclerView.setLayoutManager(mLayoutManager);
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchRecyclerView.setAdapter(searchAdapter);
        listOfSearchedVehicles.add(vehicleDetails);
        searchAdapter.notifyDataSetChanged();
    }

}
