package com.example.the_start;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity2 extends AppCompatActivity {
int gridSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second2);
        Button custom= findViewById(R.id.custom);
        Button ready=findViewById(R.id.ready);
        Button def=findViewById(R.id.normal);
        Intent already=getIntent();
        String player1_name=already.getStringExtra("p1");
        String player2_name=already.getStringExtra("p2");
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGridSizeDialog();

            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(SecondActivity2.this, ThirdActivity.class);
                startActivity(next);
            }
        });
        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(SecondActivity2.this, ThirdActivity.class);
                startActivity(next);
            }
        });

    }
    public void showGridSizeDialog() {
        // Create a new single-choice dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Define the dialog title
        builder.setTitle("Select Grid Size");

        // Create an array of grid size options (you can modify this according to your needs)
        final String[] gridSizeOptions = {"3x3", "4x4", "5x5","6x6","7x7","8x8"};

        // Set the single-choice items and a listener for selected items
        builder.setItems(gridSizeOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the selected grid size index
                gridSize = which;

                // Get the selected grid size text (optional)
                int selectedGridSize = Integer.parseInt(gridSizeOptions[which].substring(0,1));

                // Dismiss the dialog
                dialog.dismiss();

                // Launch the next activity and pass the selected grid size
               Intent intent = new Intent(SecondActivity2.this, ThirdActivity.class);
               intent.putExtra("gridSize", selectedGridSize);
               startActivity(intent);
            }
        });

        // Create and show the dialog
        builder.create().show();
    }
}