package com.example.the_start;

import static java.util.Collections.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HSActivity extends AppCompatActivity {
    private ArrayList<score> player_scores = new ArrayList<>(); // stores temporary lists
    int p1_score = 30, p2_score, time = 50;
    String p1_name, p2_name;

    ArrayList<score> new_one = new ArrayList<>(); //has all data
    int[] player_points = {20, 16};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hsactivity);
        Intent thirdtoHs = getIntent();
        p1_name = thirdtoHs.getStringExtra("winner");
        p2_name = thirdtoHs.getStringExtra("loser");
        p1_score = thirdtoHs.getIntExtra("winner_score", 0);
        p2_score = thirdtoHs.getIntExtra("loser_score", 0);
        ImageButton exit=findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoMain=new Intent(HSActivity.this,MainActivity.class);
                startActivity(backtoMain);
            }
        });
// Save

        new_one.add(new score("henry", 5));
        new_one.add(new score("Krazy-8", 8));

        SharedPreferences receiver = getSharedPreferences("players_data", MODE_PRIVATE);
        String received_String = receiver.getString("info_string", null);
        String[] individual = received_String.split(",");

        player_scores.add(new score(p1_name, p1_score));
        player_scores.add(new score(p2_name, p2_score));
        for(int i=0;i<player_scores.size();i++){
            new_one.add(player_scores.get(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < player_scores.size(); i++) {
            stringBuilder.append(player_scores.get(i).getPlayer_name()).append("/").append(player_scores.get(i).getScore());
            if (i < player_scores.size() - 1) {
                stringBuilder.append(","); //Saves as henry/100,Krazy-8/85...
            }
        }


        SharedPreferences sharedPreferences = getSharedPreferences("players_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("info_string", stringBuilder.toString());
        editor.apply();


//store current players data inside shared preferences

        LinearLayout linearLayout = findViewById(R.id.rankcard);






        for (String individual_fields : individual) {
            String[] name_score = individual_fields.split("/");
            String player_name = name_score[0];
            int player_score = Integer.parseInt(name_score[1]);
            int i = 0;
            while (i < new_one.size()) {
                score s = new_one.get(i);
                if (s.getPlayer_name().equals(player_name)) {
                    if(player_score>s.player_score) { //change score only if it's greater than d existing score
                        s.player_score = player_score;
                    }
                    break;
                }
                i++;
            }

            if (i == new_one.size()) { // if player is not found, then add it to the end of the arraylist
                new_one.add(new score(player_name, player_score));
            }

        }


//displaying top 7 players only
        new_one.sort(new Comparator<score>() {
            @Override
            public int compare(score p1, score p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });

          if (new_one.size() < 8) {
        for (int i = 0; i < new_one.size(); i++) {
            score score1 = new_one.get(i);
            View cardView = LayoutInflater.from(this).inflate(R.layout.player_ranked, null);
            TextView rankTextView = cardView.findViewById(R.id.rank);
            TextView scoreTextView = cardView.findViewById(R.id.score);
            TextView name = cardView.findViewById(R.id.player_name);
            TextView timer = cardView.findViewById(R.id.timer);
            score1.player_rank = i + 1;
            Log.i("inHs",String.valueOf(score1.getRank())+String.valueOf(score1.getScore())+score1.getPlayer_name());
            rankTextView.setText(String.valueOf(score1.getRank()));
            scoreTextView.setText(String.valueOf(score1.getScore()));
            name.setText(score1.getPlayer_name());
            linearLayout.addView(cardView);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                score score1 = new_one.get(i);
                View cardView = LayoutInflater.from(this).inflate(R.layout.player_ranked, null);
                TextView rankTextView = cardView.findViewById(R.id.rank);
                TextView scoreTextView = cardView.findViewById(R.id.score);
                TextView name = cardView.findViewById(R.id.player_name);
                score1.player_rank = i + 1;
                rankTextView.setText(String.valueOf(score1.getRank()));
                scoreTextView.setText(String.valueOf(score1.getScore()));
                name.setText(score1.getPlayer_name());
                linearLayout.addView(cardView);
            }
        }
    }
}



