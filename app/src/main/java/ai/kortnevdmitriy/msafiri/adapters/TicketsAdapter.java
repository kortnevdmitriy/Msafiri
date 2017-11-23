package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.TicketDetails;

/**
 * Created by kortn on 11/22/2017.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {
    private List<TicketDetails> ticketDetails;
    private OnItemClickListener listener;

    public TicketsAdapter(List<TicketDetails> ticketDetails, OnItemClickListener listener) {
        this.ticketDetails = ticketDetails;
        this.listener = listener;
    }

    @Override
    public TicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tickets_list_row, parent, false);

        return new TicketsAdapter.TicketsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TicketsViewHolder holder, int position) {
        TicketDetails detailsOfTickets = ticketDetails.get(position);
        holder.companyName.setText(detailsOfTickets.getCompanyName());
        holder.vehicleType.setText(detailsOfTickets.getBoardingPoint());
        holder.travelRoute.setText(detailsOfTickets.getTravelRoute());
        holder.priceInKsh.setText(detailsOfTickets.getPriceInKsh());
        holder.numberPlate.setText(detailsOfTickets.getNumberPlate());
        holder.seatNumber.setText(detailsOfTickets.getSeatNumber());
        holder.bind(detailsOfTickets, listener);
    }

    @Override
    public int getItemCount() {
        return ticketDetails.size();
    }

    public interface OnItemClickListener {
        void onItemClick(TicketDetails item);
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, travelRoute, vehicleType, priceInKsh, numberPlate, seatNumber;
        String key;

        public TicketsViewHolder(View view) {
            super(view);
            companyName = view.findViewById(R.id.ticketsCompanyName);
            vehicleType = view.findViewById(R.id.ticketsVehicleType);
            travelRoute = view.findViewById(R.id.ticketsTravelRoute);
            priceInKsh = view.findViewById(R.id.ticketsPriceInKsh);
            numberPlate = view.findViewById(R.id.ticketsNumberPlate);
            seatNumber = view.findViewById(R.id.ticketsSeatNumber);
        }

        void bind(final TicketDetails item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
