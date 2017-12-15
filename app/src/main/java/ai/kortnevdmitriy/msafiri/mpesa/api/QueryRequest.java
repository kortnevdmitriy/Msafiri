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

    public String getBusinessShortCode() {
        return businessShortCode;
    }

    public void setBusinessShortCode(String businessShortCode) {
        this.businessShortCode = businessShortCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }
}
