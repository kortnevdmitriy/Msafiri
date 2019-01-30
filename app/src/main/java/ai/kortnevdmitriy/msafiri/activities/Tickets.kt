package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.adapters.TicketsAdapter
import ai.kortnevdmitriy.msafiri.models.TicketDetails
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Tickets : AppCompatActivity() {
	
	private val TAG = Tickets::class.java.name
	private var ticketsRecyclerView: RecyclerView? = null
	private var ticketsAdapter: TicketsAdapter? = null
	private val listOfAllTickets = ArrayList<TicketDetails>()
	private var ticketsDetails: TicketDetails? = null
	private var db: FirebaseDatabase? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_tickets)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		
		readAllTickets()
	}
	
	// Access Database to retrieve data
	private fun readAllTickets() {
		// Access a Firebase Real Database instance from your Activity
		val uid = FirebaseAuth.getInstance().uid
		db = FirebaseDatabase.getInstance()
		assert(uid != null)
		val myRef = db?.reference?.child("tickets")?.child(uid!!)
		
		// Read from the database
		myRef?.addChildEventListener(object : ChildEventListener {
			
			override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
				// This method is called once with the initial value and again
				// whenever data at this location is updated.
				ticketsDetails = dataSnapshot.getValue(TicketDetails::class.java)
				Log.d(TAG, "Value is: " + ticketsDetails!!)
				if (ticketsDetails != null) {
					ticketsDetails?.key = dataSnapshot.key
					prepareAllTicketsData()
				}
			}
			
			override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
				ticketsAdapter?.notifyDataSetChanged()
			}
			
			override fun onChildRemoved(dataSnapshot: DataSnapshot) {
				ticketsAdapter?.notifyDataSetChanged()
			}
			
			override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
				ticketsAdapter?.notifyDataSetChanged()
			}
			
			override fun onCancelled(error: DatabaseError) {
				// Failed to read value
				Log.w(TAG, "Failed to read value.", error.toException())
			}
		})
	}
	
	// This method prepares and loads data from the database.
	private fun prepareAllTicketsData() {
		// Create a RecyclerView & find it's view by id to populate it with tickets
		ticketsRecyclerView = findViewById(R.id.ticketsRecyclerView)
		val mLayoutManager = LinearLayoutManager(applicationContext)
		ticketsRecyclerView?.layoutManager = mLayoutManager
		ticketsRecyclerView?.itemAnimator = DefaultItemAnimator()
		ticketsAdapter =
			TicketsAdapter(listOfAllTickets, TicketsAdapter.OnItemClickListener { item ->
				val ticketKey = item.key
				val intent = Intent(applicationContext, QRCode::class.java)
				intent.putExtra("ticketKeyValue", ticketKey)
				startActivity(intent)
			})
		ticketsRecyclerView?.adapter = ticketsAdapter
		ticketsDetails?.let { listOfAllTickets.add(it) }
		Log.d(TAG, ticketsDetails?.companyName)
		ticketsAdapter?.notifyDataSetChanged()
	}
	
}
