package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.adapters.ViewAllAdapter
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

class ViewAll : AppCompatActivity() {
	
	private val TAG = "View All"
	private var db: FirebaseDatabase? = null
	private var viewAllRecyclerView: RecyclerView? = null
	private var viewAllAdapter: ViewAllAdapter? = null
	private val listOfAllBookableVehicles = ArrayList<VehicleDetails>()
	private var vehicleDetails: VehicleDetails? = null
	private var recordsByKey: String? = null
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_view_all)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		
		readAllBookableVehicles()
		
	}
	
	// Access Database to retrieve data
	private fun readAllBookableVehicles() {
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db?.reference?.child("vehicles")
		
		// Read from the database
		myRef?.addChildEventListener(object : ChildEventListener {
			
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
				viewAllAdapter?.notifyDataSetChanged()
			}
			
			override fun onChildRemoved(dataSnapshot: DataSnapshot) {
				viewAllAdapter?.notifyDataSetChanged()
			}
			
			override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
				viewAllAdapter?.notifyDataSetChanged()
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
		viewAllRecyclerView = findViewById(R.id.listView_ViewAll)
		val mLayoutManager = LinearLayoutManager(applicationContext)
		viewAllRecyclerView?.layoutManager = mLayoutManager
		viewAllRecyclerView?.itemAnimator = DefaultItemAnimator()
		viewAllAdapter =
			ViewAllAdapter(listOfAllBookableVehicles, ViewAllAdapter.OnItemClickListener { item ->
				recordsByKey = item.key
				val intent = Intent(applicationContext, Booking::class.java)
				intent.putExtra("recordsByKey", recordsByKey)
				startActivity(intent)
			})
		viewAllRecyclerView?.adapter = viewAllAdapter
		vehicleDetails?.let { listOfAllBookableVehicles.add(it) }
		viewAllAdapter?.notifyDataSetChanged()
	}
	
}
