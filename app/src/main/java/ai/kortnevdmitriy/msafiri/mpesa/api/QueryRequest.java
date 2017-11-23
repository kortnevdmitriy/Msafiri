package ai.kortnevdmitriy.msafiri.mpesa.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kortn on 11/23/2017.
 */

public class QueryRequest {
    @SerializedName("BusinessShortCode")
    private String businessShortCode;
    @SerializedName("Password")
    private String password;
    @SerializedName("Timestamp")
    private String timestamp;
    @SerializedName("CheckoutRequestID")
    private String checkoutRequestID;

    public QueryRequest(String businessShortCode, String password, String timestamp, String checkoutRequestID) {
        this.businessShortCode = businessShortCode;
        this.password = password;
        this.timestamp = timestamp;
        this.checkoutRequestID = checkoutRequestID;
    }
}
