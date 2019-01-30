package ai.kortnevdmitriy.msafiri.mpesa.api.services

import ai.kortnevdmitriy.msafiri.mpesa.api.QueryRequest
import ai.kortnevdmitriy.msafiri.mpesa.api.STKPush
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by kortn on 10/6/2017.
 */

interface STKPushService {
	
	@get:GET("jobs/pending")
	val tasks: Call<STKPush>
	
	@POST("mpesa/stkpush/v1/processrequest")
	fun sendPush(@Body stkPush: STKPush): Call<STKPush>
	
	@POST("mpesa/stkpushquery/v1/query")
	fun sendRequest(@Body queryRequest: QueryRequest): Call<QueryRequest>
}
