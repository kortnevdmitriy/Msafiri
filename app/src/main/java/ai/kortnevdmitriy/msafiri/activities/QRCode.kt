package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.TicketDetails
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import net.glxn.qrgen.core.scheme.VCard

class QRCode : AppCompatActivity() {
	private val TAG = QRCode::class.java.name
	private var data: String? = null
	private var db: FirebaseDatabase? = null
	private var ticketsDetails: TicketDetails? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_qrcode)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		val uid = FirebaseAuth.getInstance().uid!!
		
		data = intent.getStringExtra("ticketKeyValue")
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db!!.reference.child("tickets").child(uid)
		
		// Read from the database by querying using the orderByKey to return values by key.
		myRef.orderByKey().equalTo(data).addChildEventListener(object : ChildEventListener {
			
			override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
				// This method is called once with the initial value and again
				// whenever data at this location is updated.
				ticketsDetails = dataSnapshot.getValue(TicketDetails::class.java)
				Log.d(TAG, "Value is: " + ticketsDetails!!)
				if (ticketsDetails != null) {
					val ticketDetails = TicketDetails()
					ticketDetails.companyName = ticketsDetails?.companyName
					ticketDetails.travelRoute = ticketsDetails?.travelRoute
					ticketDetails.numberPlate = ticketsDetails?.numberPlate
					ticketDetails.mpesaTransactionCode = ticketsDetails?.mpesaTransactionCode
					ticketDetails.boardingPoint = ticketsDetails?.boardingPoint
					ticketDetails.companyName = ticketsDetails?.departureTime
					ticketDetails.seatNumber = ticketsDetails?.seatNumber
					ticketDetails.vehicleType = ticketsDetails?.vehicleType
					ticketDetails.priceInKsh = ticketsDetails?.priceInKsh
					Log.d(TAG, "onChildAdded: $ticketDetails")
					
					val passenger = findViewById<TextView>(R.id.passenger)
					val seat = findViewById<TextView>(R.id.seat)
					val dateTime = findViewById<TextView>(R.id.dateTime)
					val bei = findViewById<TextView>(R.id.bei)
					val route = findViewById<TextView>(R.id.route)
					val pnr = findViewById<TextView>(R.id.pnr)
					val qr_code = findViewById<ImageView>(R.id.qr_code)
					
					val vCard = VCard()
					vCard.name = FirebaseAuth.getInstance().currentUser!!.displayName
					vCard.address = ticketDetails.mpesaTransactionCode
					vCard.note = ticketDetails.companyName
					vCard.title = ticketDetails.travelRoute
					val myBitmap = net.glxn.qrgen.android.QRCode.from(vCard).bitmap()
					
					passenger.text = FirebaseAuth.getInstance().currentUser!!.displayName
					seat.text = ticketDetails.seatNumber
					dateTime.text = ticketDetails.companyName
					bei.text = ticketDetails.priceInKsh
					route.text = ticketDetails.travelRoute
					pnr.text = ticketDetails.mpesaTransactionCode
					qr_code.setImageBitmap(myBitmap)
					
					
				}
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
	
}
