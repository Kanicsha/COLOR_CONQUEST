package com.example.the_start;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Second_Activity extends AppCompatActivity {
int gridSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        Button button_play2=findViewById(R.id.button_play2);
        EditText player1=(EditText)findViewById(R.id.et1);
        EditText player2=(EditText)findViewById(R.id.et2);


        //showDialog();
        button_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1_name=player1.getText().toString();
                String player2_name=player2.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(Second_Activity.this);

                builder.setTitle("Select Grid Size");


                // Create an array of grid size options (you can modify this according to your needs)
                final String[] gridSizeOptions = {"3x3", "5x5", "7x7", "9x9"};


                    builder.setItems(gridSizeOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the selected grid size index
                        //gridSize = which;

                        //get the first character n convert into a string
                        int selectedGridSize = Integer.parseInt(gridSizeOptions[which].substring(0, 1));

                        // Dismiss the dialog
                        dialog.dismiss();


                        // Launch the next activity and pass the selected grid size
                        Intent intent = new Intent(Second_Activity.this, ThirdActivity.class);
                        intent.putExtra("gridSize", selectedGridSize);
                        intent.putExtra("p1", player1_name);
                        intent.putExtra("p2", player2_name);
                        startActivity(intent);
                    }
                });

                // Create and show the dialog
                builder.create().show();
            }




        });
    }

}
