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

package ai.kortnevdmitriy.msafiri.mpesa.service

import ai.kortnevdmitriy.msafiri.activities.Home
import ai.kortnevdmitriy.msafiri.mpesa.app.Config
import ai.kortnevdmitriy.msafiri.mpesa.utils.NotificationUtils
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject


/**
 * Created  on 6/30/2017.
 */

class MyFirebaseMessagingService : FirebaseMessagingService() {
	
	private var notificationUtils: NotificationUtils? = null
	
	override fun onMessageReceived(remoteMessage: RemoteMessage?) {
		//Log.e(TAG, "From: " + remoteMessage.getFrom());
		
		if (remoteMessage == null)
			return
		
		// Check if message contains a notification payload.
		if (remoteMessage.notification != null) {
			//Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
			handleNotification(remoteMessage.notification!!.body)
		}
		
		// Check if message contains a data payload.
		if (remoteMessage.data.size > 0) {
			//Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
			
			try {
				val json = JSONObject(remoteMessage.data.toString())
				handleDataMessage(json)
			} catch (e: Exception) {
				//Log.e(TAG, "Exception: " + e.getMessage());
			}
			
		}
	}
	
	private fun handleNotification(message: String?) {
		if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
			// app is in foreground, broadcast the push message
			val pushNotification = Intent(Config.PUSH_NOTIFICATION)
			pushNotification.putExtra("message", message)
			LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
			
			// play notification sound
			val notificationUtils = NotificationUtils(applicationContext)
			notificationUtils.playNotificationSound()
		} else {
			// If the app is in background, firebase itself handles the notification
		}
	}
	
	private fun handleDataMessage(json: JSONObject) {
		//Log.e(TAG, "push json: " + json.toString());
		
		try {
			val data = json.getJSONObject("data")
			
			val title = data.getString("companyName")
			val message = data.getString("message")
			val isBackground = data.getBoolean("is_background")
			val imageUrl = data.getString("image")
			val timestamp = data.getString("timestamp")
			val payload = data.getJSONObject("payload")
			
			//Log.e(TAG, "companyName: " + companyName);
			//Log.e(TAG, "message: " + message);
			//Log.e(TAG, "isBackground: " + isBackground);
			//Log.e(TAG, "payload: " + payload.toString());
			//Log.e(TAG, "imageUrl: " + imageUrl);
			//Log.e(TAG, "timestamp: " + timestamp);
			
			
			if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
				// app is in foreground, broadcast the push message
				val pushNotification = Intent(Config.PUSH_NOTIFICATION)
				pushNotification.putExtra("message", message)
				LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
				
				// play notification sound
				val notificationUtils = NotificationUtils(applicationContext)
				notificationUtils.playNotificationSound()
			} else {
				// app is in background, show the notification in notification tray
				val resultIntent = Intent(applicationContext, Home::class.java)
				resultIntent.putExtra("message", message)
				
				// check for image attachment
				if (TextUtils.isEmpty(imageUrl)) {
					showNotificationMessage(
						applicationContext,
						title,
						message,
						timestamp,
						resultIntent
					)
				} else {
					// image is present, show notification with image
					showNotificationMessageWithBigImage(
						applicationContext,
						title,
						message,
						timestamp,
						resultIntent,
						imageUrl
					)
				}
			}
		} catch (e: JSONException) {
			//Log.e(TAG, "Json Exception: " + e.getMessage());
		} catch (e: Exception) {
			//Log.e(TAG, "Exception: " + e.getMessage());
		}
		
	}
	
	/**
	 * Showing notification with text only
	 */
	private fun showNotificationMessage(
		context: Context,
		title: String,
		message: String,
		timeStamp: String,
		intent: Intent
	) {
		notificationUtils = NotificationUtils(context)
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent)
	}
	
	/**
	 * Showing notification with text and image
	 */
	private fun showNotificationMessageWithBigImage(
		context: Context,
		title: String,
		message: String,
		timeStamp: String,
		intent: Intent,
		imageUrl: String
	) {
		notificationUtils = NotificationUtils(context)
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
	}
	
	companion object {
		
		private val TAG = MyFirebaseMessagingService::class.java.simpleName
	}
}