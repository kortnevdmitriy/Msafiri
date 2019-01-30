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

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created  on 5/28/2017.
 */

object RetrofitClient {
	
	private var retrofit: Retrofit? = null
	private val builder = OkHttpClient().newBuilder()
	
	internal fun getClient(baseUrl: String, token: String): Retrofit {
		if (retrofit == null) {
			val client = OkHttpClient.Builder().addInterceptor { chain ->
				val newRequest = chain.request().newBuilder()
					.addHeader("Authorization", "Bearer $token")
					.build()
				chain.proceed(newRequest)
			}.build()
			
			//Log.e("header token", token);
			
			retrofit = Retrofit.Builder()
				.client(client)
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
		}
		return retrofit!!
	}
}