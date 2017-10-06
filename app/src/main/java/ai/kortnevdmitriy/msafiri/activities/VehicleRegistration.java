package ai.kortnevdmitriy.msafiri.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tapadoo.alerter.Alerter;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

public class VehicleRegistration extends AppCompatActivity {
    private final String TAG = "Vehicle Registration";
    private EditText editTextVehicleType, editTextRegistrationDetails, editTextCompanyName,
            editTextNumberOfSeats, editTextTravelRoute, editTextPriceOfTravel;
    private Button buttonSubmit;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        // Find the EditTexts Views by id
        editTextVehicleType = findViewById(R.id.editTextVehicleType);
        editTextRegistrationDetails = findViewById(R.id.editTextRegistrationDetails);
        editTextCompanyName = findViewById(R.id.editTextCompanyName);
        editTextNumberOfSeats = findViewById(R.id.editTextNumberOfSeats);
        editTextTravelRoute = findViewById(R.id.editTextTravelRoute);
        editTextPriceOfTravel = findViewById(R.id.editTextPriceOfTravel);

        // Handling the button setup and click
        buttonSubmit = findViewById(R.id.buttonVehicleTypeSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    /*
    * This method first gets the data contained in all the EditText and assigns those values to
    * String variables. Step two is to validate that the EditTexts were not empty at the time of
    * assigning extracted values using TextUtils so that we have ALL REQUIRED data before saving to
    * the database.
    */
    public void submitData() {
        String strVehicleType = editTextVehicleType.getText().toString().trim();
        String strRegistrationDetails = editTextRegistrationDetails.getText().toString().trim();
        String strCompanyName = editTextCompanyName.getText().toString().trim();
        String strTravelRoute = editTextTravelRoute.getText().toString().trim();
        String strNumberOfSeats = editTextNumberOfSeats.getText().toString().trim();
        String strPriceOfTravel = editTextPriceOfTravel.getText().toString().trim();

        if (TextUtils.isEmpty(strVehicleType)) {
            Toast.makeText(getApplicationContext(), "Fill Vehicle Type!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strRegistrationDetails)) {
            Toast.makeText(getApplicationContext(), "Fill Registration Details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strCompanyName)) {
            Toast.makeText(getApplicationContext(), "Fill Company Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strNumberOfSeats)) {
            Toast.makeText(getApplicationContext(), "Fill Number of Seats", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strTravelRoute)) {
            Toast.makeText(getApplicationContext(), "Fill Travel Route", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(strPriceOfTravel)) {
            Toast.makeText(getApplicationContext(), "Fill Price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an object to pass to the database for storage
        VehicleDetails vehicleDetails = new VehicleDetails(strVehicleType, strRegistrationDetails, strCompanyName, strNumberOfSeats, strTravelRoute, strPriceOfTravel);

        // Add a new document with a generated ID
        db.collection("vehicles")
                .add(vehicleDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        successMessageVehicleRegistration();
                        updateUI();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "Error Submitting", Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Call this method after posting to empty the EditTexts
    public void updateUI() {
        editTextVehicleType.setText("");
        editTextRegistrationDetails.setText("");
        editTextCompanyName.setText("");
        editTextNumberOfSeats.setText("");
        editTextTravelRoute.setText("");
        editTextPriceOfTravel.setText("");
    }

    // Success Message
    public void successMessageVehicleRegistration() {
        Alerter.create(this)
                .setTitle("Vehicle Registration")
                .setText("Data submitted successfully")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }
}
