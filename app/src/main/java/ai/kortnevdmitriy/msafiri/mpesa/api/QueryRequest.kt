package ai.kortnevdmitriy.msafiri.mpesa.api

import com.google.gson.annotations.SerializedName

class QueryRequest(
	@field:SerializedName("BusinessShortCode")
	var businessShortCode: String?,
	
	@field:SerializedName("Password")
	var password: String?,
	
	@field:SerializedName("Timestamp")
	var timestamp: String?,
	
	@field:SerializedName("CheckoutRequestID")
	var checkoutRequestID: String?
)
