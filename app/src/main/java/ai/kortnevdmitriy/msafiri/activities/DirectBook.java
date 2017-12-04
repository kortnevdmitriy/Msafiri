package ai.kortnevdmitriy.msafiri.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.adapters.SeatSelectionAdapter;
import ai.kortnevdmitriy.msafiri.entities.BookedVehicles;
import ai.kortnevdmitriy.msafiri.entities.TicketDetails;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;
import ai.kortnevdmitriy.msafiri.mpesa.api.ApiUtils;
import ai.kortnevdmitriy.msafiri.mpesa.api.QueryRequest;
import ai.kortnevdmitriy.msafiri.mpesa.api.STKPush;
import ai.kortnevdmitriy.msafiri.mpesa.api.StoreKey;
import ai.kortnevdmitriy.msafiri.mpesa.api.services.STKPushService;
import ai.kortnevdmitriy.msafiri.mpesa.app.Config;
import ai.kortnevdmitriy.msafiri.mpesa.utils.NotificationUtils;
import ai.kortnevdmitriy.msafiri.utilities.Item;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectBook extends AppCompatActivity {

    private static final String TAG = Home.class.getSimpleName();
    public Bitmap seatIcon;
    public Bitmap seatBooked;
    STKPushService stkPushService;
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    SeatSelectionAdapter seatSelectionAdapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String token = null;
    private String phone_number = "";
    private String mpesa_code = "";
    private String regId;
    private String recordByNumberOfSeats, recordByPriceInKsh, recordByKeyValue;
    private String seatNumber;
    private Item item;
    private List<String> listOfBookedVehicles = new ArrayList<>();
    private boolean paidCash;
    private FirebaseDatabase db;
    private VehicleDetails vehicleDetails;
    private BookedVehicles bookedVehicles;
    private TicketDetails ticketDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_book);
        recordByNumberOfSeats = getIntent().getStringExtra("recordByNumberOfSeats");
        recordByPriceInKsh = getIntent().getStringExtra("recordByPriceInKsh");
        recordByKeyValue = getIntent().getStringExtra("recordByKeyValue");

        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("bookedVehicles");

        // Read from the database by searching through the travel route children
        myRef.orderByKey().equalTo(recordByKeyValue).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                bookedVehicles = dataSnapshot.getValue(BookedVehicles.class);
                if (bookedVehicles != null) {
                    bookedVehicles.setKey(dataSnapshot.getKey());
                    if (bookedVehicles.getSeat1().equals("booked")) {
                        seatIsBooked(0);
                    }
                    if (bookedVehicles.getSeat2().equals("booked")) {
                        seatIsBooked(1);
                    }
                    if (bookedVehicles.getSeat3().equals("booked")) {
                        seatIsBooked(2);
                    }
                    if (bookedVehicles.getSeat4().equals("booked")) {
                        seatIsBooked(3);
                    }
                    if (bookedVehicles.getSeat5().equals("booked")) {
                        seatIsBooked(4);
                    }
                    if (bookedVehicles.getSeat6().equals("booked")) {
                        seatIsBooked(5);
                    }
                    if (bookedVehicles.getSeat7().equals("booked")) {
                        seatIsBooked(6);
                    }
                    if (bookedVehicles.getSeat8().equals("booked")) {
                        seatIsBooked(7);
                    }
                    if (bookedVehicles.getSeat9().equals("booked")) {
                        seatIsBooked(8);
                    }
                    if (bookedVehicles.getSeat10().equals("booked")) {
                        seatIsBooked(9);
                    }
                    if (bookedVehicles.getSeat11().equals("booked")) {
                        seatIsBooked(10);
                    }
                    if (bookedVehicles.getSeat12().equals("booked")) {
                        seatIsBooked(11);
                    }
                    if (bookedVehicles.getSeat13().equals("booked")) {
                        seatIsBooked(12);
                    }
                    if (bookedVehicles.getSeat14().equals("booked")) {
                        seatIsBooked(13);
                    }
                    if (bookedVehicles.getSeat15().equals("booked")) {
                        seatIsBooked(14);
                    }
                    if (bookedVehicles.getSeat16().equals("booked")) {
                        seatIsBooked(15);
                    }
                    if (bookedVehicles.getSeat17().equals("booked")) {
                        seatIsBooked(16);
                    }
                    if (bookedVehicles.getSeat18().equals("booked")) {
                        seatIsBooked(17);
                    }
                    if (bookedVehicles.getSeat19().equals("booked")) {
                        seatIsBooked(18);
                    }
                    if (bookedVehicles.getSeat20().equals("booked")) {
                        seatIsBooked(19);
                    }
                    if (bookedVehicles.getSeat21().equals("booked")) {
                        seatIsBooked(20);
                    }
                    if (bookedVehicles.getSeat22().equals("booked")) {
                        seatIsBooked(21);
                    }
                    if (bookedVehicles.getSeat23().equals("booked")) {
                        seatIsBooked(22);
                    }
                    if (bookedVehicles.getSeat24().equals("booked")) {
                        seatIsBooked(23);
                    }
                    if (bookedVehicles.getSeat25().equals("booked")) {
                        seatIsBooked(24);
                    }
                    if (bookedVehicles.getSeat26().equals("booked")) {
                        seatIsBooked(25);
                    }
                    if (bookedVehicles.getSeat27().equals("booked")) {
                        seatIsBooked(26);
                    }
                    if (bookedVehicles.getSeat28().equals("booked")) {
                        seatIsBooked(27);
                    }
                    if (bookedVehicles.getSeat29().equals("booked")) {
                        seatIsBooked(28);
                    }
                    if (bookedVehicles.getSeat30().equals("booked")) {
                        seatIsBooked(29);
                    }
                    if (bookedVehicles.getSeat31().equals("booked")) {
                        seatIsBooked(30);
                    }
                    if (bookedVehicles.getSeat32().equals("booked")) {
                        seatIsBooked(31);
                    }
                    if (bookedVehicles.getSeat33().equals("booked")) {
                        seatIsBooked(32);
                    }
                    if (bookedVehicles.getSeat34().equals("booked")) {
                        seatIsBooked(33);
                    }
                    if (bookedVehicles.getSeat35().equals("booked")) {
                        seatIsBooked(34);
                    }
                    if (bookedVehicles.getSeat36().equals("booked")) {
                        seatIsBooked(35);
                    }
                    if (bookedVehicles.getSeat37().equals("booked")) {
                        seatIsBooked(36);
                    }
                    if (bookedVehicles.getSeat38().equals("booked")) {
                        seatIsBooked(37);
                    }
                    if (bookedVehicles.getSeat39().equals("booked")) {
                        seatIsBooked(38);
                    }
                    if (bookedVehicles.getSeat40().equals("booked")) {
                        seatIsBooked(39);
                    }

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
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        seatIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_avl);
        seatBooked = BitmapFactory.decodeResource(this.getResources(), R.drawable.seat_layout_screen_nor_std);
        totalSeat(Integer.parseInt(recordByNumberOfSeats));
        gridView = findViewById(R.id.gridView1);
        seatSelectionAdapter = new SeatSelectionAdapter(this, R.layout.seatrow_grid, gridArray);
        gridView.setAdapter(seatSelectionAdapter);

        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = gridArray.get(position);
                seatNumber = item.getTitle();
                Log.d("Clicked Grid Item: ", seatNumber);

                Bitmap seatcompare = item.getImage();
                if (seatcompare == seatIcon) {
                    seatSelected(position);
                } else {
                    seatDeselected(position);
                }
            }
        });

        //Use credentials from your Lipa na MPESA Online(MPesa Express) App from the developer portal
        getToken("GoDmHpTEG6bvLdXzIW0oaidG9QS11I2l", "LYLmSXBMyk5W5CUC");


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    getFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    createNotification(message);
                    showResultdialog(message);
                }
            }
        };

        getFirebaseRegId();
    }

    public String getToken(String clientKey, String clientSectret) {

        try {
            String keys = clientKey + ":" + clientSectret;
            String base64 = Base64.encodeToString(keys.getBytes(), Base64.DEFAULT);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                    .get()
                    .addHeader("authorization", "Basic " + base64.trim())
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "b0432d90-dc69-1b08-e289-695651a7d5dd")
                    .build();

            client.newCall(request)
                    .enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DirectBook.this, "Fetching token failed", Toast.LENGTH_LONG).show();
                                }
                            });
                        }


                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            String res = response.body().string();
                            token = res;

                            JsonParser jsonParser = new JsonParser();
                            JsonObject jo = jsonParser.parse(token).getAsJsonObject();
                            Assert.assertNotNull(jo);
                            //Log.e("Token", token + jo.get("access_token"));
                            token = jo.get("access_token").getAsString();
                            Log.e("access_token", token);
                            Log.e("expires_in", jo.get("expires_in").getAsString());
                            stkPushService = ApiUtils.getTasksService(token);
                        }

                    });
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(DirectBook.this, "Please add your app credentials", Toast.LENGTH_LONG).show();
        }
        return token;
    }

    public void getPhoneNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Customer's Safaricom phone number (254XXX) to checkout Kshs " + "1");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setText("2547");
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phone_number = input.getText().toString();
                try {
                    performSTKPush(phone_number);
                } catch (Exception e) {
                    Toast.makeText(DirectBook.this, "Error fetchng token", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void mpesaTransactionCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the MPESA code received as is");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        //input.setText("2547");
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mpesa_code = input.getText().toString();
                try {
                    postTicketReceipt();
                } catch (Exception e) {
                    Toast.makeText(DirectBook.this, "Error fetchng token", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void performSTKPush(String phone_number) {
        Log.e("Checkout button clicked", "Button Clicked");
        STKPush stkPush = new STKPush("174379",
                "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3",
                "20160216165627",
                "CustomerPayBillOnline",
                "1",
                phone_number,
                "174379",
                phone_number,
                "https://spurquoteapp.ga/pusher/pusher.php?companyName=stk_push&message=result&push_type=individual&regId=" + regId,
                "test",
                "test");

        Log.e("Party B", phone_number);

        Call<STKPush> call = stkPushService.sendPush(stkPush);
        call.enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                try {
                    //Log.e("Response SUccess", response.toString());
                    if (response.isSuccessful()) {
                        Log.e(TAG, "post submitted to API." + response.message());

                    } else {
                        Log.e("Response", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<STKPush> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API." + t.getMessage());
                t.printStackTrace();
                Log.e("Error message", t.getLocalizedMessage());
            }
        });
        //Log.e("Method end", "method end");
    }

    private void getFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.d(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            StoreKey storeKey = new StoreKey(DirectBook.this);
            storeKey.createKey(regId);
        }
    }

    public void createNotification(String content) {
        Notification noti = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            noti = new Notification.Builder(this)
                    .setContentTitle(content)
                    .setContentText("Subject").setSmallIcon(R.mipmap.ic_launcher).build();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        assert noti != null;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        assert notificationManager != null;
        notificationManager.notify(0, noti);

    }

    public void showResultdialog(String result) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    // displays the total number of seats for a vehicle
    public void totalSeat(int n) {
        for (int i = 1; i <= n; ++i) {
            gridArray.add(new Item(seatIcon, "seat" + i));
        }
    }


    // handles the seat selections
    public void seatSelected(int pos) {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        db.getReference().child("bookedVehicles").child(recordByKeyValue).child(seatNumber).setValue("booked");
    }

    // handles the seat booked
    public void seatIsBooked(int pos) {
        gridArray.remove(pos);
        gridArray.add(pos, new Item(seatBooked, "Booked"));
        seatSelectionAdapter.notifyDataSetChanged();
    }

    // handles the seat deselection
    public void seatDeselected(int pos) {
        gridArray.remove(pos);
        int i = pos + 1;
        gridArray.add(pos, new Item(seatBooked, "Booked"));
        Toast.makeText(getApplicationContext(), "Booked Already", Toast.LENGTH_SHORT).show();
        seatSelectionAdapter.notifyDataSetChanged();
    }

    public void performTransactionQuery() {
        QueryRequest queryRequest = new QueryRequest("174379"
                , "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3"
                , "20160216165627", "");

        Call<QueryRequest> requestCall = stkPushService.sendRequest(queryRequest);
        requestCall.enqueue(new Callback<QueryRequest>() {
            @Override
            public void onResponse(Call<QueryRequest> call, Response<QueryRequest> response) {
                try {
                    //Log.e("Response SUccess", response.toString());
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Query successful response" + response.body().toString());


                    } else {
                        Log.e("Response unsuccessful", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<QueryRequest> call, Throwable t) {
                Log.e(TAG, "Unable to Query" + t.getMessage());
                t.printStackTrace();
                Log.e("Error message", t.getLocalizedMessage());
            }
        });
    }

    // allows posting of the booking transaction as receipt details.
    public void postTicketReceipt() {
        // Access a Firebase Real Database instance from your Activity
        db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference().child("vehicles"); // Database child name

        // Read from the database by querying using the orderByKey to return values by key.
        myRef.orderByKey().equalTo(recordByKeyValue).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                vehicleDetails = dataSnapshot.getValue(VehicleDetails.class);
                Log.d(TAG, "Value is: " + vehicleDetails);
                if (vehicleDetails != null) {
                    vehicleDetails.setKey(dataSnapshot.getKey());
                    ticketDetails = new TicketDetails();
                    ticketDetails.setCompanyName(vehicleDetails.getCompanyName());
                    ticketDetails.setBoardingPoint(vehicleDetails.getBoardingPoint());
                    ticketDetails.setDepartureTime(vehicleDetails.getDepartureTime());
                    ticketDetails.setNumberPlate(vehicleDetails.getNumberPlate());
                    ticketDetails.setPriceInKsh(vehicleDetails.getPriceInKsh());
                    ticketDetails.setTravelRoute(vehicleDetails.getTravelRoute());
                    ticketDetails.setVehicleType(vehicleDetails.getVehicleType());
                    ticketDetails.setSeatNumber(seatNumber);
                    ticketDetails.setMpesaTransactionCode(mpesa_code);
                }
                String uid = FirebaseAuth.getInstance().getUid();
                db.getReference().child("tickets").push().setValue(ticketDetails);
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

    // perform db check to see which seats are empty.
    public void queryForEmptySeats() {


    }

}
