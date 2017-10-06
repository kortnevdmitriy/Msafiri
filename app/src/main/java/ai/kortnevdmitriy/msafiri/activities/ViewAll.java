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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        db.collection("vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                VehicleDetails details = document.toObject(VehicleDetails.class);
                                // Create a RecyclerView & find it's view by id to populate it with news articles
                                viewAllList = findViewById(R.id.listView_ViewAll);
                                mAdapter = new VehicleRegistrationAdapter(vehicleList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                viewAllList.setLayoutManager(mLayoutManager);
                                viewAllList.setItemAnimator(new DefaultItemAnimator());
                                viewAllList.setAdapter(mAdapter);
                                vehicleList.add(details);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    // This method is a hardcode for the news articles
    private void prepareNewsData() {
        VehicleDetails movie = new VehicleDetails("Court urged to stop Nasa bid to remove IEBC", "Daily Nation", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Supreme Court judges absolved President Uhuru Kenyatta from any blame in the August 8 General Election, as they gave their reasons for annulling last month’s presidential vote results.", "The Standard", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Nairobi Governor Mike Sonko pledges to overhaul archaic by-laws to align them with the constitution and introduce training systems and reward schems for inspectorate officers who excel in their work ", "Daily Nation", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("A bio-chemist from Murangâ county has petitioned the national assembly to have controlled bhang farming legalized in the country.", "The Star", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Streamlining the matatu industry is a tall order for Governor Sonko", "People Daily", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("MaragaPetition Mbunge wa Nyeri Mjini Ngunjiri Wambugu afika mahahakani kuwasilisha kesi ya kumng'oa Jaji Mkuu ", "Taifa Leo", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Kenyas private sector credit growth dropped to 3.6 per cent in the first seven months of 2017, a new report shows. The new level is the first incidence of contraction in the last seven years, a drop from 5.1 per cent recorded in 2016.", "Business Daily Africa", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("North Korea's foreign minister has brushed aside US President Donald Trump's fiery threat to destroy his nation, comparing it to a \"dog's bark\" and suggesting Pyongyang would not be deterred by the rhetoric.", "Daily Nation", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Uganda's Health State Minister is an agitated woman after she visited a public hospital and caught health workers demanding bribes to attend to patients.", "The Standard", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("Kenya's principal public debt is threatening to hit Sh5 trillion ceiling by the end of this year, moving from Sh3.82 trillion in December 2016 to Sh4.4 trillion by last month.", "The Star", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("iHub Limited, a company that runs an innovations incubation laboratory for techies, has been acquired by venture capital firm BSP Fund LLC for an undisclosed sum.", "Business Daily Africa", "2017");
        vehicleList.add(movie);

        movie = new VehicleDetails("How much longer will nurses remain on strike", "People Daily", "2017");
        vehicleList.add(movie);

        mAdapter.notifyDataSetChanged();
    }

}
