package my.utar.com.billsplittingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private ArrayList<HistoryItem> historyItems;

    public HistoryAdapter(Context context, ArrayList<HistoryItem> historyItems) {
        this.context = context;
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem historyItem = historyItems.get(position);

        holder.textViewDate.setText("Date: " + historyItem.getDate());
        holder.textViewName.setText("Name: " + historyItem.getName());
        holder.textViewCalculationType.setText("Type: " + historyItem.getCalculationType());
        holder.textViewTotalAmount.setText("Total Amount: RM" + String.valueOf(historyItem.getTotalAmount()));
        holder.textViewIndividualAmount.setText("Individual Amount: RM" + String.valueOf(historyItem.getIndividualAmount()));

        // Set the visibility of Percentage and Result TextViews based on the calculation type
        if (historyItem.getCalculationType().equals("Equal Breakdown")) {
            holder.textViewPercentage.setVisibility(View.GONE);
            holder.textViewResult.setVisibility(View.GONE);
        } else if (historyItem.getCalculationType().equals("Custom Individual")) {
            holder.textViewPercentage.setVisibility(View.GONE);
            holder.textViewResult.setVisibility(View.VISIBLE);
            holder.textViewResult.setText("Result: " + String.valueOf(historyItem.getResult()));
        } else {
            holder.textViewPercentage.setVisibility(View.VISIBLE);
            holder.textViewResult.setVisibility(View.VISIBLE);
            holder.textViewPercentage.setText("Percentage: " + String.valueOf(historyItem.getIndividualPercentage()) + "%");
            holder.textViewResult.setText("Result: " + String.valueOf(historyItem.getResult()));
        }

        // Set other fields as needed
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate, textViewName, textViewPercentage,
                textViewCalculationType, textViewTotalAmount,
                textViewIndividualAmount, textViewResult;

        // Add other TextViews for other fields

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCalculationType = itemView.findViewById(R.id.textViewCalculationType);
            textViewTotalAmount = itemView.findViewById(R.id.textViewTotalBill);
            textViewIndividualAmount = itemView.findViewById(R.id.textViewIndividualAmount);
            textViewPercentage = itemView.findViewById(R.id.textViewIndividualPercentage);
            textViewResult = itemView.findViewById(R.id.textViewResult);
            // Initialize other TextViews for other fields
        }
    }
}
