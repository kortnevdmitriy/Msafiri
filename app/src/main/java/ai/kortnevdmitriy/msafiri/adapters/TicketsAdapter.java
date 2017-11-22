package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

/**
 * Created by kortn on 11/22/2017.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {
    private List<VehicleDetails> vehicleDetails;
    private OnItemClickListener listener;

    public TicketsAdapter(List<VehicleDetails> vehicleDetails, OnItemClickListener listener) {
        this.vehicleDetails = vehicleDetails;
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
        VehicleDetails detailsOfVehicles = vehicleDetails.get(position);
        holder.companyName.setText(detailsOfVehicles.getCompanyName());
        holder.vehicleType.setText(detailsOfVehicles.getVehicleType());
        holder.travelRoute.setText(detailsOfVehicles.getTravelRoute());
        holder.priceInKsh.setText(detailsOfVehicles.getPriceInKsh());
        holder.numberPlate.setText(detailsOfVehicles.getNumberPlate());
        holder.key = detailsOfVehicles.getKey();
        holder.bind(detailsOfVehicles, listener);
    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VehicleDetails item);
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, travelRoute, vehicleType, priceInKsh, numberPlate;
        String key;

        public TicketsViewHolder(View view) {
            super(view);
            companyName = view.findViewById(R.id.ticketsCompanyName);
            vehicleType = view.findViewById(R.id.ticketsVehicleType);
            travelRoute = view.findViewById(R.id.ticketsTravelRoute);
            priceInKsh = view.findViewById(R.id.ticketsPriceInKsh);
            numberPlate = view.findViewById(R.id.ticketsNumberPlate);
        }

        void bind(final VehicleDetails item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
