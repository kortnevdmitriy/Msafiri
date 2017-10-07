package ai.kortnevdmitriy.msafiri.mpesa.api.services;

import ai.kortnevdmitriy.msafiri.mpesa.api.STKPush;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by kortn on 10/6/2017.
 */

public interface STKPushService {
    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush);

    @GET("jobs/pending")
    Call<STKPush> getTasks();
}
