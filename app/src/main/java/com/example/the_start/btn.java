package com.example.the_start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaCodecList;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.the_start.R;

public class btn extends androidx.appcompat.widget.AppCompatButton {
    int points, row = -1, column = -1;
    int player_chance; //0 when un-clicked, 1 for player 1(red),2 for 2nd player(blue)
    int points_player1 = 0, points_player2 = 0;

    public btn(Context context, int row, int column, int score, int color, int gridSize) {
        super(context);
        this.row = row;
        this.column = column;
        this.points = score;
        player_chance = color;


    }



    void setPlayer_chance(int player) {
        player_chance = player;
        this.setColor();
    }

    int getPoints() {
        return points;
    }

    void addPoints(int player_chance, int p) {
        points = points + p;
        if (points == 0) {
            this.setText(" ");
            // this.player_chance=0;
            setBackgroundResource(R.drawable.btn_round);
        }
        else{
            this.setText(String.valueOf(points));
            if (player_chance == 1) {
                points_player1 += p;
            } else if (player_chance==2){
                points_player2 += p;
            }
        }
    }

    int getRow() {
        return row;
    }
    void setPoints(int p){
        this.points=p;
        this.setText(" ");
    }
    int getColumn() {
        return column;
    }

    public int getPlayer_chance() {
        return player_chance;
    }

    public void setColor() {
        if (player_chance == 1) {
            setBackgroundResource(R.drawable.btn_round_red);

        } else if (player_chance==2) setBackgroundResource(R.drawable.btn_round_blue);
        else if (player_chance==0) setBackgroundResource(R.drawable.btn_round);
    }


}


//    public void printscore(){
//        Toast.makeText(MainActivity.,String.valueOf(points_player1)+"--"+String.valueOf(points_player2),Toast.LENGTH_SHORT ).show();
//    }


