package ai.kortnevdmitriy.msafiri.mpesa.api.services;

import ai.kortnevdmitriy.msafiri.mpesa.api.QueryRequest;
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

    @POST("mpesa/stkpushquery/v1/query")
    Call<QueryRequest> sendRequest(@Body QueryRequest queryRequest);

    @GET("jobs/pending")
    Call<STKPush> getTasks();
}
