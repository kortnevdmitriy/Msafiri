package ai.kortnevdmitriy.msafiri.adapters

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.VehicleDetails
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by kortn on 10/12/2017.
 */

class SearchAdapter(
	private val vehicleDetails: List<VehicleDetails>,
	private val listener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.search_list_row, parent, false)
		
		return SearchAdapter.SearchViewHolder(itemView)
	}
	
	override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
		val detailsOfVehicles = vehicleDetails[position]
		holder.searchCompanyName.text = detailsOfVehicles.companyName
		holder.searchVehicleType.text = detailsOfVehicles.vehicleType
		holder.searchTravelRoute.text = detailsOfVehicles.travelRoute
		holder.searchPriceInKsh.text = detailsOfVehicles.priceInKsh
		holder.searchNumberPlate.text = detailsOfVehicles.numberPlate
		holder.searchDepartureTime.text = detailsOfVehicles.departureTime
		holder.bind(detailsOfVehicles, listener)
	}
	
	override fun getItemCount(): Int {
		return vehicleDetails.size
	}
	
	interface OnItemClickListener {
		fun onItemClick(item: VehicleDetails)
	}
	
	class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		var searchCompanyName: TextView = view.findViewById(R.id.tvSearchCompanyName)
		var searchTravelRoute: TextView = view.findViewById(R.id.tvSearchTravelRoute)
		var searchVehicleType: TextView = view.findViewById(R.id.tvSearchVehicleType)
		var searchPriceInKsh: TextView = view.findViewById(R.id.tvSearchPriceInKsh)
		var searchNumberPlate: TextView = view.findViewById(R.id.tvSearchNumberPlate)
		var searchDepartureTime: TextView = view.findViewById(R.id.tvSearchDepartureTime)
		
		fun bind(item: VehicleDetails, listener: OnItemClickListener) {
			itemView.setOnClickListener { listener.onItemClick(item) }
		}
	}
}
