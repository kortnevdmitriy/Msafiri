package ai.kortnevdmitriy.msafiri.mpesa.service

import ai.kortnevdmitriy.msafiri.mpesa.app.Config
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created  on 6/30/2017.
 */

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
	
	override fun onTokenRefresh() {
		super.onTokenRefresh()
		val refreshedToken = FirebaseInstanceId.getInstance().token
		
		// Saving reg id to shared preferences
		storeRegIdInPref(refreshedToken)
		
		// sending reg id to your server
		sendRegistrationToServer(refreshedToken)
		
		// Notify UI that registration has completed, so the progress indicator can be hidden.
		val registrationComplete = Intent(Config.REGISTRATION_COMPLETE)
		registrationComplete.putExtra("token", refreshedToken)
		LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
	}
	
	private fun sendRegistrationToServer(token: String?) {
		// sending gcm token to server
		//Log.e(TAG, "sendRegistrationToServer: " + token);
	}
	
	private fun storeRegIdInPref(token: String?) {
		val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
		val editor = pref.edit()
		editor.putString("regId", token)
		editor.apply()
	}
	
	companion object {
		private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
	}
}
