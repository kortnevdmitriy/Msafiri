package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.SearchAdapter;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class Search extends AppCompatActivity {

    private final String TAG = "Search";
    private FirebaseFirestore db;
    private RecyclerView searchList;
    private SearchAdapter mAdapter;
    private List<VehicleDetails> vehicleSearchList = new ArrayList<>();
    private VehicleDetails details;
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

    // Access Database to retrieve data
    private void searchBookableVehicles() {
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        db.collection("vehicles")
                .whereEqualTo("travelRoute", data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                details = document.toObject(VehicleDetails.class);
                                prepareAllVehicleData();
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // This method prepares and loads data from the database.
    private void prepareAllVehicleData() {
        // Create a RecyclerView & find it's view by id to populate it with news articles
        searchList = findViewById(R.id.listView_Search);
        mAdapter = new SearchAdapter(vehicleSearchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        searchList.setLayoutManager(mLayoutManager);
        searchList.setItemAnimator(new DefaultItemAnimator());
        searchList.setAdapter(mAdapter);
        vehicleSearchList.add(details);
        mAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), DirectBook.class));
    }

}
