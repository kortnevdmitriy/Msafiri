package ai.kortnevdmitriy.msafiri.models

/**
 * Created by kortn on 10/3/2017.
 */

class VehicleDetails {
	var key: String? = null
	var companyName: String? = null
	var numberPlate: String? = null
	var travelRoute: String? = null
	var boardingPoint: String? = null
	var departureTime: String? = null
	var priceInKsh: String? = null
	var vehicleType: String? = null
	var numberOfSeats: String? = null
	var optionalDescription: String? = null
	var img_url: String? = null
	
	constructor()
	
	constructor(
		key: String,
		companyName: String,
		numberPlate: String,
		travelRoute: String,
		boardingPoint: String,
		departureTime: String,
		priceInKsh: String,
		vehicleType: String,
		numberOfSeats: String,
		optionalDescription: String,
		img_url: String
	) {
		this.key = key
		this.companyName = companyName
		this.numberPlate = numberPlate
		this.travelRoute = travelRoute
		this.boardingPoint = boardingPoint
		this.departureTime = departureTime
		this.priceInKsh = priceInKsh
		this.vehicleType = vehicleType
		this.numberOfSeats = numberOfSeats
		this.optionalDescription = optionalDescription
		this.img_url = img_url
	}
}
