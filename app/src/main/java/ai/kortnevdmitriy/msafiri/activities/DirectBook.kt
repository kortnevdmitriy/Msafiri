package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.adapters.SeatSelectionAdapter
import ai.kortnevdmitriy.msafiri.models.BookedVehicles
import ai.kortnevdmitriy.msafiri.models.TicketDetails
import ai.kortnevdmitriy.msafiri.models.VehicleDetails
import ai.kortnevdmitriy.msafiri.mpesa.api.ApiUtils
import ai.kortnevdmitriy.msafiri.mpesa.api.QueryRequest
import ai.kortnevdmitriy.msafiri.mpesa.api.STKPush
import ai.kortnevdmitriy.msafiri.mpesa.api.StoreKey
import ai.kortnevdmitriy.msafiri.mpesa.api.services.STKPushService
import ai.kortnevdmitriy.msafiri.mpesa.app.Config
import ai.kortnevdmitriy.msafiri.mpesa.utils.NotificationUtils
import ai.kortnevdmitriy.msafiri.utilities.Item
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonParser
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class DirectBook : AppCompatActivity() {
	lateinit var seatIcon: Bitmap
	lateinit var seatBooked: Bitmap
	lateinit var stkPushService: STKPushService
	lateinit var gridView: GridView
	lateinit var seatSelectionAdapter: SeatSelectionAdapter
	private var mRegistrationBroadcastReceiver: BroadcastReceiver? = null
	private var gridArray = ArrayList<Item>()
	private var token: String? = null
	private var phoneNumber = ""
	private val mpesaCode = ""
	private var regId: String? = null
	private var recordByNumberOfSeats: String? = null
	private var recordByPriceInKsh: String? = null
	private var recordByKeyValue: String? = null
	private var seatNumber: String? = null
	private var item: Item? = null
	private val listOfBookedVehicles = ArrayList<String>()
	private val paidCash: Boolean = false
	private var db: FirebaseDatabase? = null
	private var vehicleDetails: VehicleDetails? = null
	private var bookedVehicles: BookedVehicles? = null
	private var ticketDetails: TicketDetails? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_direct_book)
		recordByNumberOfSeats = intent.getStringExtra("recordByNumberOfSeats")
		recordByPriceInKsh = intent.getStringExtra("recordByPriceInKsh")
		recordByKeyValue = intent.getStringExtra("recordByKeyValue")
		
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db!!.reference.child("bookedVehicles")
		
		// Read from the database by searching through the travel route children
		myRef.orderByKey().equalTo(recordByKeyValue)
			.addChildEventListener(object : ChildEventListener {
				override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
					// This method is called once with the initial value and again
					// whenever data at this location is updated.
					bookedVehicles = dataSnapshot.getValue(BookedVehicles::class.java)
					if (bookedVehicles != null) {
						bookedVehicles!!.key = dataSnapshot.key
						try {
							if ("booked" != bookedVehicles!!.seat1) {
								seatIsUnbooked(0)
							} else
								seatIsBooked(0)
							if ("booked" != bookedVehicles!!.seat2) {
								seatIsUnbooked(1)
							} else
								seatIsBooked(1)
							if ("booked" != bookedVehicles!!.seat3) {
								seatIsUnbooked(2)
							} else
								seatIsBooked(2)
							if ("booked" != bookedVehicles!!.seat4) {
								seatIsUnbooked(3)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat5) {
								seatIsUnbooked(4)
							} else
								seatIsBooked(4)
							if ("booked" != bookedVehicles!!.seat6) {
								seatIsUnbooked(5)
							} else
								seatIsBooked(5)
							if ("booked" != bookedVehicles!!.seat7) {
								seatIsUnbooked(6)
							} else
								seatIsBooked(6)
							if ("booked" != bookedVehicles!!.seat8) {
								seatIsUnbooked(7)
							} else
								seatIsBooked(7)
							if ("booked" != bookedVehicles!!.seat9) {
								seatIsUnbooked(8)
							} else
								seatIsBooked(8)
							if ("booked" != bookedVehicles!!.seat10) {
								seatIsUnbooked(9)
							} else
								seatIsBooked(9)
							if ("booked" != bookedVehicles!!.seat11) {
								seatIsUnbooked(10)
							} else
								seatIsBooked(10)
							if ("booked" != bookedVehicles!!.seat12) {
								seatIsUnbooked(11)
							} else
								seatIsBooked(11)
							if ("booked" != bookedVehicles!!.seat13) {
								seatIsUnbooked(12)
							} else
								seatIsBooked(12)
							if ("booked" != bookedVehicles!!.seat14) {
								seatIsUnbooked(13)
							} else
								seatIsBooked(13)
							if ("booked" != bookedVehicles!!.seat15) {
								seatIsUnbooked(14)
							} else
								seatIsBooked(14)
							if ("booked" != bookedVehicles!!.seat16) {
								seatIsUnbooked(15)
							} else
								seatIsBooked(15)
							if ("booked" != bookedVehicles!!.seat17) {
								seatIsUnbooked(16)
							} else
								seatIsBooked(16)
							if ("booked" != bookedVehicles!!.seat18) {
								seatIsUnbooked(17)
							} else
								seatIsBooked(17)
							if ("booked" != bookedVehicles!!.seat19) {
								seatIsUnbooked(18)
							} else
								seatIsBooked(18)
							if ("booked" != bookedVehicles!!.seat20) {
								seatIsUnbooked(19)
							} else
								seatIsBooked(19)
							if ("booked" != bookedVehicles!!.seat21) {
								seatIsUnbooked(20)
							} else
								seatIsBooked(20)
							if ("booked" != bookedVehicles!!.seat22) {
								seatIsUnbooked(21)
							} else
								seatIsBooked(21)
							if ("booked" != bookedVehicles!!.seat23) {
								seatIsUnbooked(22)
							} else
								seatIsBooked(22)
							if ("booked" != bookedVehicles!!.seat24) {
								seatIsUnbooked(23)
							} else
								seatIsBooked(23)
							if ("booked" != bookedVehicles!!.seat25) {
								seatIsUnbooked(24)
							} else
								seatIsBooked(24)
							if ("booked" != bookedVehicles!!.seat26) {
								seatIsUnbooked(25)
							} else
								seatIsBooked(25)
							if ("booked" != bookedVehicles!!.seat27) {
								seatIsUnbooked(26)
							} else
								seatIsBooked(26)
							if ("booked" != bookedVehicles!!.seat28) {
								seatIsUnbooked(27)
							} else
								seatIsBooked(27)
							if ("booked" != bookedVehicles!!.seat29) {
								seatIsUnbooked(28)
							} else
								seatIsBooked(28)
							if ("booked" != bookedVehicles!!.seat30) {
								seatIsUnbooked(29)
							} else
								seatIsBooked(29)
							if ("booked" != bookedVehicles!!.seat31) {
								seatIsUnbooked(30)
							} else
								seatIsBooked(30)
							if ("booked" != bookedVehicles!!.seat32) {
								seatIsUnbooked(31)
							} else
								seatIsBooked(31)
							if ("booked" != bookedVehicles!!.seat33) {
								seatIsUnbooked(32)
							} else
								seatIsBooked(32)
							if ("booked" != bookedVehicles!!.seat34) {
								seatIsUnbooked(33)
							} else
								seatIsBooked(33)
							if ("booked" != bookedVehicles!!.seat35) {
								seatIsUnbooked(34)
							} else
								seatIsBooked(34)
							if ("booked" != bookedVehicles!!.seat36) {
								seatIsUnbooked(35)
							} else
								seatIsBooked(35)
							if ("booked" != bookedVehicles!!.seat37) {
								seatIsUnbooked(36)
							} else
								seatIsBooked(36)
							if ("booked" != bookedVehicles!!.seat38) {
								seatIsUnbooked(37)
							} else
								seatIsBooked(37)
							if ("booked" != bookedVehicles!!.seat39) {
								seatIsUnbooked(38)
							} else
								seatIsBooked(38)
							if ("booked" != bookedVehicles!!.seat40) {
								seatIsUnbooked(39)
							} else
								seatIsBooked(39)
							if ("booked" != bookedVehicles!!.seat41) {
								seatIsUnbooked(40)
							} else
								seatIsBooked(40)
							if ("booked" != bookedVehicles!!.seat42) {
								seatIsUnbooked(41)
							} else
								seatIsBooked(41)
							if ("booked" != bookedVehicles!!.seat43) {
								seatIsUnbooked(42)
							} else
								seatIsBooked(42)
							if ("booked" != bookedVehicles!!.seat44) {
								seatIsUnbooked(43)
							} else
								seatIsBooked(43)
							if ("booked" != bookedVehicles!!.seat45) {
								seatIsUnbooked(44)
							} else
								seatIsBooked(44)
							if ("booked" != bookedVehicles!!.seat46) {
								seatIsUnbooked(45)
							} else
								seatIsBooked(45)
							if ("booked" != bookedVehicles!!.seat47) {
								seatIsUnbooked(46)
							} else
								seatIsBooked(46)
							if ("booked" != bookedVehicles!!.seat48) {
								seatIsUnbooked(47)
							} else
								seatIsBooked(47)
							if ("booked" != bookedVehicles!!.seat49) {
								seatIsUnbooked(48)
							} else
								seatIsBooked(48)
							if ("booked" != bookedVehicles!!.seat50) {
								seatIsUnbooked(49)
							} else
								seatIsBooked(49)
							if ("booked" != bookedVehicles!!.seat51) {
								seatIsUnbooked(50)
							} else
								seatIsBooked(50)
							if ("booked" != bookedVehicles!!.seat52) {
								seatIsUnbooked(51)
							} else
								seatIsBooked(51)
							if ("booked" != bookedVehicles!!.seat53) {
								seatIsUnbooked(52)
							} else
								seatIsBooked(52)
							if ("booked" != bookedVehicles!!.seat54) {
								seatIsUnbooked(53)
							} else
								seatIsBooked(53)
							if ("booked" != bookedVehicles!!.seat55) {
								seatIsUnbooked(54)
							} else
								seatIsBooked(54)
							if ("booked" != bookedVehicles!!.seat56) {
								seatIsUnbooked(55)
							} else
								seatIsBooked(55)
							if ("booked" != bookedVehicles!!.seat57) {
								seatIsUnbooked(56)
							} else
								seatIsBooked(56)
							if ("booked" != bookedVehicles!!.seat58) {
								seatIsUnbooked(57)
							} else
								seatIsBooked(57)
							if ("booked" != bookedVehicles!!.seat59) {
								seatIsUnbooked(58)
							} else
								seatIsBooked(58)
							if ("booked" != bookedVehicles!!.seat60) {
								seatIsUnbooked(59)
							} else
								seatIsBooked(59)
							if ("booked" != bookedVehicles!!.seat61) {
								seatIsUnbooked(60)
							} else
								seatIsBooked(60)
							if ("booked" != bookedVehicles!!.seat62) {
								seatIsUnbooked(61)
							} else
								seatIsBooked(61)
							if ("booked" != bookedVehicles!!.seat63) {
								seatIsUnbooked(62)
							} else
								seatIsBooked(62)
							if ("booked" != bookedVehicles!!.seat64) {
								seatIsUnbooked(63)
							} else
								seatIsBooked(63)
							if ("booked" != bookedVehicles!!.seat65) {
								seatIsUnbooked(64)
							} else
								seatIsBooked(64)
							if ("booked" != bookedVehicles!!.seat66) {
								seatIsUnbooked(65)
							} else
								seatIsBooked(65)
							if ("booked" != bookedVehicles!!.seat67) {
								seatIsUnbooked(66)
							} else
								seatIsBooked(66)
							if ("booked" != bookedVehicles!!.seat68) {
								seatIsUnbooked(67)
							} else
								seatIsBooked(67)
							if ("booked" != bookedVehicles!!.seat69) {
								seatIsUnbooked(68)
							} else
								seatIsBooked(68)
							if ("booked" != bookedVehicles!!.seat70) {
								seatIsUnbooked(69)
							} else
								seatIsBooked(69)
							if ("booked" != bookedVehicles!!.seat71) {
								seatIsUnbooked(70)
							} else
								seatIsBooked(70)
							if ("booked" != bookedVehicles!!.seat72) {
								seatIsUnbooked(71)
							} else
								seatIsBooked(71)
							if ("booked" != bookedVehicles!!.seat73) {
								seatIsUnbooked(72)
							} else
								seatIsBooked(72)
							if ("booked" != bookedVehicles!!.seat74) {
								seatIsUnbooked(73)
							} else
								seatIsBooked(73)
							if ("booked" != bookedVehicles!!.seat75) {
								seatIsUnbooked(74)
							} else
								seatIsBooked(74)
							if ("booked" != bookedVehicles?.seat76) {
								seatIsUnbooked(75)
							} else
								seatIsBooked(75)
							if ("booked" != bookedVehicles!!.seat77) {
								seatIsUnbooked(76)
							} else
								seatIsBooked(76)
							if ("booked" != bookedVehicles!!.seat78) {
								seatIsUnbooked(77)
							} else
								seatIsBooked(77)
							if ("booked" != bookedVehicles!!.seat79) {
								seatIsUnbooked(78)
							} else
								seatIsBooked(78)
							if ("booked" != bookedVehicles!!.seat80) {
								seatIsUnbooked(79)
							} else
								seatIsBooked(79)
							if ("booked" != bookedVehicles!!.seat81) {
								seatIsUnbooked(80)
							} else
								seatIsBooked(80)
							if ("booked" != bookedVehicles!!.seat82) {
								seatIsUnbooked(81)
							} else
								seatIsBooked(81)
							if ("booked" != bookedVehicles!!.seat83) {
								seatIsUnbooked(82)
							} else
								seatIsBooked(82)
							if ("booked" != bookedVehicles!!.seat84) {
								seatIsUnbooked(83)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat85) {
								seatIsUnbooked(84)
							} else
								seatIsBooked(84)
							if ("booked" != bookedVehicles!!.seat86) {
								seatIsUnbooked(85)
							} else
								seatIsBooked(85)
							if ("booked" != bookedVehicles!!.seat87) {
								seatIsUnbooked(86)
							} else
								seatIsBooked(86)
							if ("booked" != bookedVehicles!!.seat88) {
								seatIsUnbooked(87)
							} else
								seatIsBooked(87)
							if ("booked" != bookedVehicles!!.seat89) {
								seatIsUnbooked(88)
							} else
								seatIsBooked(88)
							if ("booked" != bookedVehicles!!.seat90) {
								seatIsUnbooked(89)
							} else
								seatIsBooked(89)
							
						} catch (ex: IndexOutOfBoundsException) {
							ex.message
						}
						
					}
					
				}
				
				override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
					bookedVehicles = dataSnapshot.getValue(BookedVehicles::class.java)
					if (bookedVehicles != null) {
						bookedVehicles?.key = dataSnapshot.key
						try {
							if ("booked" != bookedVehicles!!.seat1) {
								seatIsUnbooked(0)
							} else
								seatIsBooked(0)
							if ("booked" != bookedVehicles!!.seat2) {
								seatIsUnbooked(1)
							} else
								seatIsBooked(1)
							if ("booked" != bookedVehicles!!.seat3) {
								seatIsUnbooked(2)
							} else
								seatIsBooked(2)
							if ("booked" != bookedVehicles!!.seat4) {
								seatIsUnbooked(3)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat5) {
								seatIsUnbooked(4)
							} else
								seatIsBooked(4)
							if ("booked" != bookedVehicles!!.seat6) {
								seatIsUnbooked(5)
							} else
								seatIsBooked(5)
							if ("booked" != bookedVehicles!!.seat7) {
								seatIsUnbooked(6)
							} else
								seatIsBooked(6)
							if ("booked" != bookedVehicles!!.seat8) {
								seatIsUnbooked(7)
							} else
								seatIsBooked(7)
							if ("booked" != bookedVehicles!!.seat9) {
								seatIsUnbooked(8)
							} else
								seatIsBooked(8)
							if ("booked" != bookedVehicles!!.seat10) {
								seatIsUnbooked(9)
							} else
								seatIsBooked(9)
							if ("booked" != bookedVehicles!!.seat11) {
								seatIsUnbooked(10)
							} else
								seatIsBooked(10)
							if ("booked" != bookedVehicles!!.seat12) {
								seatIsUnbooked(11)
							} else
								seatIsBooked(11)
							if ("booked" != bookedVehicles!!.seat13) {
								seatIsUnbooked(12)
							} else
								seatIsBooked(12)
							if ("booked" != bookedVehicles!!.seat14) {
								seatIsUnbooked(13)
							} else
								seatIsBooked(13)
							if ("booked" != bookedVehicles!!.seat15) {
								seatIsUnbooked(14)
							} else
								seatIsBooked(14)
							if ("booked" != bookedVehicles!!.seat16) {
								seatIsUnbooked(15)
							} else
								seatIsBooked(15)
							if ("booked" != bookedVehicles!!.seat17) {
								seatIsUnbooked(16)
							} else
								seatIsBooked(16)
							if ("booked" != bookedVehicles!!.seat18) {
								seatIsUnbooked(17)
							} else
								seatIsBooked(17)
							if ("booked" != bookedVehicles!!.seat19) {
								seatIsUnbooked(18)
							} else
								seatIsBooked(18)
							if ("booked" != bookedVehicles!!.seat20) {
								seatIsUnbooked(19)
							} else
								seatIsBooked(19)
							if ("booked" != bookedVehicles!!.seat21) {
								seatIsUnbooked(20)
							} else
								seatIsBooked(20)
							if ("booked" != bookedVehicles!!.seat22) {
								seatIsUnbooked(21)
							} else
								seatIsBooked(21)
							if ("booked" != bookedVehicles!!.seat23) {
								seatIsUnbooked(22)
							} else
								seatIsBooked(22)
							if ("booked" != bookedVehicles!!.seat24) {
								seatIsUnbooked(23)
							} else
								seatIsBooked(23)
							if ("booked" != bookedVehicles!!.seat25) {
								seatIsUnbooked(24)
							} else
								seatIsBooked(24)
							if ("booked" != bookedVehicles!!.seat26) {
								seatIsUnbooked(25)
							} else
								seatIsBooked(25)
							if ("booked" != bookedVehicles!!.seat27) {
								seatIsUnbooked(26)
							} else
								seatIsBooked(26)
							if ("booked" != bookedVehicles!!.seat28) {
								seatIsUnbooked(27)
							} else
								seatIsBooked(27)
							if ("booked" != bookedVehicles!!.seat29) {
								seatIsUnbooked(28)
							} else
								seatIsBooked(28)
							if ("booked" != bookedVehicles!!.seat30) {
								seatIsUnbooked(29)
							} else
								seatIsBooked(29)
							if ("booked" != bookedVehicles!!.seat31) {
								seatIsUnbooked(30)
							} else
								seatIsBooked(30)
							if ("booked" != bookedVehicles!!.seat32) {
								seatIsUnbooked(31)
							} else
								seatIsBooked(31)
							if ("booked" != bookedVehicles!!.seat33) {
								seatIsUnbooked(32)
							} else
								seatIsBooked(32)
							if ("booked" != bookedVehicles!!.seat34) {
								seatIsUnbooked(33)
							} else
								seatIsBooked(33)
							if ("booked" != bookedVehicles!!.seat35) {
								seatIsUnbooked(34)
							} else
								seatIsBooked(34)
							if ("booked" != bookedVehicles!!.seat36) {
								seatIsUnbooked(35)
							} else
								seatIsBooked(35)
							if ("booked" != bookedVehicles!!.seat37) {
								seatIsUnbooked(36)
							} else
								seatIsBooked(36)
							if ("booked" != bookedVehicles!!.seat38) {
								seatIsUnbooked(37)
							} else
								seatIsBooked(37)
							if ("booked" != bookedVehicles!!.seat39) {
								seatIsUnbooked(38)
							} else
								seatIsBooked(38)
							if ("booked" != bookedVehicles!!.seat40) {
								seatIsUnbooked(39)
							} else
								seatIsBooked(39)
							if ("booked" != bookedVehicles!!.seat41) {
								seatIsUnbooked(40)
							} else
								seatIsBooked(40)
							if ("booked" != bookedVehicles!!.seat42) {
								seatIsUnbooked(41)
							} else
								seatIsBooked(41)
							if ("booked" != bookedVehicles!!.seat43) {
								seatIsUnbooked(42)
							} else
								seatIsBooked(42)
							if ("booked" != bookedVehicles!!.seat44) {
								seatIsUnbooked(43)
							} else
								seatIsBooked(43)
							if ("booked" != bookedVehicles!!.seat45) {
								seatIsUnbooked(44)
							} else
								seatIsBooked(44)
							if ("booked" != bookedVehicles!!.seat46) {
								seatIsUnbooked(45)
							} else
								seatIsBooked(45)
							if ("booked" != bookedVehicles!!.seat47) {
								seatIsUnbooked(46)
							} else
								seatIsBooked(46)
							if ("booked" != bookedVehicles!!.seat48) {
								seatIsUnbooked(47)
							} else
								seatIsBooked(47)
							if ("booked" != bookedVehicles!!.seat49) {
								seatIsUnbooked(48)
							} else
								seatIsBooked(48)
							if ("booked" != bookedVehicles!!.seat50) {
								seatIsUnbooked(49)
							} else
								seatIsBooked(49)
							if ("booked" != bookedVehicles!!.seat51) {
								seatIsUnbooked(50)
							} else
								seatIsBooked(50)
							if ("booked" != bookedVehicles!!.seat52) {
								seatIsUnbooked(51)
							} else
								seatIsBooked(51)
							if ("booked" != bookedVehicles!!.seat53) {
								seatIsUnbooked(52)
							} else
								seatIsBooked(52)
							if ("booked" != bookedVehicles!!.seat54) {
								seatIsUnbooked(53)
							} else
								seatIsBooked(53)
							if ("booked" != bookedVehicles!!.seat55) {
								seatIsUnbooked(54)
							} else
								seatIsBooked(54)
							if ("booked" != bookedVehicles!!.seat56) {
								seatIsUnbooked(55)
							} else
								seatIsBooked(55)
							if ("booked" != bookedVehicles!!.seat57) {
								seatIsUnbooked(56)
							} else
								seatIsBooked(56)
							if ("booked" != bookedVehicles!!.seat58) {
								seatIsUnbooked(57)
							} else
								seatIsBooked(57)
							if ("booked" != bookedVehicles!!.seat59) {
								seatIsUnbooked(58)
							} else
								seatIsBooked(58)
							if ("booked" != bookedVehicles!!.seat60) {
								seatIsUnbooked(59)
							} else
								seatIsBooked(59)
							if ("booked" != bookedVehicles!!.seat61) {
								seatIsUnbooked(60)
							} else
								seatIsBooked(60)
							if ("booked" != bookedVehicles!!.seat62) {
								seatIsUnbooked(61)
							} else
								seatIsBooked(61)
							if ("booked" != bookedVehicles!!.seat63) {
								seatIsUnbooked(62)
							} else
								seatIsBooked(62)
							if ("booked" != bookedVehicles!!.seat64) {
								seatIsUnbooked(63)
							} else
								seatIsBooked(63)
							if ("booked" != bookedVehicles!!.seat65) {
								seatIsUnbooked(64)
							} else
								seatIsBooked(64)
							if ("booked" != bookedVehicles!!.seat66) {
								seatIsUnbooked(65)
							} else
								seatIsBooked(65)
							if ("booked" != bookedVehicles!!.seat67) {
								seatIsUnbooked(66)
							} else
								seatIsBooked(66)
							if ("booked" != bookedVehicles!!.seat68) {
								seatIsUnbooked(67)
							} else
								seatIsBooked(67)
							if ("booked" != bookedVehicles!!.seat69) {
								seatIsUnbooked(68)
							} else
								seatIsBooked(68)
							if ("booked" != bookedVehicles!!.seat70) {
								seatIsUnbooked(69)
							} else
								seatIsBooked(69)
							if ("booked" != bookedVehicles!!.seat71) {
								seatIsUnbooked(70)
							} else
								seatIsBooked(70)
							if ("booked" != bookedVehicles!!.seat72) {
								seatIsUnbooked(71)
							} else
								seatIsBooked(71)
							if ("booked" != bookedVehicles!!.seat73) {
								seatIsUnbooked(72)
							} else
								seatIsBooked(72)
							if ("booked" != bookedVehicles!!.seat74) {
								seatIsUnbooked(73)
							} else
								seatIsBooked(73)
							if ("booked" != bookedVehicles!!.seat75) {
								seatIsUnbooked(74)
							} else
								seatIsBooked(74)
							if ("booked" != bookedVehicles!!.seat76) {
								seatIsUnbooked(75)
							} else
								seatIsBooked(75)
							if ("booked" != bookedVehicles!!.seat77) {
								seatIsUnbooked(76)
							} else
								seatIsBooked(76)
							if ("booked" != bookedVehicles!!.seat78) {
								seatIsUnbooked(77)
							} else
								seatIsBooked(77)
							if ("booked" != bookedVehicles!!.seat79) {
								seatIsUnbooked(78)
							} else
								seatIsBooked(78)
							if ("booked" != bookedVehicles!!.seat80) {
								seatIsUnbooked(79)
							} else
								seatIsBooked(79)
							if ("booked" != bookedVehicles!!.seat81) {
								seatIsUnbooked(80)
							} else
								seatIsBooked(80)
							if ("booked" != bookedVehicles!!.seat82) {
								seatIsUnbooked(81)
							} else
								seatIsBooked(81)
							if ("booked" != bookedVehicles!!.seat83) {
								seatIsUnbooked(82)
							} else
								seatIsBooked(82)
							if ("booked" != bookedVehicles!!.seat84) {
								seatIsUnbooked(83)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat85) {
								seatIsUnbooked(84)
							} else
								seatIsBooked(84)
							if ("booked" != bookedVehicles!!.seat86) {
								seatIsUnbooked(85)
							} else
								seatIsBooked(85)
							if ("booked" != bookedVehicles!!.seat87) {
								seatIsUnbooked(86)
							} else
								seatIsBooked(86)
							if ("booked" != bookedVehicles!!.seat88) {
								seatIsUnbooked(87)
							} else
								seatIsBooked(87)
							if ("booked" != bookedVehicles!!.seat89) {
								seatIsUnbooked(88)
							} else
								seatIsBooked(88)
							if ("booked" != bookedVehicles!!.seat90) {
								seatIsUnbooked(89)
							} else
								seatIsBooked(89)
							
						} catch (ex: IndexOutOfBoundsException) {
							ex.message
						}
						
					}
				}
				
				override fun onChildRemoved(dataSnapshot: DataSnapshot) {
					bookedVehicles = dataSnapshot.getValue(BookedVehicles::class.java)
					if (bookedVehicles != null) {
						bookedVehicles!!.key = dataSnapshot.key
						try {
							if ("booked" != bookedVehicles!!.seat1) {
								seatIsUnbooked(0)
							} else
								seatIsBooked(0)
							if ("booked" != bookedVehicles!!.seat2) {
								seatIsUnbooked(1)
							} else
								seatIsBooked(1)
							if ("booked" != bookedVehicles!!.seat3) {
								seatIsUnbooked(2)
							} else
								seatIsBooked(2)
							if ("booked" != bookedVehicles!!.seat4) {
								seatIsUnbooked(3)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat5) {
								seatIsUnbooked(4)
							} else
								seatIsBooked(4)
							if ("booked" != bookedVehicles!!.seat6) {
								seatIsUnbooked(5)
							} else
								seatIsBooked(5)
							if ("booked" != bookedVehicles!!.seat7) {
								seatIsUnbooked(6)
							} else
								seatIsBooked(6)
							if ("booked" != bookedVehicles!!.seat8) {
								seatIsUnbooked(7)
							} else
								seatIsBooked(7)
							if ("booked" != bookedVehicles!!.seat9) {
								seatIsUnbooked(8)
							} else
								seatIsBooked(8)
							if ("booked" != bookedVehicles!!.seat10) {
								seatIsUnbooked(9)
							} else
								seatIsBooked(9)
							if ("booked" != bookedVehicles!!.seat11) {
								seatIsUnbooked(10)
							} else
								seatIsBooked(10)
							if ("booked" != bookedVehicles!!.seat12) {
								seatIsUnbooked(11)
							} else
								seatIsBooked(11)
							if ("booked" != bookedVehicles!!.seat13) {
								seatIsUnbooked(12)
							} else
								seatIsBooked(12)
							if ("booked" != bookedVehicles!!.seat14) {
								seatIsUnbooked(13)
							} else
								seatIsBooked(13)
							if ("booked" != bookedVehicles!!.seat15) {
								seatIsUnbooked(14)
							} else
								seatIsBooked(14)
							if ("booked" != bookedVehicles!!.seat16) {
								seatIsUnbooked(15)
							} else
								seatIsBooked(15)
							if ("booked" != bookedVehicles!!.seat17) {
								seatIsUnbooked(16)
							} else
								seatIsBooked(16)
							if ("booked" != bookedVehicles!!.seat18) {
								seatIsUnbooked(17)
							} else
								seatIsBooked(17)
							if ("booked" != bookedVehicles!!.seat19) {
								seatIsUnbooked(18)
							} else
								seatIsBooked(18)
							if ("booked" != bookedVehicles!!.seat20) {
								seatIsUnbooked(19)
							} else
								seatIsBooked(19)
							if ("booked" != bookedVehicles!!.seat21) {
								seatIsUnbooked(20)
							} else
								seatIsBooked(20)
							if ("booked" != bookedVehicles!!.seat22) {
								seatIsUnbooked(21)
							} else
								seatIsBooked(21)
							if ("booked" != bookedVehicles!!.seat23) {
								seatIsUnbooked(22)
							} else
								seatIsBooked(22)
							if ("booked" != bookedVehicles!!.seat24) {
								seatIsUnbooked(23)
							} else
								seatIsBooked(23)
							if ("booked" != bookedVehicles!!.seat25) {
								seatIsUnbooked(24)
							} else
								seatIsBooked(24)
							if ("booked" != bookedVehicles!!.seat26) {
								seatIsUnbooked(25)
							} else
								seatIsBooked(25)
							if ("booked" != bookedVehicles!!.seat27) {
								seatIsUnbooked(26)
							} else
								seatIsBooked(26)
							if ("booked" != bookedVehicles!!.seat28) {
								seatIsUnbooked(27)
							} else
								seatIsBooked(27)
							if ("booked" != bookedVehicles!!.seat29) {
								seatIsUnbooked(28)
							} else
								seatIsBooked(28)
							if ("booked" != bookedVehicles!!.seat30) {
								seatIsUnbooked(29)
							} else
								seatIsBooked(29)
							if ("booked" != bookedVehicles!!.seat31) {
								seatIsUnbooked(30)
							} else
								seatIsBooked(30)
							if ("booked" != bookedVehicles!!.seat32) {
								seatIsUnbooked(31)
							} else
								seatIsBooked(31)
							if ("booked" != bookedVehicles!!.seat33) {
								seatIsUnbooked(32)
							} else
								seatIsBooked(32)
							if ("booked" != bookedVehicles!!.seat34) {
								seatIsUnbooked(33)
							} else
								seatIsBooked(33)
							if ("booked" != bookedVehicles!!.seat35) {
								seatIsUnbooked(34)
							} else
								seatIsBooked(34)
							if ("booked" != bookedVehicles!!.seat36) {
								seatIsUnbooked(35)
							} else
								seatIsBooked(35)
							if ("booked" != bookedVehicles!!.seat37) {
								seatIsUnbooked(36)
							} else
								seatIsBooked(36)
							if ("booked" != bookedVehicles!!.seat38) {
								seatIsUnbooked(37)
							} else
								seatIsBooked(37)
							if ("booked" != bookedVehicles!!.seat39) {
								seatIsUnbooked(38)
							} else
								seatIsBooked(38)
							if ("booked" != bookedVehicles!!.seat40) {
								seatIsUnbooked(39)
							} else
								seatIsBooked(39)
							if ("booked" != bookedVehicles!!.seat41) {
								seatIsUnbooked(40)
							} else
								seatIsBooked(40)
							if ("booked" != bookedVehicles!!.seat42) {
								seatIsUnbooked(41)
							} else
								seatIsBooked(41)
							if ("booked" != bookedVehicles!!.seat43) {
								seatIsUnbooked(42)
							} else
								seatIsBooked(42)
							if ("booked" != bookedVehicles!!.seat44) {
								seatIsUnbooked(43)
							} else
								seatIsBooked(43)
							if ("booked" != bookedVehicles!!.seat45) {
								seatIsUnbooked(44)
							} else
								seatIsBooked(44)
							if ("booked" != bookedVehicles!!.seat46) {
								seatIsUnbooked(45)
							} else
								seatIsBooked(45)
							if ("booked" != bookedVehicles!!.seat47) {
								seatIsUnbooked(46)
							} else
								seatIsBooked(46)
							if ("booked" != bookedVehicles!!.seat48) {
								seatIsUnbooked(47)
							} else
								seatIsBooked(47)
							if ("booked" != bookedVehicles!!.seat49) {
								seatIsUnbooked(48)
							} else
								seatIsBooked(48)
							if ("booked" != bookedVehicles!!.seat50) {
								seatIsUnbooked(49)
							} else
								seatIsBooked(49)
							if ("booked" != bookedVehicles!!.seat51) {
								seatIsUnbooked(50)
							} else
								seatIsBooked(50)
							if ("booked" != bookedVehicles!!.seat52) {
								seatIsUnbooked(51)
							} else
								seatIsBooked(51)
							if ("booked" != bookedVehicles!!.seat53) {
								seatIsUnbooked(52)
							} else
								seatIsBooked(52)
							if ("booked" != bookedVehicles!!.seat54) {
								seatIsUnbooked(53)
							} else
								seatIsBooked(53)
							if ("booked" != bookedVehicles!!.seat55) {
								seatIsUnbooked(54)
							} else
								seatIsBooked(54)
							if ("booked" != bookedVehicles!!.seat56) {
								seatIsUnbooked(55)
							} else
								seatIsBooked(55)
							if ("booked" != bookedVehicles!!.seat57) {
								seatIsUnbooked(56)
							} else
								seatIsBooked(56)
							if ("booked" != bookedVehicles!!.seat58) {
								seatIsUnbooked(57)
							} else
								seatIsBooked(57)
							if ("booked" != bookedVehicles!!.seat59) {
								seatIsUnbooked(58)
							} else
								seatIsBooked(58)
							if ("booked" != bookedVehicles!!.seat60) {
								seatIsUnbooked(59)
							} else
								seatIsBooked(59)
							if ("booked" != bookedVehicles!!.seat61) {
								seatIsUnbooked(60)
							} else
								seatIsBooked(60)
							if ("booked" != bookedVehicles!!.seat62) {
								seatIsUnbooked(61)
							} else
								seatIsBooked(61)
							if ("booked" != bookedVehicles!!.seat63) {
								seatIsUnbooked(62)
							} else
								seatIsBooked(62)
							if ("booked" != bookedVehicles!!.seat64) {
								seatIsUnbooked(63)
							} else
								seatIsBooked(63)
							if ("booked" != bookedVehicles!!.seat65) {
								seatIsUnbooked(64)
							} else
								seatIsBooked(64)
							if ("booked" != bookedVehicles!!.seat66) {
								seatIsUnbooked(65)
							} else
								seatIsBooked(65)
							if ("booked" != bookedVehicles!!.seat67) {
								seatIsUnbooked(66)
							} else
								seatIsBooked(66)
							if ("booked" != bookedVehicles!!.seat68) {
								seatIsUnbooked(67)
							} else
								seatIsBooked(67)
							if ("booked" != bookedVehicles!!.seat69) {
								seatIsUnbooked(68)
							} else
								seatIsBooked(68)
							if ("booked" != bookedVehicles!!.seat70) {
								seatIsUnbooked(69)
							} else
								seatIsBooked(69)
							if ("booked" != bookedVehicles!!.seat71) {
								seatIsUnbooked(70)
							} else
								seatIsBooked(70)
							if ("booked" != bookedVehicles!!.seat72) {
								seatIsUnbooked(71)
							} else
								seatIsBooked(71)
							if ("booked" != bookedVehicles!!.seat73) {
								seatIsUnbooked(72)
							} else
								seatIsBooked(72)
							if ("booked" != bookedVehicles!!.seat74) {
								seatIsUnbooked(73)
							} else
								seatIsBooked(73)
							if ("booked" != bookedVehicles!!.seat75) {
								seatIsUnbooked(74)
							} else
								seatIsBooked(74)
							if ("booked" != bookedVehicles!!.seat76) {
								seatIsUnbooked(75)
							} else
								seatIsBooked(75)
							if ("booked" != bookedVehicles!!.seat77) {
								seatIsUnbooked(76)
							} else
								seatIsBooked(76)
							if ("booked" != bookedVehicles!!.seat78) {
								seatIsUnbooked(77)
							} else
								seatIsBooked(77)
							if ("booked" != bookedVehicles!!.seat79) {
								seatIsUnbooked(78)
							} else
								seatIsBooked(78)
							if ("booked" != bookedVehicles!!.seat80) {
								seatIsUnbooked(79)
							} else
								seatIsBooked(79)
							if ("booked" != bookedVehicles!!.seat81) {
								seatIsUnbooked(80)
							} else
								seatIsBooked(80)
							if ("booked" != bookedVehicles!!.seat82) {
								seatIsUnbooked(81)
							} else
								seatIsBooked(81)
							if ("booked" != bookedVehicles!!.seat83) {
								seatIsUnbooked(82)
							} else
								seatIsBooked(82)
							if ("booked" != bookedVehicles!!.seat84) {
								seatIsUnbooked(83)
							} else
								seatIsBooked(3)
							if ("booked" != bookedVehicles!!.seat85) {
								seatIsUnbooked(84)
							} else
								seatIsBooked(84)
							if ("booked" != bookedVehicles!!.seat86) {
								seatIsUnbooked(85)
							} else
								seatIsBooked(85)
							if ("booked" != bookedVehicles!!.seat87) {
								seatIsUnbooked(86)
							} else
								seatIsBooked(86)
							if ("booked" != bookedVehicles!!.seat88) {
								seatIsUnbooked(87)
							} else
								seatIsBooked(87)
							if ("booked" != bookedVehicles!!.seat89) {
								seatIsUnbooked(88)
							} else
								seatIsBooked(88)
							if ("booked" != bookedVehicles!!.seat90) {
								seatIsUnbooked(89)
							} else
								seatIsBooked(89)
							
						} catch (ex: IndexOutOfBoundsException) {
							ex.message
						}
						
					}
					
				}
				
				override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
				
				}
				
				override fun onCancelled(databaseError: DatabaseError) {
				
				}
			})
		
		
		seatIcon =
			BitmapFactory.decodeResource(this.resources, R.drawable.seat_layout_screen_nor_avl)
		seatBooked =
			BitmapFactory.decodeResource(this.resources, R.drawable.seat_layout_screen_nor_std)
		try {
			totalSeat(Integer.parseInt(recordByNumberOfSeats!!))
		} catch (exception: IndexOutOfBoundsException) {
			exception.message
		}
		
		gridView = findViewById(R.id.gridView1)
		seatSelectionAdapter = SeatSelectionAdapter(this, R.layout.seatrow_grid, gridArray)
		gridView.adapter = seatSelectionAdapter
		
		gridView.choiceMode = GridView.CHOICE_MODE_MULTIPLE_MODAL
		gridView.onItemClickListener =
			AdapterView.OnItemClickListener { parent, view, position, id ->
				item = gridArray[position]
				seatNumber = item!!.getTitle()
				Log.d("Clicked Grid Item: ", seatNumber)
				
				val seatcompare = item!!.getImage()
				if (seatcompare == seatIcon) {
					seatSelected(position)
				} else {
					seatDeselected(position)
				}
			}
		
		//Use credentials from your Lipa na MPESA Online(MPesa Express) App from the developer portal
		getToken("GoDmHpTEG6bvLdXzIW0oaidG9QS11I2l", "LYLmSXBMyk5W5CUC")
		
		
		mRegistrationBroadcastReceiver = object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				
				// checking for type intent filter
				if (intent.action == Config.REGISTRATION_COMPLETE) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL)
					getFirebaseRegId()
					
				} else if (intent.action == Config.PUSH_NOTIFICATION) {
					// new push notification is received
					
					val message = intent.getStringExtra("message")
					//Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
					createNotification(message)
					showResultdialog(message)
				}
			}
		}
		
		getFirebaseRegId()
	}
	
	private fun getToken(clientKey: String, clientSectret: String): String? {
		
		try {
			val keys = "$clientKey:$clientSectret"
			val base64 = Base64.encodeToString(keys.toByteArray(), Base64.DEFAULT)
			val client = OkHttpClient()
			val request = Request.Builder()
				.url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
				.get()
				.addHeader("authorization", "Basic " + base64.trim { it <= ' ' })
				.addHeader("cache-control", "no-cache")
				.addHeader("postman-token", "b0432d90-dc69-1b08-e289-695651a7d5dd")
				.build()
			
			client.newCall(request)
				.enqueue(object : okhttp3.Callback {
					override fun onFailure(call: okhttp3.Call, e: IOException) {
						runOnUiThread {
							Toast.makeText(
								this@DirectBook,
								"Fetching token failed",
								Toast.LENGTH_LONG
							).show()
						}
					}
					
					@Throws(IOException::class)
					override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
						token = response.body()?.string()
						val jsonParser = JsonParser()
						val jo = jsonParser.parse(token!!).asJsonObject
						//Log.e("Token", token + jo.get("access_token"));
						token = jo.get("access_token").asString
						Log.e("access_token", token)
						Log.e("expires_in", jo.get("expires_in").asString)
						stkPushService = ApiUtils.getTasksService(token)
					}
				})
		} catch (e: Exception) {
			//e.printStackTrace();
			Toast.makeText(this@DirectBook, "Please add your app credentials", Toast.LENGTH_LONG)
				.show()
		}
		
		return token
	}
	
	private fun getPhoneNumber() {
		LovelyTextInputDialog(this, R.style.AppTheme_NoActionBar)
			.setTopColorRes(R.color.colorPrimary)
			.setTitle("Lipa Na MPESA Online")
			.setMessage("You are about to pay for your Bus Ticket using MPESA. Ensure you enter your number in the correct format (254XXX)")
			.setIcon(R.drawable.ic_mpesa)
			.setInputFilter("Please Enter Number") { text -> text.matches("\\w+".toRegex()) }
			.setConfirmButton(android.R.string.ok) { text ->
				phoneNumber = text
				try {
					performSTKPush(phoneNumber)
				} catch (e: Exception) {
					Toast.makeText(this@DirectBook, "Error fetchng token", Toast.LENGTH_SHORT)
						.show()
				}
			}
			.show()
	}
	
	fun mpesaTransactionCode() {
		LovelyTextInputDialog(this, R.style.AppTheme_NoActionBar)
			.setTopColorRes(R.color.colorPrimary)
			.setTitle("MPESA Transaction Code")
			.setMessage("Please Enter Transaction Code Received. To find this code, Please find your ticket payment message from MPESA & copy/paste the Transaction code (LLC8NL8FOC)")
			.setIcon(R.drawable.ic_smartphone)
			.setInputFilter("Please correct transaction code") { text -> text.matches("\\w+".toRegex()) }
			.setConfirmButton(android.R.string.ok) {
				try {
					// Access a Firebase Real Database instance from your Activity
					db = FirebaseDatabase.getInstance()
					db?.reference?.child("bookedVehicles")?.child(recordByKeyValue!!)
						?.child(seatNumber!!)?.setValue("booked")
					postTicketReceipt()
					congratulations()
				} catch (e: Exception) {
					Toast.makeText(this@DirectBook, "Error fetching token", Toast.LENGTH_SHORT)
						.show()
				}
			}
			.show()
	}
	
	private fun performSTKPush(phone_number: String) {
		Log.e("Checkout button clicked", "Button Clicked")
		val stkPush = STKPush(
			"174379",
			"MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3",
			"20160216165627",
			"CustomerPayBillOnline",
			"1",
			phone_number,
			"174379",
			phone_number,
			"https://spurquoteapp.ga/pusher/pusher.php?companyName=stk_push&message=result&push_type=individual&regId=" + regId!!,
			"test",
			"test"
		)
		Log.e("Party B", phone_number)
		val call = stkPushService.sendPush(stkPush)
		call.enqueue(object : Callback<STKPush> {
			override fun onResponse(call: Call<STKPush>, response: Response<STKPush>) {
				try {
					//Log.e("Response SUccess", response.toString());
					if (response.isSuccessful) {
						Log.e(TAG, "post submitted to API." + response.message())
						mpesaTransactionCode()
					} else {
						Log.e("Response", response.errorBody()?.string())
					}
				} catch (e: Exception) {
					e.printStackTrace()
				}
				
			}
			
			override fun onFailure(call: Call<STKPush>, t: Throwable) {
				Log.e(TAG, "Unable to submit post to API." + t.message)
				t.printStackTrace()
				Log.e("Error message", t.localizedMessage)
			}
		})
	}
	
	private fun getFirebaseRegId() {
		val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
		regId = pref.getString("regId", null)
		
		Log.d(TAG, "Firebase reg id: " + regId!!)
		
		if (!TextUtils.isEmpty(regId)) {
			val storeKey = StoreKey(this@DirectBook)
			storeKey.createKey(regId)
		}
	}
	
	fun createNotification(content: String) {
		var noti: Notification? = null
		noti = Notification.Builder(this)
			.setContentTitle(content)
			.setContentText("Subject").setSmallIcon(R.mipmap.ic_launcher).build()
		
		val notificationManager =
			getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		// hide the notification after its selected
		assert(noti != null)
		noti?.flags = noti?.flags?.or(Notification.FLAG_AUTO_CANCEL)
		
		notificationManager.notify(0, noti)
		
	}
	
	fun showResultdialog(result: String) {
		val prefs = PreferenceManager.getDefaultSharedPreferences(this)
		if (!prefs.getBoolean("firstTime", false)) {
			// run your one time code
			val editor = prefs.edit()
			editor.putBoolean("firstTime", true)
			editor.apply()
		}
	}
	
	override fun onResume() {
		super.onResume()
		
		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(
			mRegistrationBroadcastReceiver!!,
			IntentFilter(Config.REGISTRATION_COMPLETE)
		)
		
		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(
			mRegistrationBroadcastReceiver!!,
			IntentFilter(Config.PUSH_NOTIFICATION)
		)
		
		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications(applicationContext)
	}
	
	override fun onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver!!)
		super.onPause()
	}
	
	// displays the total number of seats for a vehicle
	private fun totalSeat(n: Int) {
		for (i in 1..n) {
			gridArray.add(Item(seatIcon, "seat$i"))
		}
	}
	
	// handles the seat selections
	private fun seatSelected(pos: Int) {
		getPhoneNumber()
	}
	
	// handles the seat booked
	fun seatIsUnbooked(pos: Int) {
		gridArray.removeAt(pos)
		val i = pos + 1
		gridArray.add(pos, Item(seatIcon, "seat$i"))
		seatSelectionAdapter.notifyDataSetChanged()
	}
	
	// handles the seat booked
	fun seatIsBooked(pos: Int) {
		gridArray.removeAt(pos)
		gridArray.add(pos, Item(seatBooked, "Booked"))
		seatSelectionAdapter.notifyDataSetChanged()
	}
	
	// handles the seat deselection
	private fun seatDeselected(pos: Int) {
		gridArray.removeAt(pos)
		val i = pos + 1
		gridArray.add(pos, Item(seatBooked, "Booked"))
		Toast.makeText(applicationContext, "Booked Already", Toast.LENGTH_SHORT).show()
		seatSelectionAdapter.notifyDataSetChanged()
	}
	
	fun performTransactionQuery() {
		val queryRequest = QueryRequest(
			"174379",
			"MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3",
			"20160216165627",
			""
		)
		
		val requestCall = stkPushService.sendRequest(queryRequest)
		requestCall.enqueue(object : Callback<QueryRequest> {
			override fun onResponse(call: Call<QueryRequest>, response: Response<QueryRequest>) {
				try {
					//Log.e("Response SUccess", response.toString());
					if (response.isSuccessful) {
						Log.e(TAG, "Query successful response" + response.body()!!.toString())
						
						
					} else {
						Log.e("Response unsuccessful", response.errorBody()!!.string())
					}
				} catch (e: Exception) {
					e.printStackTrace()
				}
				
			}
			
			override fun onFailure(call: Call<QueryRequest>, t: Throwable) {
				Log.e(TAG, "Unable to Query" + t.message)
				t.printStackTrace()
				Log.e("Error message", t.localizedMessage)
			}
		})
	}
	
	// allows posting of the booking transaction as receipt details.
	private fun postTicketReceipt() {
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db?.reference?.child("vehicles") // Database child name
		
		// Read from the database by querying using the orderByKey to return values by key.
		myRef?.orderByKey()?.equalTo(recordByKeyValue)
			?.addChildEventListener(object : ChildEventListener {
				
				override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
					// This method is called once with the initial value and again
					// whenever data at this location is updated.
					vehicleDetails = dataSnapshot.getValue(VehicleDetails::class.java)
					Log.d(TAG, "Value is: $vehicleDetails")
					if (vehicleDetails != null) {
						vehicleDetails?.key = dataSnapshot.key
						ticketDetails = TicketDetails()
						ticketDetails?.companyName = vehicleDetails?.companyName
						ticketDetails?.boardingPoint = vehicleDetails?.boardingPoint
						ticketDetails?.departureTime = vehicleDetails?.departureTime
						ticketDetails?.numberPlate = vehicleDetails?.numberPlate
						ticketDetails?.priceInKsh = vehicleDetails?.priceInKsh
						ticketDetails?.travelRoute = vehicleDetails?.travelRoute
						ticketDetails?.vehicleType = vehicleDetails?.vehicleType
						ticketDetails?.seatNumber = seatNumber
						ticketDetails?.mpesaTransactionCode = mpesaCode
					}
					val uid = FirebaseAuth.getInstance().uid
					db?.reference?.child("tickets")?.child(uid!!)?.push()?.setValue(ticketDetails)
				}
				
				override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
				
				}
				
				override fun onChildRemoved(dataSnapshot: DataSnapshot) {
				
				}
				
				override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
				
				}
				
				override fun onCancelled(error: DatabaseError) {
					// Failed to read value
					Log.w(TAG, "Failed to read value.", error.toException())
				}
			})
	}
	
	// print the congratulations message for paying and getting a ticket
	private fun congratulations() {
		LovelyInfoDialog(this)
			.setTopColorRes(R.color.colorPrimary)
			.setIcon(R.drawable.ic_confetti)
			//This will add Don't show again checkbox to the dialog. You can pass any ID as argument
			//                .setNotShowAgainOptionEnabled(0)
			//                .setNotShowAgainOptionChecked(true)
			.setTitle("Congratulations")
			.setMessage("Thank you " + FirebaseAuth.getInstance().currentUser?.displayName + " for paying. Your Ticket is now available under My Tickets. Happy Travelling")
			.show()
	}
	
	companion object {
		
		private val TAG = Home::class.java.simpleName
	}
}
