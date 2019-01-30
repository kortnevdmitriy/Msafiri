package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Account : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_account)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		
		val user = FirebaseAuth.getInstance().currentUser
		val displayName = user?.displayName
		val userEmail = user?.email
		val phoneNumber = user?.phoneNumber
		val photoUrl = user?.photoUrl
		
		
		val tvDisplayName = findViewById<TextView>(R.id.user_profile_name)
		val tvDisplayBio = findViewById<TextView>(R.id.user_profile_short_bio)
		val tvDisplayEmail = findViewById<TextView>(R.id.user_profile_email)
		val tvDisplayPhone = findViewById<TextView>(R.id.user_profile_phone)
		val avatarImageView = findViewById<CircleImageView>(R.id.user_profile_photo)
		Picasso.get().load(photoUrl).into(avatarImageView)
		tvDisplayName.text = displayName
		tvDisplayEmail.text = userEmail
		tvDisplayPhone.text = phoneNumber
	}
	
}
