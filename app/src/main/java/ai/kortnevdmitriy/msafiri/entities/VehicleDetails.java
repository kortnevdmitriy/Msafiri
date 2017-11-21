package ai.kortnevdmitriy.msafiri.entities;

/**
 * Created by kortn on 10/3/2017.
 */

public class VehicleDetails {
    private String key;
    private String companyName;
    private String numberPlate;
    private String travelRoute;
    private String boardingPoint;
    private String departureTime;
    private String priceInKsh;
    private String vehicleType;
    private String numberOfSeats;
    private String optionalDescription;
    private String imageUrl;

    public VehicleDetails() {
    }

    public VehicleDetails(String key, String companyName, String numberPlate, String travelRoute, String boardingPoint, String departureTime, String priceInKsh, String vehicleType, String numberOfSeats, String optionalDescription, String imageUrl) {
        this.key = key;
        this.companyName = companyName;
        this.numberPlate = numberPlate;
        this.travelRoute = travelRoute;
        this.boardingPoint = boardingPoint;
        this.departureTime = departureTime;
        this.priceInKsh = priceInKsh;
        this.vehicleType = vehicleType;
        this.numberOfSeats = numberOfSeats;
        this.optionalDescription = optionalDescription;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
