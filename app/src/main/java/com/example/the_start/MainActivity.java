package com.example.the_start;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btn_play;
        btn_play=findViewById(R.id.button_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent main2second= new Intent(MainActivity.this,Second_Activity.class);
                                           startActivity(main2second);

                                       }


        });


        ImageButton qn=findViewById(R.id.imageButton);
        qn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
public void showDialog() {
    LayoutInflater inflater = LayoutInflater.from(this);
    View view = inflater.inflate(R.layout.start_game, null);
    AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).setTitle("Instruction").create();
    alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    Button next = view.findViewById(R.id.start);
    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent main2second = new Intent(MainActivity.this, HSActivity.class);
            startActivity(main2second);
        }
    });

    alertDialog.show();

}

}