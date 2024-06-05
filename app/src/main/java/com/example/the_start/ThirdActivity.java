package com.example.the_start;





import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    private int  score = 0;
    protected ArrayList<btn> buttons;
    int current_player;
    int max_score = 4;

   int gridSize;

    int count=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_grid);
        current_player = 1;
        score = 0;
        buttons = new ArrayList<btn>();
        LinearLayout layout = findViewById(R.id.layout);


        Intent third= getIntent();
        int gridSize=third.getIntExtra("gridSize",5);
        String player1_name=third.getStringExtra("p1");
        String player2_name=third.getStringExtra("p2");

        TextView tv1 =findViewById(R.id.tv_score1);
        TextView tv2 =findViewById(R.id.tv_score2);
        TextView tv_player1 =findViewById(R.id.tvplayer1);
        TextView tv_player2 =findViewById(R.id.tvplayer2);
        tv_player2.setText(player2_name);
        tv_player1.setText(player1_name);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout layout3=findViewById(R.id.layout3);
        layout.setBackgroundColor(Color.parseColor("#B9F6CA"));
        GridLayout gridLayout= new GridLayout(this);
        gridLayout.setColumnCount(gridSize);
        gridLayout.setRowCount(gridSize);
        layout3.addView(gridLayout);
        gridLayout.setPadding(30,30,30,30);
        gridLayout.setForegroundGravity(Gravity.CENTER);
        layout3.setGravity(Gravity.CENTER);
        ImageButton exit = (ImageButton) this.<View>findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGrid();
                current_player=1;
            }
        });











        int grid_width=getResources().getDisplayMetrics().widthPixels;
        int btn_width=grid_width/gridSize;
        int i;
        for (i = 0; i < gridSize; i++) {
            int j;
            for (j = 0; j < gridSize; j++) {

                btn button = new btn(this, i, j, 0, 0, gridSize);
                button.setText(" ");
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setGravity(Gravity.CENTER);
                params.columnSpec=GridLayout.spec(j);
                params.rowSpec=GridLayout.spec(i);
                button.setPadding(20,20,20,20);
                params.width=4*btn_width/5; //80% of space
                params.height=4*btn_width/5;
                buttons.add(button);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.btn_round);

                gridLayout.addView(button);
            }
        }
        for (btn button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                                      btn clicked_button = (btn) v;


                                                      if (clicked_button.getPlayer_chance() == 0) {//clicking the unturned button
                                                          final MediaPlayer mediaPlayer = MediaPlayer.create(ThirdActivity.this, R.raw.lock_tap);
                                                          mediaPlayer.start();
                                                         // Toast.makeText(ThirdActivity.this, "First Click", Toast.LENGTH_SHORT).show();

                                                          clicked_button.setPlayer_chance(current_player);
                                                          clicked_button.addPoints(current_player, 3);
                                                          clicked_button.setText(String.valueOf(clicked_button.points));
                                                          current_player = 3 - current_player;
                                                          updateScore(tv1, tv2);
                                                          change_bg(current_player, layout);
                                                          count++;

                                                      }
                                                      else if (clicked_button.getPlayer_chance() == current_player) {//clicking already turned button
                                                          final MediaPlayer mediaPlayer = MediaPlayer.create(ThirdActivity.this, R.raw.lock_tap);
                                                          mediaPlayer.start();

                                                          clicked_button.addPoints(current_player, 1);
                                                         // Toast.makeText(ThirdActivity.this, "subsequent clicks", Toast.LENGTH_SHORT).show();
                                                          Log.i("CLICKS","subsequent move");
                                                          if (clicked_button.points >= max_score) {
                                                              clicked_button.addPoints(current_player, -4);
                                                              //make the center button unturned
                                                              try {
                                                                  Thread.sleep(500);
                                                              } catch (InterruptedException e) {
                                                                  throw new RuntimeException(e);
                                                              }
                                                              attack(clicked_button);
                                                              //clicked_button.setPlayer_chance(0);

                                                          }
                                                         // Toast.makeText(ThirdActivity.this, "after attack clicks", Toast.LENGTH_SHORT).show();
                                                          updateScore(tv1, tv2);
                                                          //to set current player to next player
                                                          current_player = 3 - current_player;
                                                          // layout.setBackgroundColor(Color.parseColor("29B6F6"));
                                                          change_bg(current_player, layout);
                                                            count++;

                                                          //showDialog();
                                                      }
                                                      else {
                                                          final MediaPlayer mediaPlayer = MediaPlayer.create(ThirdActivity.this, R.raw.oops);
                                                          mediaPlayer.start();
                                                          //Toast.makeText(ThirdActivity.this, "INVALID MOVE", Toast.LENGTH_SHORT).show();
                                                      }
                                                      if (count>2){
                                                            int player1_points=Integer.parseInt((String) tv1.getText());
                                                            int player2_points=Integer.parseInt((String) tv2.getText());
                                                            if (player1_points==0 || player2_points==0){
                                                            Log.i("WON","Hurrayy");

                            //Toast.makeText(ThirdActivity.this, "Hurray !!",Toast.LENGTH_SHORT).show();
                                                             show_windialog(player1_name,player2_name,player1_points,player2_points,tv1,tv2);
                        }
                    }

                                                  }

                                              });
                                          }
                                      }



                   /**/







    private void attack(btn clickedbutton) {

        int player = clickedbutton.getPlayer_chance();
        int row = clickedbutton.getRow();
        int column = clickedbutton.getColumn();
        btn top = getButton(row - 1, column);
        btn down = getButton(row + 1, column);
        btn left = getButton(row, column - 1);
        btn right = getButton(row, column + 1);
        //if the buttons are unclicked, you set the player to the current player and addPoints
        clickedbutton.setPlayer_chance(0);
        Log.i("CLICKS",String.valueOf(top));
        if (top!=null){
            Log.i("CLICKS","top move");
            if (top.getPlayer_chance() == 0) {
                top.setPlayer_chance(player);
                top.addPoints(player, 1);
            }
            else if (top.getPlayer_chance()==player) {
                top.addPoints(player, 1);

                if (top.getPoints() >= max_score) {
                    // if, again the button adds up to 4 you get double expansion
                    top.addPoints(player, -4);
                    attack(top);
                }
            }
            else{
                top.setPlayer_chance(player);

                top.addPoints(player, 1);
                if (top.getPoints() >= max_score) {
                    // if, again the button adds up to 4 you get double expansion
                    top.addPoints(player, -4); //to make the button unturned again
                    attack(top);
                }
            }

        }
        if (down!=null){

            if (down.getPlayer_chance() == 0) {
                down.setPlayer_chance(player);
                down.addPoints(player, 1);
            }
            else if ( down.getPlayer_chance() == player) {
                down.setPlayer_chance(player);
                down.addPoints(player, 1);
                if (down.getPoints() >= max_score) {
                    down.addPoints(player, -4);
                    attack(down);
                }
            }
            else{
                down.setPlayer_chance(player);

                down.addPoints(player, 1);
                if (down.getPoints() >= max_score) {
                    down.addPoints(player, -4);
                    attack(down);
                }
            }
        }
        if (left!=null){
            if (left.getPlayer_chance() == 0) {
                left.setPlayer_chance(player);
                left.addPoints(player, 1);

            }
            else if(left.getPlayer_chance() == player) {
                left.addPoints(player, 1);
                if (left.getPoints() >= max_score) {
                    //left.setPlayer_chance(player);
                    left.addPoints(player, -4);
                    attack(left);
                }
            }
            else{
                left.setPlayer_chance(player);

                left.addPoints(player, 1);
                if (left.getPoints() >= max_score) {
                    left.addPoints(player, -4);
                    attack(left);
                }
            }

        }
        if (right!=null){
            if (right.getPlayer_chance() == 0) {
                right.setPlayer_chance(player);
                right.addPoints(player, 1);
            }
            else if (right.getPlayer_chance() == player) {
                right.addPoints(player, 1);
                if (right.getPoints() >= max_score) {
                    right.addPoints(player, -4);
                    attack(right);
                }
            }
            else{
                right.setPlayer_chance(player);
                right.addPoints(player, 1);
                if (right.getPoints() >= max_score) {

                    right.addPoints(player, -4);
                    attack(right);
                }
            }
        }

    }




    private void change_bg(int current_player,LinearLayout layout) {
        if (current_player == 1) layout.setBackgroundResource(R.drawable.game_bg_red);
        else layout.setBackgroundResource(R.drawable.game_bg_blue);
    }

    private btn getButton(int row, int column) {

        for (btn button : buttons) {
            if (button.getRow() == row && button.getColumn() == column) {
                return button;
            }}

        return null;
    }


    private boolean victory() {
        for (btn button : buttons) {
            if (button.getPlayer_chance() != current_player && button.getPlayer_chance()!=0 ) {
                return false;
                // Opponent color exists, game not over
            }
        }
        return true;
    }
    private void updateScore(TextView textView1, TextView textView2){
        int points1=0;
        int points2=0;
        for(btn button:buttons){
            if(button.player_chance==1) {
                points1 += button.points;
            }
            else if (button.player_chance==2) {
                points2+= button.points;
            }
        }
        textView1.setText(String.valueOf(points1));
        textView2.setText(String.valueOf(points2));

    }


    private void resetGrid(){
        final MediaPlayer mediaPlayer= MediaPlayer.create(ThirdActivity.this,R.raw.oops);
        mediaPlayer.start();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.reset, null);
        AlertDialog reset= new AlertDialog.Builder(this).setView(view).create();
        reset.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button yes= view.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(btn button: buttons){
                    button.setPlayer_chance(0);
                    button.setPoints(0);
                }
                reset.dismiss();
            }
        });
        Button no=view.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.dismiss();
            }
        });

        reset.show();

    }
