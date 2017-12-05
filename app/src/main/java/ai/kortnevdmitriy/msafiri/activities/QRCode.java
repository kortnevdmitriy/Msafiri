package ai.kortnevdmitriy.msafiri.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.TicketDetails;

public class QRCode extends AppCompatActivity {
    private final String TAG = QRCode.class.getName();
    private String data;
    private FirebaseDatabase db;
    private TicketDetails ticketsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String uid = FirebaseAuth.getInstance().getUid();
        assert uid != null;
        data = getIntent().getStringExtra("ticketKeyValue");
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("tickets").child(uid);

        // Read from the database by querying using the orderByKey to return values by key.
        myRef.orderByKey().equalTo(data).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ticketsDetails = dataSnapshot.getValue(TicketDetails.class);
                Log.d(TAG, "Value is: " + ticketsDetails);
                if (ticketsDetails != null) {
                    TicketDetails ticketDetails = new TicketDetails();
                    ticketDetails.setCompanyName(ticketsDetails.getCompanyName());
                    ticketDetails.setTravelRoute(ticketsDetails.getTravelRoute());
                    ticketDetails.setNumberPlate(ticketsDetails.getNumberPlate());
                    ticketDetails.setMpesaTransactionCode(ticketsDetails.getMpesaTransactionCode());
                    ticketDetails.setBoardingPoint(ticketsDetails.getBoardingPoint());
                    ticketDetails.setCompanyName(ticketsDetails.getDepartureTime());
                    ticketDetails.setSeatNumber(ticketsDetails.getSeatNumber());
                    ticketDetails.setVehicleType(ticketsDetails.getVehicleType());
                    ticketDetails.setPriceInKsh(ticketsDetails.getPriceInKsh());
                    Log.d(TAG, "onChildAdded: " + ticketDetails);
                    Bitmap myBitmap = net.glxn.qrgen.android.QRCode.from(String.valueOf(ticketDetails)).bitmap();
                    ImageView myImage = findViewById(R.id.imageViewQR);
                    myImage.setImageBitmap(myBitmap);
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
