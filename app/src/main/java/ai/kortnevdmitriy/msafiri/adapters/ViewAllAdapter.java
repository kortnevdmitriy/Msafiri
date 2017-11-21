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
 * Created by kortn on 10/4/2017.
 */

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.VehicleRegViewHolder> {

    private List<VehicleDetails> vehicleDetails;


    public ViewAllAdapter(List<VehicleDetails> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
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

    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    class VehicleRegViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, travelRoute, vehicleType, priceInKsh, numberPlate;

        VehicleRegViewHolder(View view) {
            super(view);
            companyName = view.findViewById(R.id.tvCompanyName);
            vehicleType = view.findViewById(R.id.tvSearchVehicleType);
            travelRoute = view.findViewById(R.id.tvSearchTravelRoute);
            priceInKsh = view.findViewById(R.id.tvSearchPriceInKsh);
            numberPlate = view.findViewById(R.id.tvNumberPlate);

        }
    }

}
