package my.utar.com.billsplittingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private FloatingActionButton fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        //Create a return button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("View History");

        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load and display all the history items in RecyclerView
        ArrayList<HistoryItem> historyItems = loadAllHistoryItems();
        historyAdapter = new HistoryAdapter(this, historyItems);
        recyclerView.setAdapter(historyAdapter);


        // Add item decorator to display lines between history items
        int dividerHeightInPixels = 2;
        ItemDecorator itemDecorator = new ItemDecorator(getResources().getColor(R.color.colorTheme), dividerHeightInPixels);
        recyclerView.addItemDecoration(itemDecorator);

        // FAB
        fabShare = findViewById(R.id.fabShare);

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle share action here
                Toast.makeText(ViewHistory.this, "Share clicked", Toast.LENGTH_SHORT).show();
            }
        });
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

    private ArrayList<HistoryItem> loadAllHistoryItems() {
        ArrayList<HistoryItem> allHistoryItems = new ArrayList<>();

        // Load equalBreakdown history items
        ArrayList<HistoryItem> equalHistoryItems = loadHistoryItems("equal_breakdown");
        allHistoryItems.addAll(equalHistoryItems);

        // Load individualBreakdown history items
        ArrayList<HistoryItem> percentageHistoryItems = loadHistoryItems("percentage_breakdown");
        allHistoryItems.addAll(percentageHistoryItems);

        // Load individualBreakdown history items
        ArrayList<HistoryItem> individualHistoryItems = loadHistoryItems("individual_breakdown");
        allHistoryItems.addAll(individualHistoryItems);

        // Load combineBreakdown history items
        ArrayList<HistoryItem> combineHistoryItems = loadHistoryItems("combine_breakdown");
        allHistoryItems.addAll(combineHistoryItems);

        return allHistoryItems;
    }

    private ArrayList<HistoryItem> loadHistoryItems(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(key, MODE_PRIVATE);
        Gson gson = new Gson();

        String historyItemsJson = sharedPreferences.getString("historyItems", null);
        String historyItemsDate = sharedPreferences.getString("date", null);

        Type type = new TypeToken<ArrayList<HistoryItem>>() {}.getType();
        ArrayList<HistoryItem> historyItems = gson.fromJson(historyItemsJson, type);

        if (historyItems == null) {
            historyItems = new ArrayList<>();
        }

        // Set the date for each HistoryItem
        for (HistoryItem item : historyItems){
            item.setDate(historyItemsDate);
        }

        return historyItems;
    }

    public class ItemDecorator extends RecyclerView.ItemDecoration {

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

