package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jetradar.desertplaceholder.DesertPlaceholder

class DealOffers : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_deal_offers)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		
		val desertPlaceholder = findViewById<DesertPlaceholder>(R.id.placeholder)
		desertPlaceholder.setOnButtonClickListener {
			// do stuff
		}
	}
	
}
