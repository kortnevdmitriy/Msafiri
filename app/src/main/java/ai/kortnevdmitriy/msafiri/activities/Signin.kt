package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tapadoo.alerter.Alerter

class Signin : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
	View.OnClickListener {
	private var mGoogleApiClient: GoogleApiClient? = null
	private var mSignInButton: SignInButton? = null
	private var auth: FirebaseAuth? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		auth = FirebaseAuth.getInstance() // Get Firebase auth instance
		if (auth?.currentUser != null) {
			startActivity(Intent(applicationContext, Home::class.java))
			finish()
		}
		
		// Make Screen Window Fullscreen before setting the contents.
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)
		
		setContentView(R.layout.activity_signin)
		
		mSignInButton = findViewById(R.id.sign_in_button)
		mSignInButton?.setSize(SignInButton.SIZE_WIDE)
		mSignInButton?.setOnClickListener(this)
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			//.requestIdToken(getString(R.string.default_web_client_id))
			.requestEmail()
			.build()
		mGoogleApiClient = GoogleApiClient.Builder(this)
			.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
			.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
			.build()
		auth = FirebaseAuth.getInstance()
	}
	
	
	private fun handleFirebaseAuthResult(authResult: AuthResult?) {
		if (authResult != null) {
			// Welcome the user
			val user = authResult.user
			Toast.makeText(this, "Welcome " + user.displayName!!, Toast.LENGTH_SHORT).show()
			
			// Go back to the main activity
			startActivity(Intent(applicationContext, Home::class.java))
		}
		
	}
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.sign_in_button -> signIn()
			else -> return
		}
	}
	
	private fun signIn() {
		val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
		startActivityForResult(signInIntent, RC_SIGN_IN)
	}
	
	public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
			if (result.isSuccess) {
				// Google Sign In was successful, authenticate with Firebase
				val account = result.signInAccount
				firebaseAuthWithGoogle(account!!)
			} else {
				// Google Sign In failed
				googleSignInErrorNotification()
				Log.e(TAG, "Google Sign In failed.")
			}
		}
	}
	
	private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
		val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
		auth!!.signInWithCredential(credential)
			.addOnCompleteListener(this) { task ->
				Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful)
				
				// If sign in fails, display a message to the user. If sign in succeeds
				// the auth state listener will be notified and logic to handle the
				// signed in user can be handled in the listener.
				if (!task.isSuccessful) {
					Log.w(TAG, "signInWithCredential", task.exception)
					authenticationErrorNotification()
				} else {
					startActivity(Intent(applicationContext, Home::class.java))
					finish()
				}
			}
	}
	
	
	override fun onConnectionFailed(connectionResult: ConnectionResult) {
		// An unresolvable error has occurred and Google APIs (including Sign-In) will not
		// be available.
		Log.d(TAG, "onConnectionFailed:$connectionResult")
		Alerter.create(this)
			.setTitle("Google Play")
			.setText("Google Play Services error")
			.setBackgroundColorRes(android.R.color.holo_red_dark) // or setBackgroundColorInt(Color.CYAN)
			.show()
	}
	
	private fun authenticationErrorNotification() {
		Alerter.create(this)
			.setTitle("Authentication")
			.setText("Authentication failed. Check your Internet")
			.setBackgroundColorRes(android.R.color.holo_red_dark) // or setBackgroundColorInt(Color.CYAN)
			.show()
	}
	
	private fun googleSignInErrorNotification() {
		Alerter.create(this)
			.setTitle("Google Signin")
			.setText("Google Sign In failed")
			.setBackgroundColorRes(android.R.color.holo_red_dark) // or setBackgroundColorInt(Color.CYAN)
			.show()
	}
	
	companion object {
		private const val TAG = "SignInActivity"
		private const val RC_SIGN_IN = 9001
	}
}
