package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.entities.VehicleDetails;

/**
 * Created by kortn on 10/29/2017.
 */

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.MyTicketsViewHolder> {
    private List<VehicleDetails> vehicleDetails;
    private VehicleDetails detailsOfVehicles;

    public MyTicketsAdapter(List<VehicleDetails> vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    @Override
    public MyTicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytickets_list_row, parent, false);

        return new MyTicketsAdapter.MyTicketsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTicketsViewHolder holder, int position) {
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

    public class MyTicketsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, price, regdet;
        public ImageView buttonBook;

        public MyTicketsViewHolder(View view) {
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
