package ai.kortnevdmitriy.msafiri.models

/**
 * Created by kortn on 11/22/2017.
 */

class TicketDetails {
	var key: String? = null
	var companyName: String? = null
	var numberPlate: String? = null
	var travelRoute: String? = null
	var boardingPoint: String? = null
	var departureTime: String? = null
	var priceInKsh: String? = null
	var vehicleType: String? = null
	var seatNumber: String? = null
	var optionalDescription: String? = null
	var mpesaTransactionCode: String? = null
	
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
		seatNumber: String,
		optionalDescription: String,
		mpesaTransactionCode: String
	) {
		this.key = key
		this.companyName = companyName
		this.numberPlate = numberPlate
		this.travelRoute = travelRoute
		this.boardingPoint = boardingPoint
		this.departureTime = departureTime
		this.priceInKsh = priceInKsh
		this.vehicleType = vehicleType
		this.seatNumber = seatNumber
		this.optionalDescription = optionalDescription
		this.mpesaTransactionCode = mpesaTransactionCode
	}
}
