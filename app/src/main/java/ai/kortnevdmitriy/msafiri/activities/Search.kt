package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.adapters.SearchAdapter
import ai.kortnevdmitriy.msafiri.models.VehicleDetails
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Search : AppCompatActivity() {
	
	private val TAG = "Search"
	private var db: FirebaseDatabase? = null
	private var searchRecyclerView: RecyclerView? = null
	private var searchAdapter: SearchAdapter? = null
	private val listOfSearchedVehicles = ArrayList<VehicleDetails>()
	private var vehicleDetails: VehicleDetails? = null
	private var data: String? = null
	private var recordsByKey: String? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		data = intent.getStringExtra("keyName")
		
		searchBookableVehicles()
	}
	
	// Access Database to retrieve data using a Search Algorithm
	private fun searchBookableVehicles() {
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db?.reference?.child("vehicles")
		
		// Read from the database by searching through the travel route children
		myRef?.orderByChild("travelRoute")?.equalTo(data)
			?.addChildEventListener(object : ChildEventListener {
				
				override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
					// This method is called once with the initial value and again
					// whenever data at this location is updated.
					vehicleDetails = dataSnapshot.getValue(VehicleDetails::class.java)
					Log.d(TAG, "Value is: " + vehicleDetails!!)
					if (vehicleDetails != null) {
						vehicleDetails?.key = dataSnapshot.key
						prepareAllVehicleData()
					}
				}
				
				override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
					searchAdapter?.notifyDataSetChanged()
				}
				
				override fun onChildRemoved(dataSnapshot: DataSnapshot) {
					searchAdapter?.notifyDataSetChanged()
				}
				
				override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
					searchAdapter?.notifyDataSetChanged()
				}
				
				override fun onCancelled(error: DatabaseError) {
					// Failed to read value
					Log.w(TAG, "Failed to read value.", error.toException())
				}
			})
	}
	
	// This method prepares and loads data from the database.
	private fun prepareAllVehicleData() {
		// Create a RecyclerView & find it's view by id to populate it with news articles
		searchRecyclerView = findViewById(R.id.listView_Search)
		val mLayoutManager = LinearLayoutManager(applicationContext)
		searchRecyclerView?.layoutManager = mLayoutManager
		searchRecyclerView?.itemAnimator = DefaultItemAnimator()
		searchAdapter =
			SearchAdapter(listOfSearchedVehicles, SearchAdapter.OnItemClickListener { item ->
				recordsByKey = item.key
				val intent = Intent(applicationContext, Booking::class.java)
				intent.putExtra("recordsByKey", recordsByKey)
				startActivity(intent)
			})
		searchRecyclerView?.adapter = searchAdapter
		vehicleDetails?.let { listOfSearchedVehicles.add(it) }
		searchAdapter?.notifyDataSetChanged()
	}
	
}
