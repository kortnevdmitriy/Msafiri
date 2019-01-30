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

package ai.kortnevdmitriy.msafiri.mpesa.app

/**
 * Created  on 6/30/2017.
 */

object Config {
	// global topic to receive app wide push notifications
	const val TOPIC_GLOBAL = "global"
	
	// broadcast receiver intent filters
	const val REGISTRATION_COMPLETE = "registrationComplete"
	const val PUSH_NOTIFICATION = "pushNotification"
	
	// id to handle the notification in the notification tray
	const val NOTIFICATION_ID = 100
	const val NOTIFICATION_ID_BIG_IMAGE = 101
	
	const val SHARED_PREF = "ah_firebase"
}
