package my.utar.com.billsplittingapp;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ViewHistory extends AppCompatActivity implements DeleteConfirmationDialog.DeleteConfirmationListener {

    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryItem> originalHistoryItems;
    FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        // Initialize the FloatingActionButton
        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        ArrayList<HistoryItem> historyItems1 = loadAllHistoryItems();
        // Make a copy of original history items
        originalHistoryItems = new ArrayList<>(historyItems1);

        //Create a return button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("View History");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load and display all the history items in RecyclerView
        ArrayList<HistoryItem> historyItems = loadAllHistoryItems();
        historyAdapter = new HistoryAdapter(this, historyItems);
        recyclerView.setAdapter(historyAdapter);


        // Add item decorator to display lines between history items
        int dividerHeightInPixels = 2;
        ItemDecorator itemDecorator = new ItemDecorator(getResources().getColor(R.color.colorTheme), dividerHeightInPixels);
        recyclerView.addItemDecoration(itemDecorator);

    }

    private ArrayList<HistoryItem> loadAllHistoryItems() {
        ArrayList<HistoryItem> allHistoryItems = new ArrayList<>();

        // Load equalBreakdown history items
        ArrayList<HistoryItem> equalHistoryItems = loadHistoryItems("equal_breakdown");
        allHistoryItems.addAll(equalHistoryItems);

        // Load percentageBreakdown history items
        ArrayList<HistoryItem> percentageHistoryItems = loadHistoryItems("percentage_breakdown");
        allHistoryItems.addAll(percentageHistoryItems);

        // Load individualBreakdown history items
        ArrayList<HistoryItem> individualHistoryItems = loadHistoryItems("individual_breakdown");
        allHistoryItems.addAll(individualHistoryItems);

        // Retrieve and set results for custom_individual
/*        ArrayList<Result> customIndividualResults = loadResults("custom_individual");
        System.out.println("Results:" + customIndividualResults);*/
        /*for (int i = 0; i < Math.min(individualHistoryItems.size(), customIndividualResults.size()); i++) {
            individualHistoryItems.get(i).setResult(customIndividualResults.get(i).getResult());
        }*/

        // Load combineBreakdown history items
        ArrayList<HistoryItem> combineHistoryItems = loadHistoryItems("combine_breakdown");
        allHistoryItems.addAll(combineHistoryItems);

        // Retrieve and set results for combine_breakdown
/*        ArrayList<Result> combineResults = loadResults("combine_breakdown");
        System.out.println("Results:" + combineResults);*/
        /*for (int i = 0; i < Math.min(combineHistoryItems.size(), combineResults.size()); i++) {
            combineHistoryItems.get(i).setResult(combineResults.get(i).getResult());
        }*/

        return allHistoryItems;
    }

    private ArrayList<HistoryItem> loadHistoryItems(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        Gson gson = new Gson();

        String historyItemsJson = sharedPreferences.getString("historyItems", null);
        String historyItemsDate = sharedPreferences.getString("date", null);

        Type type = new TypeToken<ArrayList<HistoryItem>>() {
        }.getType();
        ArrayList<HistoryItem> historyItems = gson.fromJson(historyItemsJson, type);

        if (historyItems == null) {
            historyItems = new ArrayList<>();
        }

        // Set the date for each HistoryItem
        for (HistoryItem item : historyItems) {
            item.setDate(historyItemsDate);
        }

        return historyItems;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterBySearchQuery(String query) {
        ArrayList<HistoryItem> filteredList = new ArrayList<>();
        for (HistoryItem item : originalHistoryItems) {
            if (item.getCalculationType().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDate().toLowerCase().contains(query.toLowerCase()) ||
                    String.valueOf(item.getTotalAmount()).contains(query)) {
                filteredList.add(item);
            }
        }
        historyAdapter.updateList(filteredList); // Add this line to update the adapter
    }

    private void showDeleteConfirmationDialog() {
        DeleteConfirmationDialog confirmationDialog = new DeleteConfirmationDialog();
        confirmationDialog.show(getSupportFragmentManager(), "custom_dialog_message");
    }

    @Override
    public void onDeleteConfirmed() {
        clearAllHistory();
    }

    private void clearAllHistory() {

        if (originalHistoryItems.isEmpty()) {
            Toast.makeText(this, "No history to delete.", Toast.LENGTH_SHORT).show();
        } else {
            originalHistoryItems.clear();
            historyAdapter.updateList(new ArrayList<HistoryItem>()); // Update the adapter with an empty list

            // Clear equal_breakdown history
            clearSharedPreferences("equal_breakdown");

            // Clear percentage_breakdown history
            clearSharedPreferences("percentage_breakdown");

            // Clear individual_breakdown history
            clearSharedPreferences("individual_breakdown");

            // Clear combine_breakdown history
            clearSharedPreferences("combine_breakdown");


            Toast.makeText(this, "All history cleared.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSharedPreferences(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear data by applying editor changes
        editor.clear().apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_history, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up search query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Apply filter when the user click the submit button
                filterBySearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // Apply filter based on the new query
                filterBySearchQuery(query);

                // Check if the text becomes empty
                if (query.isEmpty()) {
                    // Reset the RecyclerView to its original state
                    historyAdapter.updateList(originalHistoryItems);
                }
                return true;
            }
        });

        return true;
    }



    /*    private ArrayList<Result> loadResults(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        Gson gson = new Gson();

        String resultsJson = sharedPreferences.getString("Result", null);

        Type type = new TypeToken<ArrayList<Result>>() {}.getType();
        ArrayList<Result> resultsHistoryItems = gson.fromJson(resultsJson, type);

        if (resultsHistoryItems == null) {
            resultsHistoryItems = new ArrayList<>();
        }

        return resultsHistoryItems;
    }*/

    public static class ItemDecorator extends RecyclerView.ItemDecoration {

        private final Paint paint;
        private final int dividerHeight;

        public ItemDecorator(int color, int dividerHeight) {
            paint = new Paint();
            paint.setColor(color);
            this.dividerHeight = dividerHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = dividerHeight;
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + dividerHeight;
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }
}

