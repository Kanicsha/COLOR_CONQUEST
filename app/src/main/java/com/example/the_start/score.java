package com.example.the_start;

import android.content.Context;

public class score {

    int player_rank;
    String player_name;
    int player_score;

    float timer_sec;
    public score(  String player_name, int player_score){
       //this.player_rank=rank;
       this.player_score=player_score;
       this.player_name=player_name;
    }
    public int getRank() {
        return player_rank;
    }

    public int getScore(){
        return player_score;
    }

    public String getPlayer_name(){
        return player_name;
    }
}
