package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;


/**
 * Created by kortn on 10/12/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<VehicleDetails> vehicleDetails;
    private VehicleDetails detailsOfVehicles;

    public SearchAdapter(List<VehicleDetails> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_row, parent, false);

        return new SearchAdapter.SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        detailsOfVehicles = vehicleDetails.get(position);
        holder.title.setText(detailsOfVehicles.getCompanyName());
        holder.genre.setText(detailsOfVehicles.getVehicleType());
        holder.year.setText(detailsOfVehicles.getTravelRoute());
        holder.price.setText(detailsOfVehicles.getPriceInKsh());
        holder.regdet.setText(detailsOfVehicles.getNumberPlate());
    }

    @Override
    public int getItemCount() {
        return vehicleDetails.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, price, regdet;
        public Button buttonBook;

        public SearchViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            genre = view.findViewById(R.id.genre);
            year = view.findViewById(R.id.year);
            price = view.findViewById(R.id.price);
            regdet = view.findViewById(R.id.regdet);
            buttonBook = view.findViewById(R.id.buttonBook);

        }
    }
}
