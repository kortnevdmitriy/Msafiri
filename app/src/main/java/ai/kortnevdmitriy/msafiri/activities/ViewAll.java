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
import ai.kortnevdmitriy.msafiri.adapters.ViewAllAdapter;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class ViewAll extends AppCompatActivity {

    private final String TAG = "View All";
    private FirebaseDatabase db;
    private RecyclerView viewAllList;
    private ViewAllAdapter mAdapter;
    private List<VehicleDetails> vehicleList = new ArrayList<>();
    private VehicleDetails details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readAllBookableVehicles();

    }

    // Access Database to retrieve data
    private void readAllBookableVehicles() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles");

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                details = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + details);
                if (details != null) {
                    details.setKey(dataSnapshot.getKey());
                    prepareAllVehicleData();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                mAdapter.notifyDataSetChanged();
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
        viewAllList = findViewById(R.id.listView_ViewAll);
        mAdapter = new ViewAllAdapter(vehicleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        viewAllList.setLayoutManager(mLayoutManager);
        viewAllList.setItemAnimator(new DefaultItemAnimator());
        viewAllList.setAdapter(mAdapter);
        vehicleList.add(details);
        mAdapter.notifyDataSetChanged();
    }

}
