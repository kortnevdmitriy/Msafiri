package ai.kortnevdmitriy.msafiri.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

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
                    try {
                        if (!"booked".equals(bookedVehicles.getSeat1())) {
                            seatIsUnbooked(0);
                        } else seatIsBooked(0);
                        if (!"booked".equals(bookedVehicles.getSeat2())) {
                            seatIsUnbooked(1);
                        } else seatIsBooked(1);
                        if (!"booked".equals(bookedVehicles.getSeat3())) {
                            seatIsUnbooked(2);
                        } else seatIsBooked(2);
                        if (!"booked".equals(bookedVehicles.getSeat4())) {
                            seatIsUnbooked(3);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat5())) {
                            seatIsUnbooked(4);
                        } else seatIsBooked(4);
                        if (!"booked".equals(bookedVehicles.getSeat6())) {
                            seatIsUnbooked(5);
                        } else seatIsBooked(5);
                        if (!"booked".equals(bookedVehicles.getSeat7())) {
                            seatIsUnbooked(6);
                        } else seatIsBooked(6);
                        if (!"booked".equals(bookedVehicles.getSeat8())) {
                            seatIsUnbooked(7);
                        } else seatIsBooked(7);
                        if (!"booked".equals(bookedVehicles.getSeat9())) {
                            seatIsUnbooked(8);
                        } else seatIsBooked(8);
                        if (!"booked".equals(bookedVehicles.getSeat10())) {
                            seatIsUnbooked(9);
                        } else seatIsBooked(9);
                        if (!"booked".equals(bookedVehicles.getSeat11())) {
                            seatIsUnbooked(10);
                        } else seatIsBooked(10);
                        if (!"booked".equals(bookedVehicles.getSeat12())) {
                            seatIsUnbooked(11);
                        } else seatIsBooked(11);
                        if (!"booked".equals(bookedVehicles.getSeat13())) {
                            seatIsUnbooked(12);
                        } else seatIsBooked(12);
                        if (!"booked".equals(bookedVehicles.getSeat14())) {
                            seatIsUnbooked(13);
                        } else seatIsBooked(13);
                        if (!"booked".equals(bookedVehicles.getSeat15())) {
                            seatIsUnbooked(14);
                        } else seatIsBooked(14);
                        if (!"booked".equals(bookedVehicles.getSeat16())) {
                            seatIsUnbooked(15);
                        } else seatIsBooked(15);
                        if (!"booked".equals(bookedVehicles.getSeat17())) {
                            seatIsUnbooked(16);
                        } else seatIsBooked(16);
                        if (!"booked".equals(bookedVehicles.getSeat18())) {
                            seatIsUnbooked(17);
                        } else seatIsBooked(17);
                        if (!"booked".equals(bookedVehicles.getSeat19())) {
                            seatIsUnbooked(18);
                        } else seatIsBooked(18);
                        if (!"booked".equals(bookedVehicles.getSeat20())) {
                            seatIsUnbooked(19);
                        } else seatIsBooked(19);
                        if (!"booked".equals(bookedVehicles.getSeat21())) {
                            seatIsUnbooked(20);
                        } else seatIsBooked(20);
                        if (!"booked".equals(bookedVehicles.getSeat22())) {
                            seatIsUnbooked(21);
                        } else seatIsBooked(21);
                        if (!"booked".equals(bookedVehicles.getSeat23())) {
                            seatIsUnbooked(22);
                        } else seatIsBooked(22);
                        if (!"booked".equals(bookedVehicles.getSeat24())) {
                            seatIsUnbooked(23);
                        } else seatIsBooked(23);
                        if (!"booked".equals(bookedVehicles.getSeat25())) {
                            seatIsUnbooked(24);
                        } else seatIsBooked(24);
                        if (!"booked".equals(bookedVehicles.getSeat26())) {
                            seatIsUnbooked(25);
                        } else seatIsBooked(25);
                        if (!"booked".equals(bookedVehicles.getSeat27())) {
                            seatIsUnbooked(26);
                        } else seatIsBooked(26);
                        if (!"booked".equals(bookedVehicles.getSeat28())) {
                            seatIsUnbooked(27);
                        } else seatIsBooked(27);
                        if (!"booked".equals(bookedVehicles.getSeat29())) {
                            seatIsUnbooked(28);
                        } else seatIsBooked(28);
                        if (!"booked".equals(bookedVehicles.getSeat30())) {
                            seatIsUnbooked(29);
                        } else seatIsBooked(29);
                        if (!"booked".equals(bookedVehicles.getSeat31())) {
                            seatIsUnbooked(30);
                        } else seatIsBooked(30);
                        if (!"booked".equals(bookedVehicles.getSeat32())) {
                            seatIsUnbooked(31);
                        } else seatIsBooked(31);
                        if (!"booked".equals(bookedVehicles.getSeat33())) {
                            seatIsUnbooked(32);
                        } else seatIsBooked(32);
                        if (!"booked".equals(bookedVehicles.getSeat34())) {
                            seatIsUnbooked(33);
                        } else seatIsBooked(33);
                        if (!"booked".equals(bookedVehicles.getSeat35())) {
                            seatIsUnbooked(34);
                        } else seatIsBooked(34);
                        if (!"booked".equals(bookedVehicles.getSeat36())) {
                            seatIsUnbooked(35);
                        } else seatIsBooked(35);
                        if (!"booked".equals(bookedVehicles.getSeat37())) {
                            seatIsUnbooked(36);
                        } else seatIsBooked(36);
                        if (!"booked".equals(bookedVehicles.getSeat38())) {
                            seatIsUnbooked(37);
                        } else seatIsBooked(37);
                        if (!"booked".equals(bookedVehicles.getSeat39())) {
                            seatIsUnbooked(38);
                        } else seatIsBooked(38);
                        if (!"booked".equals(bookedVehicles.getSeat40())) {
                            seatIsUnbooked(39);
                        } else seatIsBooked(39);
                        if (!"booked".equals(bookedVehicles.getSeat41())) {
                            seatIsUnbooked(40);
                        } else seatIsBooked(40);
                        if (!"booked".equals(bookedVehicles.getSeat42())) {
                            seatIsUnbooked(41);
                        } else seatIsBooked(41);
                        if (!"booked".equals(bookedVehicles.getSeat43())) {
                            seatIsUnbooked(42);
                        } else seatIsBooked(42);
                        if (!"booked".equals(bookedVehicles.getSeat44())) {
                            seatIsUnbooked(43);
                        } else seatIsBooked(43);
                        if (!"booked".equals(bookedVehicles.getSeat45())) {
                            seatIsUnbooked(44);
                        } else seatIsBooked(44);
                        if (!"booked".equals(bookedVehicles.getSeat46())) {
                            seatIsUnbooked(45);
                        } else seatIsBooked(45);
                        if (!"booked".equals(bookedVehicles.getSeat47())) {
                            seatIsUnbooked(46);
                        } else seatIsBooked(46);
                        if (!"booked".equals(bookedVehicles.getSeat48())) {
                            seatIsUnbooked(47);
                        } else seatIsBooked(47);
                        if (!"booked".equals(bookedVehicles.getSeat49())) {
                            seatIsUnbooked(48);
                        } else seatIsBooked(48);
                        if (!"booked".equals(bookedVehicles.getSeat50())) {
                            seatIsUnbooked(49);
                        } else seatIsBooked(49);
                        if (!"booked".equals(bookedVehicles.getSeat51())) {
                            seatIsUnbooked(50);
                        } else seatIsBooked(50);
                        if (!"booked".equals(bookedVehicles.getSeat52())) {
                            seatIsUnbooked(51);
                        } else seatIsBooked(51);
                        if (!"booked".equals(bookedVehicles.getSeat53())) {
                            seatIsUnbooked(52);
                        } else seatIsBooked(52);
                        if (!"booked".equals(bookedVehicles.getSeat54())) {
                            seatIsUnbooked(53);
                        } else seatIsBooked(53);
                        if (!"booked".equals(bookedVehicles.getSeat55())) {
                            seatIsUnbooked(54);
                        } else seatIsBooked(54);
                        if (!"booked".equals(bookedVehicles.getSeat56())) {
                            seatIsUnbooked(55);
                        } else seatIsBooked(55);
                        if (!"booked".equals(bookedVehicles.getSeat57())) {
                            seatIsUnbooked(56);
                        } else seatIsBooked(56);
                        if (!"booked".equals(bookedVehicles.getSeat58())) {
                            seatIsUnbooked(57);
                        } else seatIsBooked(57);
                        if (!"booked".equals(bookedVehicles.getSeat59())) {
                            seatIsUnbooked(58);
                        } else seatIsBooked(58);
                        if (!"booked".equals(bookedVehicles.getSeat60())) {
                            seatIsUnbooked(59);
                        } else seatIsBooked(59);
                        if (!"booked".equals(bookedVehicles.getSeat61())) {
                            seatIsUnbooked(60);
                        } else seatIsBooked(60);
                        if (!"booked".equals(bookedVehicles.getSeat62())) {
                            seatIsUnbooked(61);
                        } else seatIsBooked(61);
                        if (!"booked".equals(bookedVehicles.getSeat63())) {
                            seatIsUnbooked(62);
                        } else seatIsBooked(62);
                        if (!"booked".equals(bookedVehicles.getSeat64())) {
                            seatIsUnbooked(63);
                        } else seatIsBooked(63);
                        if (!"booked".equals(bookedVehicles.getSeat65())) {
                            seatIsUnbooked(64);
                        } else seatIsBooked(64);
                        if (!"booked".equals(bookedVehicles.getSeat66())) {
                            seatIsUnbooked(65);
                        } else seatIsBooked(65);
                        if (!"booked".equals(bookedVehicles.getSeat67())) {
                            seatIsUnbooked(66);
                        } else seatIsBooked(66);
                        if (!"booked".equals(bookedVehicles.getSeat68())) {
                            seatIsUnbooked(67);
                        } else seatIsBooked(67);
                        if (!"booked".equals(bookedVehicles.getSeat69())) {
                            seatIsUnbooked(68);
                        } else seatIsBooked(68);
                        if (!"booked".equals(bookedVehicles.getSeat70())) {
                            seatIsUnbooked(69);
                        } else seatIsBooked(69);
                        if (!"booked".equals(bookedVehicles.getSeat71())) {
                            seatIsUnbooked(70);
                        } else seatIsBooked(70);
                        if (!"booked".equals(bookedVehicles.getSeat72())) {
                            seatIsUnbooked(71);
                        } else seatIsBooked(71);
                        if (!"booked".equals(bookedVehicles.getSeat73())) {
                            seatIsUnbooked(72);
                        } else seatIsBooked(72);
                        if (!"booked".equals(bookedVehicles.getSeat74())) {
                            seatIsUnbooked(73);
                        } else seatIsBooked(73);
                        if (!"booked".equals(bookedVehicles.getSeat75())) {
                            seatIsUnbooked(74);
                        } else seatIsBooked(74);
                        if (!"booked".equals(bookedVehicles.getSeat76())) {
                            seatIsUnbooked(75);
                        } else seatIsBooked(75);
                        if (!"booked".equals(bookedVehicles.getSeat77())) {
                            seatIsUnbooked(76);
                        } else seatIsBooked(76);
                        if (!"booked".equals(bookedVehicles.getSeat78())) {
                            seatIsUnbooked(77);
                        } else seatIsBooked(77);
                        if (!"booked".equals(bookedVehicles.getSeat79())) {
                            seatIsUnbooked(78);
                        } else seatIsBooked(78);
                        if (!"booked".equals(bookedVehicles.getSeat80())) {
                            seatIsUnbooked(79);
                        } else seatIsBooked(79);
                        if (!"booked".equals(bookedVehicles.getSeat81())) {
                            seatIsUnbooked(80);
                        } else seatIsBooked(80);
                        if (!"booked".equals(bookedVehicles.getSeat82())) {
                            seatIsUnbooked(81);
                        } else seatIsBooked(81);
                        if (!"booked".equals(bookedVehicles.getSeat83())) {
                            seatIsUnbooked(82);
                        } else seatIsBooked(82);
                        if (!"booked".equals(bookedVehicles.getSeat84())) {
                            seatIsUnbooked(83);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat85())) {
                            seatIsUnbooked(84);
                        } else seatIsBooked(84);
                        if (!"booked".equals(bookedVehicles.getSeat86())) {
                            seatIsUnbooked(85);
                        } else seatIsBooked(85);
                        if (!"booked".equals(bookedVehicles.getSeat87())) {
                            seatIsUnbooked(86);
                        } else seatIsBooked(86);
                        if (!"booked".equals(bookedVehicles.getSeat88())) {
                            seatIsUnbooked(87);
                        } else seatIsBooked(87);
                        if (!"booked".equals(bookedVehicles.getSeat89())) {
                            seatIsUnbooked(88);
                        } else seatIsBooked(88);
                        if (!"booked".equals(bookedVehicles.getSeat90())) {
                            seatIsUnbooked(89);
                        } else seatIsBooked(89);

                    } catch (IndexOutOfBoundsException ex) {
                        ex.getMessage();
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                bookedVehicles = dataSnapshot.getValue(BookedVehicles.class);
                if (bookedVehicles != null) {
                    bookedVehicles.setKey(dataSnapshot.getKey());
                    try {
                        if (!"booked".equals(bookedVehicles.getSeat1())) {
                            seatIsUnbooked(0);
                        } else seatIsBooked(0);
                        if (!"booked".equals(bookedVehicles.getSeat2())) {
                            seatIsUnbooked(1);
                        } else seatIsBooked(1);
                        if (!"booked".equals(bookedVehicles.getSeat3())) {
                            seatIsUnbooked(2);
                        } else seatIsBooked(2);
                        if (!"booked".equals(bookedVehicles.getSeat4())) {
                            seatIsUnbooked(3);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat5())) {
                            seatIsUnbooked(4);
                        } else seatIsBooked(4);
                        if (!"booked".equals(bookedVehicles.getSeat6())) {
                            seatIsUnbooked(5);
                        } else seatIsBooked(5);
                        if (!"booked".equals(bookedVehicles.getSeat7())) {
                            seatIsUnbooked(6);
                        } else seatIsBooked(6);
                        if (!"booked".equals(bookedVehicles.getSeat8())) {
                            seatIsUnbooked(7);
                        } else seatIsBooked(7);
                        if (!"booked".equals(bookedVehicles.getSeat9())) {
                            seatIsUnbooked(8);
                        } else seatIsBooked(8);
                        if (!"booked".equals(bookedVehicles.getSeat10())) {
                            seatIsUnbooked(9);
                        } else seatIsBooked(9);
                        if (!"booked".equals(bookedVehicles.getSeat11())) {
                            seatIsUnbooked(10);
                        } else seatIsBooked(10);
                        if (!"booked".equals(bookedVehicles.getSeat12())) {
                            seatIsUnbooked(11);
                        } else seatIsBooked(11);
                        if (!"booked".equals(bookedVehicles.getSeat13())) {
                            seatIsUnbooked(12);
                        } else seatIsBooked(12);
                        if (!"booked".equals(bookedVehicles.getSeat14())) {
                            seatIsUnbooked(13);
                        } else seatIsBooked(13);
                        if (!"booked".equals(bookedVehicles.getSeat15())) {
                            seatIsUnbooked(14);
                        } else seatIsBooked(14);
                        if (!"booked".equals(bookedVehicles.getSeat16())) {
                            seatIsUnbooked(15);
                        } else seatIsBooked(15);
                        if (!"booked".equals(bookedVehicles.getSeat17())) {
                            seatIsUnbooked(16);
                        } else seatIsBooked(16);
                        if (!"booked".equals(bookedVehicles.getSeat18())) {
                            seatIsUnbooked(17);
                        } else seatIsBooked(17);
                        if (!"booked".equals(bookedVehicles.getSeat19())) {
                            seatIsUnbooked(18);
                        } else seatIsBooked(18);
                        if (!"booked".equals(bookedVehicles.getSeat20())) {
                            seatIsUnbooked(19);
                        } else seatIsBooked(19);
                        if (!"booked".equals(bookedVehicles.getSeat21())) {
                            seatIsUnbooked(20);
                        } else seatIsBooked(20);
                        if (!"booked".equals(bookedVehicles.getSeat22())) {
                            seatIsUnbooked(21);
                        } else seatIsBooked(21);
                        if (!"booked".equals(bookedVehicles.getSeat23())) {
                            seatIsUnbooked(22);
                        } else seatIsBooked(22);
                        if (!"booked".equals(bookedVehicles.getSeat24())) {
                            seatIsUnbooked(23);
                        } else seatIsBooked(23);
                        if (!"booked".equals(bookedVehicles.getSeat25())) {
                            seatIsUnbooked(24);
                        } else seatIsBooked(24);
                        if (!"booked".equals(bookedVehicles.getSeat26())) {
                            seatIsUnbooked(25);
                        } else seatIsBooked(25);
                        if (!"booked".equals(bookedVehicles.getSeat27())) {
                            seatIsUnbooked(26);
                        } else seatIsBooked(26);
                        if (!"booked".equals(bookedVehicles.getSeat28())) {
                            seatIsUnbooked(27);
                        } else seatIsBooked(27);
                        if (!"booked".equals(bookedVehicles.getSeat29())) {
                            seatIsUnbooked(28);
                        } else seatIsBooked(28);
                        if (!"booked".equals(bookedVehicles.getSeat30())) {
                            seatIsUnbooked(29);
                        } else seatIsBooked(29);
                        if (!"booked".equals(bookedVehicles.getSeat31())) {
                            seatIsUnbooked(30);
                        } else seatIsBooked(30);
                        if (!"booked".equals(bookedVehicles.getSeat32())) {
                            seatIsUnbooked(31);
                        } else seatIsBooked(31);
                        if (!"booked".equals(bookedVehicles.getSeat33())) {
                            seatIsUnbooked(32);
                        } else seatIsBooked(32);
                        if (!"booked".equals(bookedVehicles.getSeat34())) {
                            seatIsUnbooked(33);
                        } else seatIsBooked(33);
                        if (!"booked".equals(bookedVehicles.getSeat35())) {
                            seatIsUnbooked(34);
                        } else seatIsBooked(34);
                        if (!"booked".equals(bookedVehicles.getSeat36())) {
                            seatIsUnbooked(35);
                        } else seatIsBooked(35);
                        if (!"booked".equals(bookedVehicles.getSeat37())) {
                            seatIsUnbooked(36);
                        } else seatIsBooked(36);
                        if (!"booked".equals(bookedVehicles.getSeat38())) {
                            seatIsUnbooked(37);
                        } else seatIsBooked(37);
                        if (!"booked".equals(bookedVehicles.getSeat39())) {
                            seatIsUnbooked(38);
                        } else seatIsBooked(38);
                        if (!"booked".equals(bookedVehicles.getSeat40())) {
                            seatIsUnbooked(39);
                        } else seatIsBooked(39);
                        if (!"booked".equals(bookedVehicles.getSeat41())) {
                            seatIsUnbooked(40);
                        } else seatIsBooked(40);
                        if (!"booked".equals(bookedVehicles.getSeat42())) {
                            seatIsUnbooked(41);
                        } else seatIsBooked(41);
                        if (!"booked".equals(bookedVehicles.getSeat43())) {
                            seatIsUnbooked(42);
                        } else seatIsBooked(42);
                        if (!"booked".equals(bookedVehicles.getSeat44())) {
                            seatIsUnbooked(43);
                        } else seatIsBooked(43);
                        if (!"booked".equals(bookedVehicles.getSeat45())) {
                            seatIsUnbooked(44);
                        } else seatIsBooked(44);
                        if (!"booked".equals(bookedVehicles.getSeat46())) {
                            seatIsUnbooked(45);
                        } else seatIsBooked(45);
                        if (!"booked".equals(bookedVehicles.getSeat47())) {
                            seatIsUnbooked(46);
                        } else seatIsBooked(46);
                        if (!"booked".equals(bookedVehicles.getSeat48())) {
                            seatIsUnbooked(47);
                        } else seatIsBooked(47);
                        if (!"booked".equals(bookedVehicles.getSeat49())) {
                            seatIsUnbooked(48);
                        } else seatIsBooked(48);
                        if (!"booked".equals(bookedVehicles.getSeat50())) {
                            seatIsUnbooked(49);
                        } else seatIsBooked(49);
                        if (!"booked".equals(bookedVehicles.getSeat51())) {
                            seatIsUnbooked(50);
                        } else seatIsBooked(50);
                        if (!"booked".equals(bookedVehicles.getSeat52())) {
                            seatIsUnbooked(51);
                        } else seatIsBooked(51);
                        if (!"booked".equals(bookedVehicles.getSeat53())) {
                            seatIsUnbooked(52);
                        } else seatIsBooked(52);
                        if (!"booked".equals(bookedVehicles.getSeat54())) {
                            seatIsUnbooked(53);
                        } else seatIsBooked(53);
                        if (!"booked".equals(bookedVehicles.getSeat55())) {
                            seatIsUnbooked(54);
                        } else seatIsBooked(54);
                        if (!"booked".equals(bookedVehicles.getSeat56())) {
                            seatIsUnbooked(55);
                        } else seatIsBooked(55);
                        if (!"booked".equals(bookedVehicles.getSeat57())) {
                            seatIsUnbooked(56);
                        } else seatIsBooked(56);
                        if (!"booked".equals(bookedVehicles.getSeat58())) {
                            seatIsUnbooked(57);
                        } else seatIsBooked(57);
                        if (!"booked".equals(bookedVehicles.getSeat59())) {
                            seatIsUnbooked(58);
                        } else seatIsBooked(58);
                        if (!"booked".equals(bookedVehicles.getSeat60())) {
                            seatIsUnbooked(59);
                        } else seatIsBooked(59);
                        if (!"booked".equals(bookedVehicles.getSeat61())) {
                            seatIsUnbooked(60);
                        } else seatIsBooked(60);
                        if (!"booked".equals(bookedVehicles.getSeat62())) {
                            seatIsUnbooked(61);
                        } else seatIsBooked(61);
                        if (!"booked".equals(bookedVehicles.getSeat63())) {
                            seatIsUnbooked(62);
                        } else seatIsBooked(62);
                        if (!"booked".equals(bookedVehicles.getSeat64())) {
                            seatIsUnbooked(63);
                        } else seatIsBooked(63);
                        if (!"booked".equals(bookedVehicles.getSeat65())) {
                            seatIsUnbooked(64);
                        } else seatIsBooked(64);
                        if (!"booked".equals(bookedVehicles.getSeat66())) {
                            seatIsUnbooked(65);
                        } else seatIsBooked(65);
                        if (!"booked".equals(bookedVehicles.getSeat67())) {
                            seatIsUnbooked(66);
                        } else seatIsBooked(66);
                        if (!"booked".equals(bookedVehicles.getSeat68())) {
                            seatIsUnbooked(67);
                        } else seatIsBooked(67);
                        if (!"booked".equals(bookedVehicles.getSeat69())) {
                            seatIsUnbooked(68);
                        } else seatIsBooked(68);
                        if (!"booked".equals(bookedVehicles.getSeat70())) {
                            seatIsUnbooked(69);
                        } else seatIsBooked(69);
                        if (!"booked".equals(bookedVehicles.getSeat71())) {
                            seatIsUnbooked(70);
                        } else seatIsBooked(70);
                        if (!"booked".equals(bookedVehicles.getSeat72())) {
                            seatIsUnbooked(71);
                        } else seatIsBooked(71);
                        if (!"booked".equals(bookedVehicles.getSeat73())) {
                            seatIsUnbooked(72);
                        } else seatIsBooked(72);
                        if (!"booked".equals(bookedVehicles.getSeat74())) {
                            seatIsUnbooked(73);
                        } else seatIsBooked(73);
                        if (!"booked".equals(bookedVehicles.getSeat75())) {
                            seatIsUnbooked(74);
                        } else seatIsBooked(74);
                        if (!"booked".equals(bookedVehicles.getSeat76())) {
                            seatIsUnbooked(75);
                        } else seatIsBooked(75);
                        if (!"booked".equals(bookedVehicles.getSeat77())) {
                            seatIsUnbooked(76);
                        } else seatIsBooked(76);
                        if (!"booked".equals(bookedVehicles.getSeat78())) {
                            seatIsUnbooked(77);
                        } else seatIsBooked(77);
                        if (!"booked".equals(bookedVehicles.getSeat79())) {
                            seatIsUnbooked(78);
                        } else seatIsBooked(78);
                        if (!"booked".equals(bookedVehicles.getSeat80())) {
                            seatIsUnbooked(79);
                        } else seatIsBooked(79);
                        if (!"booked".equals(bookedVehicles.getSeat81())) {
                            seatIsUnbooked(80);
                        } else seatIsBooked(80);
                        if (!"booked".equals(bookedVehicles.getSeat82())) {
                            seatIsUnbooked(81);
                        } else seatIsBooked(81);
                        if (!"booked".equals(bookedVehicles.getSeat83())) {
                            seatIsUnbooked(82);
                        } else seatIsBooked(82);
                        if (!"booked".equals(bookedVehicles.getSeat84())) {
                            seatIsUnbooked(83);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat85())) {
                            seatIsUnbooked(84);
                        } else seatIsBooked(84);
                        if (!"booked".equals(bookedVehicles.getSeat86())) {
                            seatIsUnbooked(85);
                        } else seatIsBooked(85);
                        if (!"booked".equals(bookedVehicles.getSeat87())) {
                            seatIsUnbooked(86);
                        } else seatIsBooked(86);
                        if (!"booked".equals(bookedVehicles.getSeat88())) {
                            seatIsUnbooked(87);
                        } else seatIsBooked(87);
                        if (!"booked".equals(bookedVehicles.getSeat89())) {
                            seatIsUnbooked(88);
                        } else seatIsBooked(88);
                        if (!"booked".equals(bookedVehicles.getSeat90())) {
                            seatIsUnbooked(89);
                        } else seatIsBooked(89);

                    } catch (IndexOutOfBoundsException ex) {
                        ex.getMessage();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                bookedVehicles = dataSnapshot.getValue(BookedVehicles.class);
                if (bookedVehicles != null) {
                    bookedVehicles.setKey(dataSnapshot.getKey());
                    try {
                        if (!"booked".equals(bookedVehicles.getSeat1())) {
                            seatIsUnbooked(0);
                        } else seatIsBooked(0);
                        if (!"booked".equals(bookedVehicles.getSeat2())) {
                            seatIsUnbooked(1);
                        } else seatIsBooked(1);
                        if (!"booked".equals(bookedVehicles.getSeat3())) {
                            seatIsUnbooked(2);
                        } else seatIsBooked(2);
                        if (!"booked".equals(bookedVehicles.getSeat4())) {
                            seatIsUnbooked(3);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat5())) {
                            seatIsUnbooked(4);
                        } else seatIsBooked(4);
                        if (!"booked".equals(bookedVehicles.getSeat6())) {
                            seatIsUnbooked(5);
                        } else seatIsBooked(5);
                        if (!"booked".equals(bookedVehicles.getSeat7())) {
                            seatIsUnbooked(6);
                        } else seatIsBooked(6);
                        if (!"booked".equals(bookedVehicles.getSeat8())) {
                            seatIsUnbooked(7);
                        } else seatIsBooked(7);
                        if (!"booked".equals(bookedVehicles.getSeat9())) {
                            seatIsUnbooked(8);
                        } else seatIsBooked(8);
                        if (!"booked".equals(bookedVehicles.getSeat10())) {
                            seatIsUnbooked(9);
                        } else seatIsBooked(9);
                        if (!"booked".equals(bookedVehicles.getSeat11())) {
                            seatIsUnbooked(10);
                        } else seatIsBooked(10);
                        if (!"booked".equals(bookedVehicles.getSeat12())) {
                            seatIsUnbooked(11);
                        } else seatIsBooked(11);
                        if (!"booked".equals(bookedVehicles.getSeat13())) {
                            seatIsUnbooked(12);
                        } else seatIsBooked(12);
                        if (!"booked".equals(bookedVehicles.getSeat14())) {
                            seatIsUnbooked(13);
                        } else seatIsBooked(13);
                        if (!"booked".equals(bookedVehicles.getSeat15())) {
                            seatIsUnbooked(14);
                        } else seatIsBooked(14);
                        if (!"booked".equals(bookedVehicles.getSeat16())) {
                            seatIsUnbooked(15);
                        } else seatIsBooked(15);
                        if (!"booked".equals(bookedVehicles.getSeat17())) {
                            seatIsUnbooked(16);
                        } else seatIsBooked(16);
                        if (!"booked".equals(bookedVehicles.getSeat18())) {
                            seatIsUnbooked(17);
                        } else seatIsBooked(17);
                        if (!"booked".equals(bookedVehicles.getSeat19())) {
                            seatIsUnbooked(18);
                        } else seatIsBooked(18);
                        if (!"booked".equals(bookedVehicles.getSeat20())) {
                            seatIsUnbooked(19);
                        } else seatIsBooked(19);
                        if (!"booked".equals(bookedVehicles.getSeat21())) {
                            seatIsUnbooked(20);
                        } else seatIsBooked(20);
                        if (!"booked".equals(bookedVehicles.getSeat22())) {
                            seatIsUnbooked(21);
                        } else seatIsBooked(21);
                        if (!"booked".equals(bookedVehicles.getSeat23())) {
                            seatIsUnbooked(22);
                        } else seatIsBooked(22);
                        if (!"booked".equals(bookedVehicles.getSeat24())) {
                            seatIsUnbooked(23);
                        } else seatIsBooked(23);
                        if (!"booked".equals(bookedVehicles.getSeat25())) {
                            seatIsUnbooked(24);
                        } else seatIsBooked(24);
                        if (!"booked".equals(bookedVehicles.getSeat26())) {
                            seatIsUnbooked(25);
                        } else seatIsBooked(25);
                        if (!"booked".equals(bookedVehicles.getSeat27())) {
                            seatIsUnbooked(26);
                        } else seatIsBooked(26);
                        if (!"booked".equals(bookedVehicles.getSeat28())) {
                            seatIsUnbooked(27);
                        } else seatIsBooked(27);
                        if (!"booked".equals(bookedVehicles.getSeat29())) {
                            seatIsUnbooked(28);
                        } else seatIsBooked(28);
                        if (!"booked".equals(bookedVehicles.getSeat30())) {
                            seatIsUnbooked(29);
                        } else seatIsBooked(29);
                        if (!"booked".equals(bookedVehicles.getSeat31())) {
                            seatIsUnbooked(30);
                        } else seatIsBooked(30);
                        if (!"booked".equals(bookedVehicles.getSeat32())) {
                            seatIsUnbooked(31);
                        } else seatIsBooked(31);
                        if (!"booked".equals(bookedVehicles.getSeat33())) {
                            seatIsUnbooked(32);
                        } else seatIsBooked(32);
                        if (!"booked".equals(bookedVehicles.getSeat34())) {
                            seatIsUnbooked(33);
                        } else seatIsBooked(33);
                        if (!"booked".equals(bookedVehicles.getSeat35())) {
                            seatIsUnbooked(34);
                        } else seatIsBooked(34);
                        if (!"booked".equals(bookedVehicles.getSeat36())) {
                            seatIsUnbooked(35);
                        } else seatIsBooked(35);
                        if (!"booked".equals(bookedVehicles.getSeat37())) {
                            seatIsUnbooked(36);
                        } else seatIsBooked(36);
                        if (!"booked".equals(bookedVehicles.getSeat38())) {
                            seatIsUnbooked(37);
                        } else seatIsBooked(37);
                        if (!"booked".equals(bookedVehicles.getSeat39())) {
                            seatIsUnbooked(38);
                        } else seatIsBooked(38);
                        if (!"booked".equals(bookedVehicles.getSeat40())) {
                            seatIsUnbooked(39);
                        } else seatIsBooked(39);
                        if (!"booked".equals(bookedVehicles.getSeat41())) {
                            seatIsUnbooked(40);
                        } else seatIsBooked(40);
                        if (!"booked".equals(bookedVehicles.getSeat42())) {
                            seatIsUnbooked(41);
                        } else seatIsBooked(41);
                        if (!"booked".equals(bookedVehicles.getSeat43())) {
                            seatIsUnbooked(42);
                        } else seatIsBooked(42);
                        if (!"booked".equals(bookedVehicles.getSeat44())) {
                            seatIsUnbooked(43);
                        } else seatIsBooked(43);
                        if (!"booked".equals(bookedVehicles.getSeat45())) {
                            seatIsUnbooked(44);
                        } else seatIsBooked(44);
                        if (!"booked".equals(bookedVehicles.getSeat46())) {
                            seatIsUnbooked(45);
                        } else seatIsBooked(45);
                        if (!"booked".equals(bookedVehicles.getSeat47())) {
                            seatIsUnbooked(46);
                        } else seatIsBooked(46);
                        if (!"booked".equals(bookedVehicles.getSeat48())) {
                            seatIsUnbooked(47);
                        } else seatIsBooked(47);
                        if (!"booked".equals(bookedVehicles.getSeat49())) {
                            seatIsUnbooked(48);
                        } else seatIsBooked(48);
                        if (!"booked".equals(bookedVehicles.getSeat50())) {
                            seatIsUnbooked(49);
                        } else seatIsBooked(49);
                        if (!"booked".equals(bookedVehicles.getSeat51())) {
                            seatIsUnbooked(50);
                        } else seatIsBooked(50);
                        if (!"booked".equals(bookedVehicles.getSeat52())) {
                            seatIsUnbooked(51);
                        } else seatIsBooked(51);
                        if (!"booked".equals(bookedVehicles.getSeat53())) {
                            seatIsUnbooked(52);
                        } else seatIsBooked(52);
                        if (!"booked".equals(bookedVehicles.getSeat54())) {
                            seatIsUnbooked(53);
                        } else seatIsBooked(53);
                        if (!"booked".equals(bookedVehicles.getSeat55())) {
                            seatIsUnbooked(54);
                        } else seatIsBooked(54);
                        if (!"booked".equals(bookedVehicles.getSeat56())) {
                            seatIsUnbooked(55);
                        } else seatIsBooked(55);
                        if (!"booked".equals(bookedVehicles.getSeat57())) {
                            seatIsUnbooked(56);
                        } else seatIsBooked(56);
                        if (!"booked".equals(bookedVehicles.getSeat58())) {
                            seatIsUnbooked(57);
                        } else seatIsBooked(57);
                        if (!"booked".equals(bookedVehicles.getSeat59())) {
                            seatIsUnbooked(58);
                        } else seatIsBooked(58);
                        if (!"booked".equals(bookedVehicles.getSeat60())) {
                            seatIsUnbooked(59);
                        } else seatIsBooked(59);
                        if (!"booked".equals(bookedVehicles.getSeat61())) {
                            seatIsUnbooked(60);
                        } else seatIsBooked(60);
                        if (!"booked".equals(bookedVehicles.getSeat62())) {
                            seatIsUnbooked(61);
                        } else seatIsBooked(61);
                        if (!"booked".equals(bookedVehicles.getSeat63())) {
                            seatIsUnbooked(62);
                        } else seatIsBooked(62);
                        if (!"booked".equals(bookedVehicles.getSeat64())) {
                            seatIsUnbooked(63);
                        } else seatIsBooked(63);
                        if (!"booked".equals(bookedVehicles.getSeat65())) {
                            seatIsUnbooked(64);
                        } else seatIsBooked(64);
                        if (!"booked".equals(bookedVehicles.getSeat66())) {
                            seatIsUnbooked(65);
                        } else seatIsBooked(65);
                        if (!"booked".equals(bookedVehicles.getSeat67())) {
                            seatIsUnbooked(66);
                        } else seatIsBooked(66);
                        if (!"booked".equals(bookedVehicles.getSeat68())) {
                            seatIsUnbooked(67);
                        } else seatIsBooked(67);
                        if (!"booked".equals(bookedVehicles.getSeat69())) {
                            seatIsUnbooked(68);
                        } else seatIsBooked(68);
                        if (!"booked".equals(bookedVehicles.getSeat70())) {
                            seatIsUnbooked(69);
                        } else seatIsBooked(69);
                        if (!"booked".equals(bookedVehicles.getSeat71())) {
                            seatIsUnbooked(70);
                        } else seatIsBooked(70);
                        if (!"booked".equals(bookedVehicles.getSeat72())) {
                            seatIsUnbooked(71);
                        } else seatIsBooked(71);
                        if (!"booked".equals(bookedVehicles.getSeat73())) {
                            seatIsUnbooked(72);
                        } else seatIsBooked(72);
                        if (!"booked".equals(bookedVehicles.getSeat74())) {
                            seatIsUnbooked(73);
                        } else seatIsBooked(73);
                        if (!"booked".equals(bookedVehicles.getSeat75())) {
                            seatIsUnbooked(74);
                        } else seatIsBooked(74);
                        if (!"booked".equals(bookedVehicles.getSeat76())) {
                            seatIsUnbooked(75);
                        } else seatIsBooked(75);
                        if (!"booked".equals(bookedVehicles.getSeat77())) {
                            seatIsUnbooked(76);
                        } else seatIsBooked(76);
                        if (!"booked".equals(bookedVehicles.getSeat78())) {
                            seatIsUnbooked(77);
                        } else seatIsBooked(77);
                        if (!"booked".equals(bookedVehicles.getSeat79())) {
                            seatIsUnbooked(78);
                        } else seatIsBooked(78);
                        if (!"booked".equals(bookedVehicles.getSeat80())) {
                            seatIsUnbooked(79);
                        } else seatIsBooked(79);
                        if (!"booked".equals(bookedVehicles.getSeat81())) {
                            seatIsUnbooked(80);
                        } else seatIsBooked(80);
                        if (!"booked".equals(bookedVehicles.getSeat82())) {
                            seatIsUnbooked(81);
                        } else seatIsBooked(81);
                        if (!"booked".equals(bookedVehicles.getSeat83())) {
                            seatIsUnbooked(82);
                        } else seatIsBooked(82);
                        if (!"booked".equals(bookedVehicles.getSeat84())) {
                            seatIsUnbooked(83);
                        } else seatIsBooked(3);
                        if (!"booked".equals(bookedVehicles.getSeat85())) {
                            seatIsUnbooked(84);
                        } else seatIsBooked(84);
                        if (!"booked".equals(bookedVehicles.getSeat86())) {
                            seatIsUnbooked(85);
                        } else seatIsBooked(85);
                        if (!"booked".equals(bookedVehicles.getSeat87())) {
                            seatIsUnbooked(86);
                        } else seatIsBooked(86);
                        if (!"booked".equals(bookedVehicles.getSeat88())) {
                            seatIsUnbooked(87);
                        } else seatIsBooked(87);
                        if (!"booked".equals(bookedVehicles.getSeat89())) {
                            seatIsUnbooked(88);
                        } else seatIsBooked(88);
                        if (!"booked".equals(bookedVehicles.getSeat90())) {
                            seatIsUnbooked(89);
                        } else seatIsBooked(89);

                    } catch (IndexOutOfBoundsException ex) {
                        ex.getMessage();
                    }
                }

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
        try {
            totalSeat(Integer.parseInt(recordByNumberOfSeats));
        } catch (IndexOutOfBoundsException exception) {
            exception.getMessage();
        }
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
                            token = response.body().string();
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
        new LovelyTextInputDialog(this, R.style.AppTheme_NoActionBar)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Lipa Na MPESA Online")
                .setMessage("You are about to pay for your Bus Ticket using MPESA. Ensure you enter your number in the correct format (254XXX)")
                .setIcon(R.drawable.ic_mpesa)
                .setInputFilter("Please Enter Number", new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.matches("\\w+");
                    }
                })
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        phone_number = text;
                        try {
                            performSTKPush(phone_number);
                        } catch (Exception e) {
                            Toast.makeText(DirectBook.this, "Error fetchng token", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    public void mpesaTransactionCode() {
        new LovelyTextInputDialog(this, R.style.AppTheme_NoActionBar)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("MPESA Transaction Code")
                .setMessage("Please Enter Transaction Code Received. To find this code, Please find your ticket payment message from MPESA & copy/paste the Transaction code (LLC8NL8FOC)")
                .setIcon(R.drawable.ic_smartphone)
                .setInputFilter("Please correct transaction code", new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.matches("\\w+");
                    }
                })
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        try {
                            // Access a Firebase Real Database instance from your Activity
                            db = FirebaseDatabase.getInstance();
                            db.getReference().child("bookedVehicles").child(recordByKeyValue).child(seatNumber).setValue("booked");
                            postTicketReceipt();
                            congratulations();
                        } catch (Exception e) {
                            Toast.makeText(DirectBook.this, "Error fetchng token", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
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
                        mpesaTransactionCode();
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
        getPhoneNumber();
    }

    // handles the seat booked
    public void seatIsUnbooked(int pos) {
        gridArray.remove(pos);
        int i = pos + 1;
        gridArray.add(pos, new Item(seatIcon, "seat" + i));
        seatSelectionAdapter.notifyDataSetChanged();
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
            public void onResponse(@NonNull Call<QueryRequest> call, Response<QueryRequest> response) {
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
                db.getReference().child("tickets").child(uid).push().setValue(ticketDetails);
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

    // print the congratulations message for paying and getting a ticket
    public void congratulations() {
        new LovelyInfoDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_confetti)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
//                .setNotShowAgainOptionEnabled(0)
//                .setNotShowAgainOptionChecked(true)
                .setTitle("Congratulations")
                .setMessage("Thank you " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " for paying. Your Ticket is now available under My Tickets. Happy Travelling")
                .show();
    }
}
