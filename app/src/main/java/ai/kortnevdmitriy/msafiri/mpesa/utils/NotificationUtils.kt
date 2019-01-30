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

package ai.kortnevdmitriy.msafiri.mpesa.utils

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.mpesa.app.Config
import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.ParseException
import android.net.Uri
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created  on 6/30/2017.
 */

class NotificationUtils(private val mContext: Context) {
	
	@JvmOverloads
	fun showNotificationMessage(
		title: String,
		message: String,
		timeStamp: String,
		intent: Intent,
		imageUrl: String? = null
	) {
		// Check for empty push message
		if (TextUtils.isEmpty(message))
			return
		
		
		// notification icon
		
		intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
		val resultPendingIntent = PendingIntent.getActivity(
			mContext,
			0,
			intent,
			PendingIntent.FLAG_CANCEL_CURRENT
		)
		
		val mBuilder = NotificationCompat.Builder(
			mContext
		)
		
		val alarmSound = Uri.parse(
			ContentResolver.SCHEME_ANDROID_RESOURCE
					+ "://" + mContext.packageName + "/raw/notification"
		)
		
		if (!TextUtils.isEmpty(imageUrl)) {
			
			if (imageUrl != null && imageUrl.length > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
				
				val bitmap = getBitmapFromURL(imageUrl)
				
				if (bitmap != null) {
					showBigNotification(
						bitmap,
						mBuilder,
						title,
						message,
						timeStamp,
						resultPendingIntent,
						alarmSound
					)
				} else {
					showSmallNotification(
						mBuilder,
						title,
						message,
						timeStamp,
						resultPendingIntent,
						alarmSound
					)
				}
			}
		} else {
			showSmallNotification(
				mBuilder,
				title,
				message,
				timeStamp,
				resultPendingIntent,
				alarmSound
			)
			playNotificationSound()
		}
	}
	
	private fun showSmallNotification(
		mBuilder: NotificationCompat.Builder,
		title: String,
		message: String,
		timeStamp: String,
		resultPendingIntent: PendingIntent,
		alarmSound: Uri
	) {
		
		val inboxStyle = NotificationCompat.InboxStyle()
		
		inboxStyle.addLine(message)
		
		val notification: Notification
		notification = mBuilder.setSmallIcon(2131689472).setTicker(title).setWhen(0)
			.setAutoCancel(true)
			.setContentTitle(title)
			.setContentIntent(resultPendingIntent)
			.setSound(alarmSound)
			.setStyle(inboxStyle)
			.setWhen(getTimeMilliSec(timeStamp))
			.setSmallIcon(R.mipmap.ic_launcher)
			.setLargeIcon(BitmapFactory.decodeResource(mContext.resources, 2131689472))
			.setContentText(message)
			.build()
		
		val notificationManager =
			mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		Objects.requireNonNull(notificationManager).notify(Config.NOTIFICATION_ID, notification)
	}
	
	private fun showBigNotification(
		bitmap: Bitmap,
		mBuilder: NotificationCompat.Builder,
		title: String,
		message: String,
		timeStamp: String,
		resultPendingIntent: PendingIntent,
		alarmSound: Uri
	) {
		val bigPictureStyle = NotificationCompat.BigPictureStyle()
		bigPictureStyle.setBigContentTitle(title)
		bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
		bigPictureStyle.bigPicture(bitmap)
		val notification: Notification
		notification = mBuilder.setSmallIcon(2131689472).setTicker(title).setWhen(0)
			.setAutoCancel(true)
			.setContentTitle(title)
			.setContentIntent(resultPendingIntent)
			.setSound(alarmSound)
			.setStyle(bigPictureStyle)
			.setWhen(getTimeMilliSec(timeStamp))
			.setSmallIcon(R.mipmap.ic_launcher)
			.setLargeIcon(BitmapFactory.decodeResource(mContext.resources, 2131689472))
			.setContentText(message)
			.build()
		
		val notificationManager =
			mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		Objects.requireNonNull(notificationManager)
			.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification)
	}
	
	/**
	 * Downloading push notification image before displaying it in
	 * the notification tray
	 */
	private fun getBitmapFromURL(strURL: String): Bitmap? {
		return try {
			val url = URL(strURL)
			val connection = url.openConnection() as HttpURLConnection
			connection.doInput = true
			connection.connect()
			val input = connection.inputStream
			BitmapFactory.decodeStream(input)
		} catch (e: IOException) {
			e.printStackTrace()
			null
		}
		
	}
	
	// Playing notification sound
	fun playNotificationSound() {
		try {
			val alarmSound = Uri.parse(
				ContentResolver.SCHEME_ANDROID_RESOURCE
						+ "://" + mContext.packageName + "/raw/notification"
			)
			val r = RingtoneManager.getRingtone(mContext, alarmSound)
			r.play()
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
	}
	
	companion object {
		private val TAG = NotificationUtils::class.java.simpleName
		
		/**
		 * Method checks if the app is in background or not
		 */
		fun isAppIsInBackground(context: Context): Boolean {
			var isInBackground = true
			val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
			val runningProcesses = am.runningAppProcesses
			for (processInfo in runningProcesses) {
				if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					for (activeProcess in processInfo.pkgList) {
						if (activeProcess == context.packageName) {
							isInBackground = false
						}
					}
				}
			}
			
			return isInBackground
		}
		
		// Clears notification tray messages
		fun clearNotifications(context: Context) {
			val notificationManager =
				context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.cancelAll()
		}
		
		private fun getTimeMilliSec(timeStamp: String): Long {
			val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			try {
				val date = format.parse(timeStamp)
				return date.time
			} catch (e: ParseException) {
				e.printStackTrace()
			} catch (e: java.text.ParseException) {
				e.printStackTrace()
			}
			
			return 0
		}
	}
}
