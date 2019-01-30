package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.ViewAllAdapter;
import ai.kortnevdmitriy.msafiri.models.VehicleDetails;

public class ViewAll extends AppCompatActivity {

    private final String TAG = "View All";
    private FirebaseDatabase db;
    private RecyclerView viewAllRecyclerView;
    private ViewAllAdapter viewAllAdapter;
    private List<VehicleDetails> listOfAllBookableVehicles = new ArrayList<>();
    private VehicleDetails vehicleDetails;
    private String recordsByKey;


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
                vehicleDetails = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    prepareAllVehicleData();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                viewAllAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                viewAllAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                viewAllAdapter.notifyDataSetChanged();
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
        viewAllRecyclerView = findViewById(R.id.listView_ViewAll);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        viewAllRecyclerView.setLayoutManager(mLayoutManager);
        viewAllRecyclerView.setItemAnimator(new DefaultItemAnimator());
        viewAllAdapter = new ViewAllAdapter(listOfAllBookableVehicles, new ViewAllAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VehicleDetails item) {
                recordsByKey = item.getKey();
                Intent intent = new Intent(getApplicationContext(), Booking.class);
                intent.putExtra("recordsByKey", recordsByKey);
                startActivity(intent);
            }
        });
        viewAllRecyclerView.setAdapter(viewAllAdapter);
        listOfAllBookableVehicles.add(vehicleDetails);
        viewAllAdapter.notifyDataSetChanged();
    }

}
