package ai.kortnevdmitriy.msafiri.models;

/**
 * Created by kortn on 11/22/2017.
 */

public class TicketDetails {
    private String key;
    private String companyName;
    private String numberPlate;
    private String travelRoute;
    private String boardingPoint;
    private String departureTime;
    private String priceInKsh;
    private String vehicleType;
    private String seatNumber;
    private String optionalDescription;
    private String mpesaTransactionCode;

    public TicketDetails() {
    }

    public TicketDetails(String key, String companyName, String numberPlate, String travelRoute, String boardingPoint, String departureTime, String priceInKsh, String vehicleType, String seatNumber, String optionalDescription, String mpesaTransactionCode) {
        this.key = key;
        this.companyName = companyName;
        this.numberPlate = numberPlate;
        this.travelRoute = travelRoute;
        this.boardingPoint = boardingPoint;
        this.departureTime = departureTime;
        this.priceInKsh = priceInKsh;
        this.vehicleType = vehicleType;
        this.seatNumber = seatNumber;
        this.optionalDescription = optionalDescription;
        this.mpesaTransactionCode = mpesaTransactionCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getTravelRoute() {
        return travelRoute;
    }

    public void setTravelRoute(String travelRoute) {
        this.travelRoute = travelRoute;
    }

    public String getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPriceInKsh() {
        return priceInKsh;
    }

    public void setPriceInKsh(String priceInKsh) {
        this.priceInKsh = priceInKsh;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getOptionalDescription() {
        return optionalDescription;
    }

    public void setOptionalDescription(String optionalDescription) {
        this.optionalDescription = optionalDescription;
    }

    public String getMpesaTransactionCode() {
        return mpesaTransactionCode;
    }

    public void setMpesaTransactionCode(String mpesaTransactionCode) {
        this.mpesaTransactionCode = mpesaTransactionCode;
    }
}
