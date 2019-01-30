package ai.kortnevdmitriy.msafiri.adapters

import ai.kortnevdmitriy.msafiri.R
import ai.kortnevdmitriy.msafiri.models.TicketDetails
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by kortn on 11/22/2017.
 */

class TicketsAdapter(
	private val ticketDetails: List<TicketDetails>,
	private val listener: OnItemClickListener
) : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>() {
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
		val itemView = LayoutInflater.from(parent.context)
			.inflate(R.layout.tickets_list_row, parent, false)
		
		return TicketsAdapter.TicketsViewHolder(itemView)
	}
	
	override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
		val detailsOfTickets = ticketDetails[position]
		holder.companyName.text = detailsOfTickets.companyName
		holder.vehicleType.text = detailsOfTickets.boardingPoint
		holder.travelRoute.text = detailsOfTickets.travelRoute
		holder.priceInKsh.text = detailsOfTickets.priceInKsh
		holder.numberPlate.text = detailsOfTickets.numberPlate
		holder.seatNumber.text = detailsOfTickets.seatNumber
		holder.bind(detailsOfTickets, listener)
	}
	
	override fun getItemCount(): Int {
		return ticketDetails.size
	}
	
	interface OnItemClickListener {
		fun onItemClick(item: TicketDetails)
	}
	
	class TicketsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		internal var companyName: TextView = view.findViewById(R.id.ticketsCompanyName)
		internal var travelRoute: TextView = view.findViewById(R.id.ticketsTravelRoute)
		internal var vehicleType: TextView = view.findViewById(R.id.ticketsVehicleType)
		internal var priceInKsh: TextView = view.findViewById(R.id.ticketsPriceInKsh)
		internal var numberPlate: TextView = view.findViewById(R.id.ticketsNumberPlate)
		internal var seatNumber: TextView = view.findViewById(R.id.ticketsSeatNumber)
		internal var key: String? = null
		
		internal fun bind(item: TicketDetails, listener: OnItemClickListener) {
			itemView.setOnClickListener { listener.onItemClick(item) }
		}
	}
}
