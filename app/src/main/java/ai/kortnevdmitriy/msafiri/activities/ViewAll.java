package ai.kortnevdmitriy.msafiri.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.VehicleRegistrationAdapter;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class ViewAll extends AppCompatActivity {

    private final String TAG = "View All";
    private FirebaseFirestore db;
    private RecyclerView viewAllList;
    private VehicleRegistrationAdapter mAdapter;
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
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        db.collection("vehicles")
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
        viewAllList = findViewById(R.id.listView_ViewAll);
        mAdapter = new VehicleRegistrationAdapter(vehicleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        viewAllList.setLayoutManager(mLayoutManager);
        viewAllList.setItemAnimator(new DefaultItemAnimator());
        viewAllList.setAdapter(mAdapter);
        vehicleList.add(details);
        mAdapter.notifyDataSetChanged();
    }

}
