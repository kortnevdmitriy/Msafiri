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
 * Created by kortn on 10/12/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<VehicleDetails> vehicleDetails;
    private OnItemClickListener listener;

    public SearchAdapter(List<VehicleDetails> vehicleDetails, OnItemClickListener listener) {
        this.vehicleDetails = vehicleDetails;
        this.listener = listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new SearchAdapter.SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        VehicleDetails detailsOfVehicles = vehicleDetails.get(position);
        holder.searchCompanyName.setText(detailsOfVehicles.getCompanyName());
        holder.searchVehicleType.setText(detailsOfVehicles.getVehicleType());
        holder.searchTravelRoute.setText(detailsOfVehicles.getTravelRoute());
        holder.searchPriceInKsh.setText(detailsOfVehicles.getPriceInKsh());
        holder.searchNumberPlate.setText(detailsOfVehicles.getNumberPlate());
        holder.searchDepartureTime.setText(detailsOfVehicles.getDepartureTime());
        holder.bind(detailsOfVehicles, listener);
    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    public interface OnItemClickListener {
        void onItemClick(VehicleDetails item);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView searchCompanyName, searchTravelRoute, searchVehicleType, searchPriceInKsh, searchNumberPlate, searchDepartureTime;

        SearchViewHolder(View view) {
            super(view);
            searchCompanyName = view.findViewById(R.id.tvSearchCompanyName);
            searchVehicleType = view.findViewById(R.id.tvSearchVehicleType);
            searchTravelRoute = view.findViewById(R.id.tvSearchTravelRoute);
            searchPriceInKsh = view.findViewById(R.id.tvSearchPriceInKsh);
            searchNumberPlate = view.findViewById(R.id.tvSearchNumberPlate);
            searchDepartureTime = view.findViewById(R.id.tvSearchDepartureTime);

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
