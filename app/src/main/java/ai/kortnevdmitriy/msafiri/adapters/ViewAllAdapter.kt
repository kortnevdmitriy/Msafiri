package ai.kortnevdmitriy.msafiri.adapters

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.VehicleDetails
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by kortn on 10/4/2017.
 */

class ViewAllAdapter(
	private val vehicleDetails: List<VehicleDetails>,
	private val listener: OnItemClickListener
) : RecyclerView.Adapter<ViewAllAdapter.VehicleRegViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleRegViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.viewall_list_row, parent, false)
		
		return VehicleRegViewHolder(itemView)
	}
	
	override fun onBindViewHolder(holder: VehicleRegViewHolder, position: Int) {
		val detailsOfVehicles = vehicleDetails[position]
		holder.companyName.text = detailsOfVehicles.companyName
		holder.vehicleType.text = detailsOfVehicles.vehicleType
		holder.travelRoute.text = detailsOfVehicles.travelRoute
		holder.priceInKsh.text = detailsOfVehicles.priceInKsh
		holder.numberPlate.text = detailsOfVehicles.numberPlate
		holder.departureTime.text = detailsOfVehicles.departureTime
		holder.key = detailsOfVehicles.key
		holder.bind(detailsOfVehicles, listener)
	}
	
	override fun getItemCount(): Int {
		return vehicleDetails.size
	}
	
	interface OnItemClickListener {
		fun onItemClick(item: VehicleDetails)
	}
	
	class VehicleRegViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		var companyName: TextView = view.findViewById(R.id.tvCompanyName)
		var travelRoute: TextView = view.findViewById(R.id.tvSearchTravelRoute)
		var vehicleType: TextView = view.findViewById(R.id.tvSearchVehicleType)
		var priceInKsh: TextView = view.findViewById(R.id.tvSearchPriceInKsh)
		var numberPlate: TextView = view.findViewById(R.id.tvNumberPlate)
		var departureTime: TextView = view.findViewById(R.id.tvDepartureTime)
		var key: String? = null
		
		fun bind(item: VehicleDetails, listener: OnItemClickListener) {
			itemView.setOnClickListener { listener.onItemClick(item) }
		}
	}
	
}
