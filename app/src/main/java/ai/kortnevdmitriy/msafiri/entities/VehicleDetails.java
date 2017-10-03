package ai.kortnevdmitriy.msafiri.entities;

/**
 * Created by kortn on 10/3/2017.
 */

public class VehicleDetails {
    private String vehicleType;
    private String registrationDetails;
    private String companyName;
    private Integer numberOfSeats;
    private String travelRoute;
    private Integer priceOfTravel;

    public VehicleDetails() {
    }

    public VehicleDetails(String vehicleType, String registrationDetails, String companyName, Integer numberOfSeats, String travelRoute, Integer priceOfTravel) {
        this.vehicleType = vehicleType;
        this.registrationDetails = registrationDetails;
        this.companyName = companyName;
        this.numberOfSeats = numberOfSeats;
        this.travelRoute = travelRoute;
        this.priceOfTravel = priceOfTravel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRegistrationDetails() {
        return registrationDetails;
    }

    public void setRegistrationDetails(String registrationDetails) {
        this.registrationDetails = registrationDetails;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getTravelRoute() {
        return travelRoute;
    }

    public void setTravelRoute(String travelRoute) {
        this.travelRoute = travelRoute;
    }

    public Integer getPriceOfTravel() {
        return priceOfTravel;
    }

    public void setPriceOfTravel(Integer priceOfTravel) {
        this.priceOfTravel = priceOfTravel;
    }
}
