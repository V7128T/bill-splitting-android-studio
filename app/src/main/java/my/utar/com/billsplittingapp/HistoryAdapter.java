package my.utar.com.billsplittingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
        holder.textViewDate.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
        holder.textViewDate.setTextColor(ContextCompat.getColor(context, R.color.white));

        holder.textViewName.setText("Name: " + historyItem.getName());
        holder.textViewName.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
        holder.textViewName.setTextColor(ContextCompat.getColor(context, R.color.white));

        holder.textViewCalculationType.setText("Type: " + historyItem.getCalculationType());
        holder.textViewCalculationType.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
        holder.textViewCalculationType.setTextColor(ContextCompat.getColor(context, R.color.terminalGreen_lowAlpha));

        holder.textViewTotalAmount.setText("Total Amount: RM" + String.valueOf(historyItem.getTotalAmount()));
        holder.textViewTotalAmount.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
        holder.textViewTotalAmount.setTextColor(ContextCompat.getColor(context, R.color.terminalGreen));

        holder.textViewIndividualAmount.setText("Individual Amount: RM" + String.valueOf(historyItem.getIndividualAmount()));
        holder.textViewIndividualAmount.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
        holder.textViewIndividualAmount.setTextColor(ContextCompat.getColor(context, R.color.terminalGreen_lowAlpha));

        // Set the visibility of Percentage and Result TextViews based on the calculation type
        if (historyItem.getCalculationType().equals("Equal Breakdown")) {
            holder.textViewPercentage.setVisibility(View.GONE);
            holder.textViewResult.setVisibility(View.GONE);
            holder.textViewCalculationType.setTextColor(ContextCompat.getColor(context, R.color.redCherry));
        } else if (historyItem.getCalculationType().equals("Custom Individual")) {
            holder.textViewPercentage.setVisibility(View.GONE);
            holder.textViewResult.setVisibility(View.GONE);
            holder.textViewCalculationType.setTextColor(ContextCompat.getColor(context, R.color.purple_700));
            //holder.textViewResult.setText("Result: " + String.valueOf(historyItem.getResult()));
        } else {
            holder.textViewPercentage.setVisibility(View.VISIBLE);
            holder.textViewResult.setVisibility(View.GONE);
            holder.textViewPercentage.setText("Percentage: " + String.valueOf(historyItem.getIndividualPercentage()) + "%");
            holder.textViewPercentage.setTypeface(ResourcesCompat.getFont(context, R.font.proto_mono_light));
            holder.textViewPercentage.setTextColor(ContextCompat.getColor(context, R.color.terminalGreen_lowAlpha));
            holder.textViewCalculationType.setTextColor(ContextCompat.getColor(context, R.color.purpleBright));
            //holder.textViewResult.setText("Result: " + String.valueOf(historyItem.getResult()));
        }

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

    public void updateList(ArrayList<HistoryItem> filteredItems) {
        historyItems.clear();
        historyItems.addAll(filteredItems);
        notifyDataSetChanged();
    }
}
