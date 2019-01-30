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

package ai.kortnevdmitriy.msafiri.mpesa.api

import com.google.gson.annotations.SerializedName

/**
 * Created  on 5/28/2017.
 */

class STKPush(
	@field:SerializedName("BusinessShortCode")
	private val businessShortCode: String,
	
	@field:SerializedName("Password")
	private val password: String,
	
	@field:SerializedName("Timestamp")
	private val timestamp: String,
	
	@field:SerializedName("TransactionType")
	private val transactionType: String,
	
	@field:SerializedName("Amount")
	private val amount: String,
	
	@field:SerializedName("PartyA")
	private val partyA: String,
	
	@field:SerializedName("PartyB")
	private val partyB: String,
	
	@field:SerializedName("PhoneNumber")
	private val phoneNumber: String,
	
	@field:SerializedName("CallBackURL")
	private val callBackURL: String,
	
	@field:SerializedName("AccountReference")
	private val accountReference: String,
	
	@field:SerializedName("TransactionDesc")
	private val transactionDesc: String
)