package ai.kortnevdmitriy.msafiri.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.models.VehicleDetails;

/**
 * Created by kortn on 10/4/2017.
 */

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.VehicleRegViewHolder> {

    private List<VehicleDetails> vehicleDetails;
    private OnItemClickListener listener;

    public ViewAllAdapter(List<VehicleDetails> vehicleDetails, OnItemClickListener listener) {
        this.vehicleDetails = vehicleDetails;
        this.listener = listener;
    }

    @Override
    public VehicleRegViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewall_list_row, parent, false);

        return new VehicleRegViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleRegViewHolder holder, int position) {
        VehicleDetails detailsOfVehicles = vehicleDetails.get(position);
        holder.companyName.setText(detailsOfVehicles.getCompanyName());
        holder.vehicleType.setText(detailsOfVehicles.getVehicleType());
        holder.travelRoute.setText(detailsOfVehicles.getTravelRoute());
        holder.priceInKsh.setText(detailsOfVehicles.getPriceInKsh());
        holder.numberPlate.setText(detailsOfVehicles.getNumberPlate());
        holder.departureTime.setText(detailsOfVehicles.getDepartureTime());
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

    class VehicleRegViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, travelRoute, vehicleType, priceInKsh, numberPlate, departureTime;
        String key;

        VehicleRegViewHolder(View view) {
            super(view);
            companyName = view.findViewById(R.id.tvCompanyName);
            vehicleType = view.findViewById(R.id.tvSearchVehicleType);
            travelRoute = view.findViewById(R.id.tvSearchTravelRoute);
            priceInKsh = view.findViewById(R.id.tvSearchPriceInKsh);
            numberPlate = view.findViewById(R.id.tvNumberPlate);
            departureTime = view.findViewById(R.id.tvDepartureTime);

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
