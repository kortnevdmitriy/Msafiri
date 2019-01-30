package ai.kortnevdmitriy.msafiri.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.VehicleDetails
import kotlinx.android.synthetic.main.activity_booking.*

class Booking : AppCompatActivity() {

	private val TAG = Booking::class.java.name
	private var vehicleCompanyName: TextView? = null
	private var vehicleTravelRoute: TextView? = null
	private var vehicleNumberPlate: TextView? = null
	private var vehicleDepartureTime: TextView? = null
	private val vehicleNumberOfSeats: TextView? = null
	private var vehicleBoardingPoint: TextView? = null
	private var vehicleOptionalDescription: TextView? = null
	private var headerCoverImage: ImageView? = null
	private var data: String? = null
	private var db: FirebaseDatabase? = null
	private var vehicleDetails: VehicleDetails? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_booking)
		data = intent.getStringExtra("recordsByKey")
		fab.setOnClickListener {
			val intent = Intent(applicationContext, DirectBook::class.java)
			intent.putExtra("recordByNumberOfSeats", vehicleDetails?.numberOfSeats)
			intent.putExtra("recordByPriceInKsh", vehicleDetails?.priceInKsh)
			intent.putExtra("recordByKeyValue", vehicleDetails?.key)
			startActivity(intent)
		}
		
		viewVehicleInformation()
	}
	

	// Access Database to retrieve the full vehicle information
	private fun viewVehicleInformation() {
		// Access a Firebase Real Database instance from your Activity
		db = FirebaseDatabase.getInstance()
		val myRef = db?.reference?.child("vehicles") // Database child name

		// Read from the database by querying using the orderByKey to return values by key.
		myRef?.orderByKey()?.equalTo(data)?.addChildEventListener(object : ChildEventListener {

			override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
				// This method is called once with the initial value and again
				// whenever data at this location is updated.
				vehicleDetails = dataSnapshot.getValue(VehicleDetails::class.java)
				Log.d(TAG, "Value is: " + vehicleDetails!!)
				if (vehicleDetails != null) {
					vehicleDetails?.key = dataSnapshot.key
					vehicleCompanyName?.text = vehicleDetails?.companyName
					vehicleTravelRoute?.text = vehicleDetails?.travelRoute
					vehicleNumberPlate?.text = vehicleDetails?.numberPlate
					vehicleDepartureTime?.text = vehicleDetails?.departureTime
					vehicleOptionalDescription?.text = vehicleDetails?.optionalDescription
					vehicleBoardingPoint?.text = vehicleDetails?.boardingPoint
					// Picasso.(getApplicationContext()).load(Uri.parse(vehicleDetails.getImg_url())).into(headerCoverImage);
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