//one of the scores must be zero
private void show_windialog(String player1_name, String player2_name, int player1_score, int player2_score,TextView tv1, TextView tv2){

    final MediaPlayer mediaPlayer = MediaPlayer.create(ThirdActivity.this, R.raw.won);
    mediaPlayer.start();
        String winner_name=player1_score==0?player2_name:player1_name;
    int winner_score=player1_score==0?player2_score:player1_score;
    int loser_score=(winner_score==player1_score)?player2_score:player1_score;
    String loser= (winner_name==player1_name) ?player2_name:player1_name;
    /*Animation staranimation= AnimationUtils.loadAnimation(this,R.anim.star);
    ImageView star1=findViewById(R.id.star1);
    star1.setVisibility(View.VISIBLE);
    star1.setAnimation(staranimation);
    ImageView star2=findViewById(R.id.star2);
    star2.setVisibility(View.VISIBLE);
    star2.setAnimation(staranimation);
    ImageView star3=findViewById(R.id.star3);
    star3.setVisibility(View.VISIBLE);
    star3.setAnimation(staranimation);*/
    AlertDialog winDialog = new AlertDialog.Builder(this).create();
        // Define the dialog title
    LayoutInflater inflater = LayoutInflater.from(this);
    View view = inflater.inflate(R.layout.winner_dialog, null);
    winDialog.setView(view);
    Button home = view.findViewById(R.id.home);
    Button play_again = view.findViewById(R.id.play_again);
    TextView winnertv=view.findViewById(R.id.textView2);
    winnertv.setText(winner_name);

    play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winDialog.dismiss();

                Intent toHome = new Intent();  // Create an intent to carry data
                toHome.putExtra("winner", winner_name);
                toHome.putExtra("loser",loser);
                toHome.putExtra("winner_score",winner_score);
                toHome.putExtra("loser_score",loser_score);

                for(btn button: buttons){
                    button.setPlayer_chance(0);
                    button.setPoints(0);
                }
                updateScore(tv1,tv2);
                current_player=1;
                count=0;


            }
        });

    home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome= new Intent(ThirdActivity.this,HSActivity.class);
                toHome.putExtra("winner", winner_name);
                toHome.putExtra("loser",loser);
                toHome.putExtra("winner_score",winner_score);
                toHome.putExtra("loser_score",loser_score);
                startActivity(toHome);
                
            }
        });


        winDialog.show();
}
        }







