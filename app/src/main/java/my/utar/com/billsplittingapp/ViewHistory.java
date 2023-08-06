package my.utar.com.billsplittingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewHistory extends AppCompatActivity {

    private FloatingActionButton fabShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        setTitle("View History");
        fabShare = findViewById(R.id.fabShare);

        // Set click listeners for FAB

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle share action here
                Toast.makeText(ViewHistory.this, "Share clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}