package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.utilities.Constants
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
	
	companion object {
		private const val REQUEST_INVITE = 0
		private val TAG = Home::class.java.simpleName
	}
	
	private lateinit var extractedTravelRoute: String
	private var navigationView: NavigationView? = null
	private var destinationFrom: AutoCompleteTextView? = null
	private var destinationTo: AutoCompleteTextView? = null
	private var adapter: ArrayAdapter<String>? = null
	private val separator = "-"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)
		val fab = findViewById<FloatingActionButton>(R.id.fab)
		fab.setOnClickListener {
			extractTravelRoute()
			/*Snackbar.make(view, "Searching the database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
		}
		
		val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		val toggle = ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
		)
		drawer.addDrawerListener(toggle)
		toggle.syncState()
		
		navigationView = findViewById(R.id.nav_view)
		navigationView?.setNavigationItemSelectedListener(this)
		
		updateNavigationViewUI()
		searchUI()
		
	}
	
	override fun onBackPressed() {
		val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}
	
	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.home, menu)
		return true
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		val id = item.itemId
		
		if (id == R.id.action_settings) {
			startActivity(Intent(applicationContext, Settings::class.java))
			return true
		}
		if (id == R.id.action_signout) {
			FirebaseAuth.getInstance().signOut()
			startActivity(Intent(applicationContext, Signin::class.java))
			return true
		}
		
		return super.onOptionsItemSelected(item)
	}
	
	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		// Handle navigation view item clicks here.
		val id = item.itemId
		
		if (id == R.id.nav_view_all) {
			startActivity(Intent(applicationContext, ViewAll::class.java))
		} else if (id == R.id.nav_account) {
			startActivity(Intent(applicationContext, Account::class.java))
		} else if (id == R.id.nav_my_tickets) {
			startActivity(Intent(applicationContext, Tickets::class.java))
		} else if (id == R.id.nav_share) {
			onInviteClicked()
		}
		
		val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
		drawer.closeDrawer(GravityCompat.START)
		return true
	}
	
	/* This methods update the navigation views with the account owner details.
   Picture, name & email*/
	private fun updateNavigationViewUI() {
		val header = navigationView?.getHeaderView(0)
		val picView = header?.findViewById<CircleImageView>(R.id.picView)
		val nameView = header?.findViewById<TextView>(R.id.nameView)
		val emailView = header?.findViewById<TextView>(R.id.emailView)
		
		val mFirebaseUser = FirebaseAuth.getInstance().currentUser
		nameView?.text = mFirebaseUser?.displayName
		emailView?.text = mFirebaseUser?.email
		Picasso.get().load(mFirebaseUser?.photoUrl).into(picView)
	}
	
	fun searchUI() {
		adapter =
			ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, Constants.DESTINATIONS)
		destinationFrom = findViewById(R.id.autoCompleteDestinationFrom)
		destinationTo = findViewById(R.id.autoCompleteDestinationTo)
		destinationFrom?.setAdapter<ArrayAdapter<String>>(adapter)
		destinationTo?.setAdapter<ArrayAdapter<String>>(adapter)
	}
	
	private fun extractTravelRoute() {
		val destFrom = destinationFrom?.text.toString().trim { it <= ' ' }
		val destTo = destinationTo?.text.toString().trim { it <= ' ' }
		
		if (TextUtils.isEmpty(destFrom)) {
			Toast.makeText(applicationContext, "Fill Travelling From!", Toast.LENGTH_SHORT).show()
			return
		}
		if (TextUtils.isEmpty(destTo)) {
			Toast.makeText(applicationContext, "Fill Travelling To", Toast.LENGTH_SHORT).show()
			return
		}
		
		extractedTravelRoute = destFrom + separator + destTo
		Log.i("Extracted Route:", extractedTravelRoute)
		
		val intent = Intent(applicationContext, Search::class.java)
		intent.putExtra("keyName", extractedTravelRoute)
		startActivity(intent)
	}
	
	// Firebase Invite.
	private fun onInviteClicked() {
		val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
			.setMessage(getString(R.string.invitation_message))
			.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
			.setCallToActionText(getString(R.string.invitation_cta))
			.build()
		startActivityForResult(intent, REQUEST_INVITE)
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		Log.d(TAG, "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")
		if (requestCode == REQUEST_INVITE) {
			if (resultCode == Activity.RESULT_OK) {
				// Get the invitation IDs of all sent messages
				val ids = AppInviteInvitation.getInvitationIds(resultCode, data!!)
				for (id in ids) {
					Log.d(TAG, "onActivityResult: sent invitation $id")
				}
				
			} else {
				// Sending failed or it was canceled, show failure message to the user
				// [START_EXCLUDE]
				// [END_EXCLUDE]
			}
			
		}
		
	}
}
