package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MyTrips : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_my_trips)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}
	
}
