package ai.kortnevdmitriy.msafiri.entities;

/**
 * Created by kortn on 11/22/2017.
 */

public class TicketDetails {
    private String companyName;
    private String numberPlate;
    private String travelRoute;
    private String boardingPoint;
    private String departureTime;
    private String priceInKsh;
    private String vehicleType;
    private String numberOfSeats;
    private String optionalDescription;
    private String mpesaTransactionCode;

    public TicketDetails() {
    }

    public TicketDetails(String companyName, String numberPlate, String travelRoute, String boardingPoint, String departureTime, String priceInKsh, String vehicleType, String numberOfSeats, String optionalDescription, String mpesaTransactionCode) {
        this.companyName = companyName;
        this.numberPlate = numberPlate;
        this.travelRoute = travelRoute;
        this.boardingPoint = boardingPoint;
        this.departureTime = departureTime;
        this.priceInKsh = priceInKsh;
        this.vehicleType = vehicleType;
        this.numberOfSeats = numberOfSeats;
        this.optionalDescription = optionalDescription;
        this.mpesaTransactionCode = mpesaTransactionCode;
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

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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
