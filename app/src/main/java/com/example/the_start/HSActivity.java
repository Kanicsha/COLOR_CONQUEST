package com.example.the_start;

import static java.util.Collections.*;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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
    private ArrayList<score> player_scores ;
     int p_score=30,time=20;
    String p_name="Andrea" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hsactivity);

        LinearLayout linearLayout=findViewById(R.id.rankcard);
        player_scores=new ArrayList<>();

        player_scores.add(new score("Harry",25, 40.04f));
        player_scores.add(new score("jdf",23, 43.04f));
        player_scores.add(new score(p_name,p_score,time));



           player_scores.sort(new Comparator<score>() {
                @Override
                public int compare(score p1, score p2) {
                    return Integer.compare(p2.getScore(), p1.getScore());
                }
            });

            for(int i=0;i<player_scores.size();i++) {
                score score1=player_scores.get(i);
                View cardView = LayoutInflater.from(this).inflate(R.layout.player_ranked, null);
                TextView rankTextView = cardView.findViewById(R.id.rank);
                TextView scoreTextView = cardView.findViewById(R.id.score);
                TextView name = cardView.findViewById(R.id.player_name);
                TextView timer = cardView.findViewById(R.id.timer);


                score1.player_rank=i+1;
                rankTextView.setText(String.valueOf(score1.getRank()));
                scoreTextView.setText(String.valueOf(score1.getScore()));
                timer.setText(String.valueOf(score1.getTimer()));
                name.setText(score1.getPlayer_name());
                linearLayout.addView(cardView);
            }
        }


}

