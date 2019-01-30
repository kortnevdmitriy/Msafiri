/*
 *
 *  * Copyright (C) 2017 Safaricom, Ltd.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package ai.kortnevdmitriy.msafiri.activities

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.mpesa.app.Config
import ai.kortnevdmitriy.msafiri.mpesa.utils.NotificationUtils
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessaging

class NotificationActivity : AppCompatActivity() {
	
	companion object {
		private val TAG = Home::class.java.simpleName
	}
	
	private var mRegistrationBroadcastReceiver: BroadcastReceiver? = null
	private var txtMessage: TextView? = null
	private var txtRegId: EditText? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_notification)
		
		txtRegId = findViewById(R.id.txt_reg_id)
		txtMessage = findViewById(R.id.txt_push_message)
		
		mRegistrationBroadcastReceiver = object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				
				// checking for type intent filter
				if (intent.action == Config.REGISTRATION_COMPLETE) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL)
					displayFirebaseRegId()
					
				} else if (intent.action == Config.PUSH_NOTIFICATION) {
					// new push notification is received
					
					val message = intent.getStringExtra("message")
					
					Toast.makeText(
						applicationContext,
						"Push notification: $message",
						Toast.LENGTH_LONG
					).show()
					
					txtMessage!!.text = message
				}
			}
		}
		
		displayFirebaseRegId()
	}
	
	
	// Fetches reg id from shared preferences
	// and displays on the screen
	private fun displayFirebaseRegId() {
		val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
		val regId = pref.getString("regId", null)
		
		//Log.e(TAG, "Firebase reg id: " + regId);
		
		if (!TextUtils.isEmpty(regId))
			txtRegId?.setText("Firebase Reg Id: " + regId!!)
		else
			txtRegId?.setText("Firebase Reg Id is not received yet!")
	}
	
	override fun onResume() {
		super.onResume()
		
		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(
			mRegistrationBroadcastReceiver!!,
			IntentFilter(Config.REGISTRATION_COMPLETE)
		)
		
		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(
			mRegistrationBroadcastReceiver!!,
			IntentFilter(Config.PUSH_NOTIFICATION)
		)
		
		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications(applicationContext)
	}
	
	override fun onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver!!)
		super.onPause()
	}
	
}
