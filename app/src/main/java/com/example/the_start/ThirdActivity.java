package com.example.the_start;





import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    private int  score = 0, i, j;
    protected ArrayList<btn> buttons;
    int current_player;
    int max_score = 4;
    Intent third= getIntent();
   //int  gridSize=show_gridDialog();
    int gridSize=5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_grid);
        current_player = 1;
        score = 0;
        buttons = new ArrayList<btn>();
        LinearLayout layout = findViewById(R.id.layout);
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
            }
        });




        String player1_name=third.getStringExtra("p1");
        String player2_name=third.getStringExtra("p2");

        TextView tv1 =findViewById(R.id.tv_score1);
        TextView tvplayer1 =findViewById(R.id.tvplayer1);
        TextView tvplayer2 =findViewById(R.id.tvplayer2);
        tvplayer2.setText(player2_name);
        tvplayer1.setText(player1_name);

        TextView tv2 =findViewById(R.id.tv_score2);







        int grid_width=getResources().getDisplayMetrics().widthPixels;
        int btn_width=grid_width/gridSize;
        for (i = 0; i < gridSize; i++) {
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
                        final MediaPlayer mediaPlayer= MediaPlayer.create(ThirdActivity.this,R.raw.lock_tap);
                        mediaPlayer.start();


                        clicked_button.setPlayer_chance(current_player);
                        clicked_button.addPoints(current_player, 3);
                        clicked_button.setText(String.valueOf(clicked_button.points));
                        current_player = 3 - current_player;
                        updateScore(tv1,tv2);
                        change_bg(current_player,layout);

                    }

                    else if (clicked_button.getPlayer_chance() == current_player) {//clicking already turned button
                        final MediaPlayer mediaPlayer= MediaPlayer.create(ThirdActivity.this,R.raw.lock_tap);
                        mediaPlayer.start();

                        clicked_button.addPoints(current_player, 1);

                        if (clicked_button.points >= max_score) {
                            clicked_button.addPoints(current_player, -4);
                            //make the center button unturned
                            attack(clicked_button);
                            clicked_button.setPlayer_chance(0);




                        }
                        updateScore(tv1,tv2);
                        //to set current player to next player
                        current_player = 3 - current_player;
                        // layout.setBackgroundColor(Color.parseColor("29B6F6"));
                        change_bg(current_player,layout);


                        //showDialog();
                    }

                    else {
                        final MediaPlayer mediaPlayer= MediaPlayer.create(ThirdActivity.this,R.raw.oops);
                        mediaPlayer.start();

                    }
                    if (victory()) {
                        //show_windialog();

                    }

                }

            });
        }
    }


    private void attack(btn clickedbutton) {

        int player = clickedbutton.getPlayer_chance();
        int row = clickedbutton.getRow();
        int column = clickedbutton.getColumn();
        btn top = getButton(row - 1, column);
        btn down = getButton(row + 1, column);
        btn left = getButton(row, column - 1);
        btn right = getButton(row, column + 1);
        //if the buttons are unclicked, you set the player to the current player and addPoints


        if (top != null && top.getPlayer_chance() == 0) {
            top.setPlayer_chance(player);

            top.addPoints(player, 1);


        }
        if (down != null && down.getPlayer_chance() == 0) {
            down.setPlayer_chance(player);

            down.addPoints(player, 1);
        }


        if (left != null && left.getPlayer_chance() == 0) {
            left.setPlayer_chance(player);
            left.addPoints(player, 1);

        }
        if (right != null && right.getPlayer_chance() == 0) {
            right.setPlayer_chance(player);

            right.addPoints(player, 1);
        }
        //CONQUEST
        else {
            //if it's opponent's button
            if (top != null && top.getPlayer_chance() == 3 - player) {
                top.setPlayer_chance(player);

                top.addPoints(player, 1);
                if (top.getPoints() >= max_score) {
                    // if, again the button adds up to 4 you get double expansion
                    top.addPoints(player, -4); //to make the button unturned again
                    attack(top);
                }
            }
            //if it's your button
            if (top != null && top.getPlayer_chance() == player) {
                top.addPoints(player, 1);

                if (top.getPoints() >= max_score) {
                    // if, again the button adds up to 4 you get double expansion
                    top.addPoints(player, -4);

                    attack(top);
                }
            }
//for other buttons
            if (down != null && down.getPlayer_chance() == 3 - player) {
                down.setPlayer_chance(player);

                down.addPoints(player, 1);
                if (down.getPoints() >= max_score) {
                    down.addPoints(player, -4);
                    attack(down);
                }
            }
            if (down != null && down.getPlayer_chance() == player) {
                down.setPlayer_chance(player);
                down.addPoints(player, 1);
                if (down.getPoints() >= max_score) {
                    down.addPoints(player, -4);
                    attack(down);
                }
            }


            if (right != null && right.getPlayer_chance() == 3 - player) {
                right.setPlayer_chance(player);

                right.addPoints(player, 1);
                if (right.getPoints() >= max_score) {

                    right.addPoints(player, -4);
                    attack(right);
                }
            }
            if (right != null && right.getPlayer_chance() == player) {
                right.addPoints(player, 1);
                if (right.getPoints() >= max_score) {

                    right.addPoints(player, -4);
                    attack(right);
                }
            }
            if (left != null && left.getPlayer_chance() == 3 - player) {
                left.setPlayer_chance(player);

                left.addPoints(player, 1);
                if (left.getPoints() >= max_score) {
                    left.addPoints(player, -4);
                    attack(left);
                }
            }
            if (left != null && left.getPlayer_chance() == player) {
                left.addPoints(player, 1);
                if (left.getPoints() >= max_score) {
                    left.addPoints(player, -4);
                    attack(left);
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
            }
        }
        return null;
    }


    private boolean victory() {
        for (btn button : buttons) {
            if (button.getPlayer_chance() != current_player) {
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
    /*
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
                int gridSize = Integer.parseInt(gridSizeOptions[which].substring(0,1));

                // Dismiss the dialog
                dialog.dismiss();

                // Launch the next activity and pass the selected grid size
            }
        });

        // Create and show the dialog
        builder.create().show();

    }*/
    private int show_gridDialog(){
       Intent a=getIntent();
        return a.getIntExtra("selectedGridSize",5);
    }
    private void resetGrid(){
        final MediaPlayer mediaPlayer= MediaPlayer.create(ThirdActivity.this,R.raw.oops);
        mediaPlayer.start();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.reset, null);
        AlertDialog reset= new AlertDialog.Builder(this).setView(view).create();
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
/*
private void show_windialog(){
    String winner_name = null,loser = null;
    int winner_score=0,loser_score=0;
    winner_name=player1_name;
    for (btn button : buttons) {
        if (button.getPlayer_chance() != 1) {
           winner_name=player2_name;
           break;
            // Opponent color exists, game not over
        }

    }
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Define the dialog title
    LayoutInflater inflater = LayoutInflater.from(this);
    View view = inflater.inflate(R.layout.winner_dialog, null);

        // Create an array of grid size options (you can modify this according to your needs)


        // Set the single-choice items and a listener for selected items
        builder.setTitle("GAME OVER");
        Button home=findViewById(R.id.home);
    Button play_again=findViewById(R.id.play_again);
    String finalWinner_name = winner_name;
    TextView winner=findViewById(R.id.textView2);
    winner.setText(winner_name);
    home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome= new Intent(ThirdActivity.this,HSActivity.class);
                toHome.putExtra("winner", finalWinner_name);
                toHome.putExtra("loser",loser);
                toHome.putExtra("winner_score",winner_score);
                toHome.putExtra("loser_score",loser_score);
                resetGrid();;
            }
        });
    String finalWinner_name1 = winner_name;
    play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome= new Intent(ThirdActivity.this,HSActivity.class);
                toHome.putExtra("winner", finalWinner_name1);
                toHome.putExtra("loser",loser);
                toHome.putExtra("winner_score",winner_score);
                toHome.putExtra("loser_score",loser_score);
                startActivity(toHome);
            }
        });


        // Create and show the dialog
        builder.create().show();

}*/
}






