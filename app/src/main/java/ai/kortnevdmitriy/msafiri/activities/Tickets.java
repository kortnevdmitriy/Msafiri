package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.TicketsAdapter;
import ai.kortnevdmitriy.msafiri.entities.TicketDetails;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tickets extends AppCompatActivity {

    private final String TAG = Tickets.class.getName();
    private RecyclerView ticketsRecyclerView;
    private TicketsAdapter ticketsAdapter;
    private List<TicketDetails> listOfAllTickets = new ArrayList<>();
    private TicketDetails ticketsDetails;
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
        String uid = FirebaseAuth.getInstance().getUid();
        db = FirebaseDatabase.getInstance();
        assert uid != null;
        DatabaseReference myRef = db.getReference().child("tickets").child(uid);

        // Read from the database
        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ticketsDetails = dataSnapshot.getValue(TicketDetails.class);
                Log.d(TAG, "Value is: " + ticketsDetails);
                if (ticketsDetails != null) {
                    ticketsDetails.setKey(dataSnapshot.getKey());
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
            public void onItemClick(TicketDetails item) {
                String ticketKey = item.getKey();
                Intent intent = new Intent(getApplicationContext(), QRCode.class);
                intent.putExtra("ticketKeyValue", ticketKey);
                startActivity(intent);
            }
        });
        ticketsRecyclerView.setAdapter(ticketsAdapter);
        listOfAllTickets.add(ticketsDetails);
        Log.d(TAG, ticketsDetails.getCompanyName());
        ticketsAdapter.notifyDataSetChanged();
    }

}
