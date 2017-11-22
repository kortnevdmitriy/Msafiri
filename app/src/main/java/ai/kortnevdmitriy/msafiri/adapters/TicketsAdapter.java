package ai.kortnevdmitriy.msafiri.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kortn on 11/22/2017.
 */

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {

    private OnItemClickListener listener;

    @Override
    public TicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TicketsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnItemClickListener {
        // TODO
    }

    public class TicketsViewHolder extends RecyclerView.ViewHolder {
        public TicketsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
